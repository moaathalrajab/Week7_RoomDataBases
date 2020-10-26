package com.example.week9_demo_databases;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.week9_demo_databases.RealmDB.RealmDBActivity;
import com.example.week9_demo_databases.RoomDB.RoomDBActivity;
import com.example.week9_demo_databases.SQLiteDB.SQLiteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void goToSQLite(View view) {

        startActivity(new Intent(this, SQLiteActivity.class));
    }

    public void goToRoom(View view) {
        startActivity(new Intent(this, RoomDBActivity.class));
    }

    public void goToRealm(View view) {
        startActivity(new Intent(this, RealmDBActivity.class));

    }
}