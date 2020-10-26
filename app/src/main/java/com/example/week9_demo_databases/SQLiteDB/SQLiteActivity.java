package com.example.week9_demo_databases.SQLiteDB;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.SQLiteDB.DB.NoteOrgManipulation;
import com.example.week9_demo_databases.SQLiteDB.DB.NoteOrganizer;

import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {
    private NoteOrgManipulation datasource;
    private NoteOrgAdapter adapter;
    @BindView(R.id.lsview) ListView mLv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite);
        ButterKnife.bind(this);

        datasource = new NoteOrgManipulation(this);
        datasource.open();

        List<NoteOrganizer> values = datasource.getAllNotes();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
       adapter = new NoteOrgAdapter(this, values  );
        mLv.setAdapter(adapter);

        mLv.setOnItemClickListener(this::processClickItem);
    }

    private void processClickItem(AdapterView<?> adapterView, View view, int i, long l) {
        final ArrayAdapter<NoteOrganizer> adapter = (ArrayAdapter<NoteOrganizer>) mLv.getAdapter();

        final NoteOrganizer note = (NoteOrganizer) adapterView.getAdapter().getItem(i);
        final long id=note.getId(); final int itmloc=i;
        final ViewGroup subView = (ViewGroup) getLayoutInflater().// inflater view
                inflate(R.layout.costum_alert_view, null, false);
        ( (EditText)  subView.findViewById(R.id.noteTitle) ).setText(note.getNoteTitle());
         ((EditText)  subView.findViewById(R.id.noteDescription) ).setText(note.getNoteDescription());
        AlertDialog dialog = new AlertDialog.Builder(SQLiteActivity.this)
                .setTitle("Edit Note")
                .setView(subView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.remove(note);
                        datasource.updateNote(id,( (EditText)  subView.findViewById(R.id.noteTitle) ).getText().toString()
                                , ((EditText)  subView.findViewById(R.id.noteDescription) ).getText().toString());
                        adapter.insert(new NoteOrganizer(id,( (EditText)  subView.findViewById(R.id.noteTitle) )
                                .getText().toString()
                                , ((EditText)  subView.findViewById(R.id.noteDescription) ).getText().toString())
                                ,itmloc);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datasource.deleteNote(note);
                        adapter.remove(note);
                    }
                })
                .create();
                 dialog.show();
                 adapter.notifyDataSetChanged();
                 adapter.notifyDataSetInvalidated();

    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {

               final ArrayAdapter<NoteOrganizer> adapter = (ArrayAdapter<NoteOrganizer>) mLv.getAdapter();
                 NoteOrganizer nt = null;
                final ViewGroup subView = (ViewGroup) getLayoutInflater().// inflater view
                inflate(R.layout.costum_alert_view, null, false);
                AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Note")

                .setView(subView)

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NoteOrganizer nt = datasource.createNote(   ( (EditText)  subView.findViewById(R.id.noteTitle) ).getText().toString()
                        , ((EditText)  subView.findViewById(R.id.noteDescription) ).getText().toString() );
                        adapter.add(nt);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}