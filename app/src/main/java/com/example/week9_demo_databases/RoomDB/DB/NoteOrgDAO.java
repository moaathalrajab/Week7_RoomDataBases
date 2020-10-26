package com.example.week9_demo_databases.RoomDB.DB;

import java.util.List;

import androidx.room.*;

import static androidx.room.OnConflictStrategy.IGNORE;


@Dao
public interface NoteOrgDAO {

    @Query("SELECT * FROM items")
    List<NoteOrgRoom> getAll();

    @Query("SELECT * FROM items WHERE uid IN (:userIds)")
    List<NoteOrgRoom> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM items WHERE note_title LIKE :title AND " +
            "note_description LIKE :desc LIMIT 1")
    NoteOrgRoom findByName(String title, String desc);

    @Insert(onConflict = IGNORE)
    void insertAll(NoteOrgRoom... notes);

    @Insert(onConflict = IGNORE)
    void insert(NoteOrgRoom notes);

    @Delete
    void delete(NoteOrgRoom note);

    @Update
    void update(NoteOrgRoom note);

}
