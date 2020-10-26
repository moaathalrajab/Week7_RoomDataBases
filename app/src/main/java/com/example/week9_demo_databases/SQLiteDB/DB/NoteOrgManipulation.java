package com.example.week9_demo_databases.SQLiteDB.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.week9_demo_databases.SQLiteDB.DB.NoteOrganizerContract.*;


public class NoteOrgManipulation {
        // Database fields
        private SQLiteDatabase database;
        private NoteOrgSQLiteHelper dbHelper;
        private String[] allColumns = {
                                        OrganizerNoteEntry._ID,
                                        OrganizerNoteEntry.COLUMN_NAME_TITLE,
                                        OrganizerNoteEntry.COLUMN_NAME_DESCRIPTION
                                    };



        public NoteOrgManipulation(Context context) {
            dbHelper = new NoteOrgSQLiteHelper(context);
        }



        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }



        public void close() {
            dbHelper.close();
        }



        public NoteOrganizer createNote(String noteTitle, String noteDesc) {


            ContentValues values = new ContentValues();
            values.put(OrganizerNoteEntry.COLUMN_NAME_TITLE,noteTitle);
            values.put(OrganizerNoteEntry.COLUMN_NAME_DESCRIPTION,noteDesc);

            long insertId = database.insert(OrganizerNoteEntry.TABLE_NAME, null,
                    values);

            Cursor cursor = database.query(OrganizerNoteEntry.TABLE_NAME,
                    allColumns, OrganizerNoteEntry._ID + " = " + insertId, null,
                    null, null, null);

            cursor.moveToFirst();
            NoteOrganizer newComment = cursorToComment(cursor);
            cursor.close();
            return newComment;
        }

        public void deleteNote(NoteOrganizer note) {
            long id = note.getId();
            System.out.println("Note deleted with id: " + id);
            database.delete(OrganizerNoteEntry.TABLE_NAME, OrganizerNoteEntry._ID
                    + " = " + id, null);
        }

    public void updateNote(long id, String noteTitle, String noteDesc) {
        ContentValues values = new ContentValues();
        System.out.println("Note update with id: " + id);

        values.put(OrganizerNoteEntry._ID,id);
        values.put(OrganizerNoteEntry.COLUMN_NAME_TITLE,noteTitle);
        values.put(OrganizerNoteEntry.COLUMN_NAME_DESCRIPTION,noteDesc);
        database.update(OrganizerNoteEntry.TABLE_NAME, values,OrganizerNoteEntry._ID
                + " = " + id, null);
    }

        public List<NoteOrganizer> getAllNotes() {
            List<NoteOrganizer> notes = new ArrayList<NoteOrganizer>();

            Cursor cursor = database.query(OrganizerNoteEntry.TABLE_NAME,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                NoteOrganizer note = cursorToComment(cursor);
                notes.add(note);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return notes;
        }

        private NoteOrganizer cursorToComment(Cursor cursor) {
            NoteOrganizer note = new NoteOrganizer();
            note.setId(cursor.getLong(0));
            note.setNoteTitle(cursor.getString(1));
            note.setNoteDescription(cursor.getString(2));
            return note;
        }
}