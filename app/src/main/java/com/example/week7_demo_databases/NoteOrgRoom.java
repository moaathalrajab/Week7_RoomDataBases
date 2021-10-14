package com.example.week7_demo_databases;


import java.io.Serializable;

import androidx.room.*;

@Entity(tableName = "items")

public class NoteOrgRoom  implements  Serializable{


    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_description")
    private String NoteDesc;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDesc() {
        return NoteDesc;
    }

    public void setNoteDesc(String noteDesc) {
        NoteDesc = noteDesc;
    }
}
