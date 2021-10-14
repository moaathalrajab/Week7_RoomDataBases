package com.example.week7_demo_databases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.week9_demo_databases.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import android.content.Intent;
        import android.os.AsyncTask;

import java.util.List;


public class RoomDBActivity  extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_d_b);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.fabRooM);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomDBActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });


        getNotes();

    }


    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<NoteOrgRoom>> {

            @Override
            protected List<NoteOrgRoom> doInBackground(Void... voids) {
                List<NoteOrgRoom> noteList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .noteOrgDAO()
                        .getAll();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<NoteOrgRoom> nts) {
                super.onPostExecute(nts);
                NoteOrgAdapter adapter = new NoteOrgAdapter(RoomDBActivity.this, nts);
                recyclerView.setAdapter(adapter);
            }
        }

        GetNotes gt = new GetNotes();
        gt.execute();
    }

}