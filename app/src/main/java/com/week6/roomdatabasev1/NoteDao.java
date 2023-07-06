package com.week6.roomdatabasev1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Query("select * from notes")
    List<Note> getAllNotes();

}
