package com.example.week9_demo_databases.SQLiteDB.DB;

import android.provider.BaseColumns;

public final class NoteOrganizerContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NoteOrganizerContract(){

    }

    /* Inner class that defines the table contents */
    public static class OrganizerNoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "note_title";
        public static final String COLUMN_NAME_DESCRIPTION = "note_description";
    }
}
