package com.example.week9_demo_databases.RoomDB.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteOrgRoom.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteOrgDAO noteOrgDAO();
}
