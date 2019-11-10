package com.example.clearmvvmnotes.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private List<Note> allNotes;

    //TODO remove the appdatabase
    //TODO how to get
    public NoteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNote();
    }

    public void insert(Note note) {
        noteDao.insert(note);
    }

    public void update(Note note) {
        noteDao.update(note);
    }

    public void delete(Note note) {
        noteDao.delete(note);
    }

    public void deleteAllNotes() {
        noteDao.deleteAllNotes();
    }

    public List<Note> getAllNotes() {
        return allNotes;
    }
}