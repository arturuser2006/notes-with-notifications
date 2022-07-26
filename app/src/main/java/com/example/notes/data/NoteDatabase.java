package com.example.notes.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.notes.model.Note;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();
}
