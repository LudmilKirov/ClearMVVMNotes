package com.example.clearmvvmnotes.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.clearmvvmnotes.model.Note;
import com.example.clearmvvmnotes.model.NoteRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private MutableLiveData<List<Note>> listLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> booleanForDialog = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
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
    public void delete(Note note) {
        new DeleteAsyncTask(repository).execute(note);
    }

    public void deleteAll() {
        new DeleteAllNotesAsyncTask(repository).execute();
    }

    //Create an asynctask that get the repository on the background thread and return the livedata
    public LiveData<List<Note>> getAllNotes() {
        GetNotesAsynctTask t = new GetNotesAsynctTask();
        try {
            Log.d("kur", "succsess");
            return t.execute(repository).get();
        } catch (Exception e) {
            Log.d("kur", "getAllNotes: " + e.getMessage());
        }
        return listLiveData;
    }
    //AsyncTasks
    private class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteRepository noteRepository;

        private DeleteAsyncTask(NoteRepository noteRepository) {
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
            noteRepository.delete(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setBooleanForDialog(true);
        }
    }

    private class DeleteAllNotesAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteRepository noteRepository;

        private DeleteAllNotesAsyncTask(NoteRepository noteRepository) {
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
            noteRepository.deleteAllNotes();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setBooleanForDialog(true);
        }
    }

    private class GetNotesAsynctTask extends AsyncTask<NoteRepository, Void, LiveData<List<Note>>> {

        @Override
        protected LiveData<List<Note>> doInBackground(NoteRepository... repositories) {
            listLiveData.postValue(repositories[0].getAllNotes());
            return listLiveData;
        }
    }
}
