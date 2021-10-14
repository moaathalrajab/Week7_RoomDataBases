package com.example.week7_demo_databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteOrgRoom.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteOrgDAO noteOrgDAO();
}
