package com.example.week9_demo_databases.RealmDB;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.RealmDB.DB.NoteOrgAdapter;
import com.example.week9_demo_databases.RealmDB.DB.NoteOrgRlm;

import java.util.UUID;

public class RealmDBActivity extends AppCompatActivity {
    private Realm realm;

    @BindView(R.id.lsviewRLM)  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_d_b);
        ButterKnife.bind(this);

        Realm.init(this); // added in Realm 2.0.0
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("MoaathDB.realm").schemaVersion(0).build());


        realm = Realm.getDefaultInstance();


        RealmResults<NoteOrgRlm> tasks = realm.where(NoteOrgRlm.class).findAll();
        final NoteOrgAdapter adapter = new NoteOrgAdapter(this, tasks);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final NoteOrgRlm note = (NoteOrgRlm) adapterView.getAdapter().getItem(i);
                final String crrntId= note.getId();
                final ViewGroup subView = (ViewGroup) getLayoutInflater().// inflater view
                        inflate(R.layout.costum_alert_view, null, false);
                ( (EditText)  subView.findViewById(R.id.noteTitle) ).setText(note.getNoteTitle());
                ( (EditText)  subView.findViewById(R.id.noteDescription) ).setText(note.getNoteDescription());
                AlertDialog dialog = new AlertDialog.Builder(RealmDBActivity.this)
                        .setTitle("Edit Task")
                        .setView(subView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                NoteOrgRlm n= new NoteOrgRlm();
                                n.setId(crrntId);
                                n.setNoteTitle( ((EditText)  subView.findViewById(R.id.noteTitle) ).getText().toString());
                                n.setNoteDescription( ((EditText)  subView.findViewById(R.id.noteDescription) ).getText().toString());

                                updateNoteName(n);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTask(crrntId);
                            }
                        })
                        .create();
                dialog.show();
            }
        });




    }

    public void onClickRLM(View view) {

        final ViewGroup subView = (ViewGroup) getLayoutInflater().// inflater view
                inflate(R.layout.costum_alert_view, null, false);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Note")

                .setView(subView)

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                NoteOrgRlm n=
                                        realm.createObject(NoteOrgRlm.class, UUID.randomUUID().toString());
                                        n.setNoteTitle(String.valueOf(
                                               ( (EditText)  subView.findViewById(R.id.noteTitle) ).getText()));
                                n.setNoteDescription(String.valueOf(( (EditText)  subView.findViewById(R.id.noteDescription) ).getText()
                                ));

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    public void updateNoteName(NoteOrgRlm nte ) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NoteOrgRlm note = realm.where(NoteOrgRlm.class).equalTo("id", nte.getId()).findFirst();
                note.setNoteTitle(nte.getNoteTitle());
                note.setNoteDescription(nte.getNoteDescription());
            }
        });
    }


    private void deleteTask(final String _Id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(NoteOrgRlm.class).equalTo("id", _Id)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    private void deleteAllDone() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(NoteOrgRlm.class).equalTo("done", true)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }




}