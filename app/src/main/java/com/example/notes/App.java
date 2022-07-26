package com.example.notes;

import android.app.Application;

import androidx.room.Room;

import com.example.notes.data.NoteDao;
import com.example.notes.data.NoteDatabase;

public class App extends Application {

    private NoteDatabase database;
    private NoteDao noteDao;
    public static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class, "notedb")
                .allowMainThreadQueries()
                .build();

        noteDao = database.getNoteDao();
        instance = this;
    }

    public NoteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(NoteDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}
