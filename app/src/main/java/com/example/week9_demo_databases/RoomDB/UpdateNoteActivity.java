package com.example.week9_demo_databases.RoomDB;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.week9_demo_databases.R;
import com.example.week9_demo_databases.RoomDB.DB.DatabaseClient;
import com.example.week9_demo_databases.RoomDB.DB.NoteOrgRoom;

import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;

import android.view.View;
import android.widget.EditText;
        import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText editTextNoteTitle, editTextDesc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);


        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextDesc = findViewById(R.id.editTextDesc);



        final NoteOrgRoom note = (NoteOrgRoom) getIntent().getSerializableExtra("Note");

        loadNote(note);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateNote(note);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateNoteActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNote(note);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadNote(NoteOrgRoom nt) {
        editTextNoteTitle.setText(nt.getNoteTitle());
        editTextDesc.setText(nt.getNoteDesc());

    }

    private void updateNote(final NoteOrgRoom nt) {
        final String ntTitle = editTextNoteTitle.getText().toString().trim();
        final String ntDesc = editTextDesc.getText().toString().trim();

        if (ntTitle.isEmpty()) {
            editTextNoteTitle.setError("Task required");
            editTextNoteTitle.requestFocus();
            return;
        }

        if (ntDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }



        class UpdateNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                nt.setNoteTitle(ntTitle);
                nt.setNoteDesc(ntDesc);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteOrgDAO()
                        .update(nt);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateNoteActivity.this, RoomDBActivity.class));
            }
        }

        UpdateNote ut = new UpdateNote();
        ut.execute();
    }


    private void deleteNote(final NoteOrgRoom task) {
        class DeleteNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()

                        .noteOrgDAO()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateNoteActivity.this, RoomDBActivity.class));
            }
        }

        DeleteNote dt = new DeleteNote();
        dt.execute();

    }

}
