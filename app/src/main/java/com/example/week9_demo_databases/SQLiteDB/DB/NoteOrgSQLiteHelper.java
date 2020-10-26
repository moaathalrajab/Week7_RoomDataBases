package com.example.week9_demo_databases.SQLiteDB.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.week9_demo_databases.SQLiteDB.DB.NoteOrganizerContract.*;


public class NoteOrgSQLiteHelper extends SQLiteOpenHelper {

    // Database creation sql statement
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrganizerNoteEntry.TABLE_NAME + " (" +
                    OrganizerNoteEntry._ID + " INTEGER PRIMARY KEY," +
                    OrganizerNoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    OrganizerNoteEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + OrganizerNoteEntry.TABLE_NAME;

    private static final String DATABASE_NAME = "organizer_notes.db";
    private static final int DATABASE_VERSION = 1;



    public NoteOrgSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NoteOrgSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + OrganizerNoteEntry.TABLE_NAME);
        onCreate(db);
    }

}