package com.example.week7_demo_databases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.week9_demo_databases.R;

public class AddNoteActivity  extends AppCompatActivity {

    private EditText editTextTitle, editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextNoteTitle);
        editTextDesc = findViewById(R.id.editTextDesc);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteOrg();
            }
        });
    }

    private void saveNoteOrg() {
        final String ttle = editTextTitle.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();

        if (ttle.isEmpty()) {
            editTextTitle.setError("Note required");
            editTextTitle.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Note required");
            editTextDesc.requestFocus();
            return;
        }



        class SaveNoteOrg extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a note
                NoteOrgRoom note = new NoteOrgRoom();
                note.setNoteTitle(ttle);
                note.setNoteDesc(sDesc);


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteOrgDAO()
                        .insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), RoomDBActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveNoteOrg st = new SaveNoteOrg();
        st.execute();
    }

}
