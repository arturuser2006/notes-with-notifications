package com.example.notes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllLiveData();

    @Query("SELECT * FROM note WHERE id IN (:id)")
    List<Note> findAllById(int[] id);

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1")
    Note findByName(int id);

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
