package com.example.clearmvvmnotes.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.clearmvvmnotes.model.Note;
import com.example.clearmvvmnotes.model.NoteRepository;

public class AddEditNoteActivityViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private MutableLiveData<Boolean> booleanForDialog = new MutableLiveData<>();

    public AddEditNoteActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    //Getter and setter
    public MutableLiveData<Boolean> getBooleanForDialog() {
        return booleanForDialog;
    }

    public void setBooleanForDialog(boolean b) {
        booleanForDialog.postValue(b);
    }

    //Methods for viewmodel
    public void insert(Note note) {
        new AddEditNoteActivityViewModel.InsertNoteAsyncTask(repository).execute(note);
    }

    public void update(Note note) {
        new AddEditNoteActivityViewModel.UpdateAsyncTask(repository).execute(note);
    }

    //AsyncTasks
    private class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteRepository noteRepository;

        private InsertNoteAsyncTask(NoteRepository noteRepository) {
            this.noteRepository = noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanForDialog(false);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            final long start = System.currentTimeMillis();
            // wait 5 seconds (5000 milliseconds) until proceeding
            while (System.currentTimeMillis() - start < 5000) {
            }
            noteRepository.insert(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setBooleanForDialog(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteRepository noteRepository;

        private UpdateAsyncTask(NoteRepository noteRepository) {
            this.noteRepository = noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanForDialog(false);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            final long start = System.currentTimeMillis();
            // wait 5 seconds (5000 milliseconds) until proceeding
            while (System.currentTimeMillis() - start < 10000) {
            }
            noteRepository.update(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setBooleanForDialog(true);
        }
    }
}
