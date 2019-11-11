package com.example.clearmvvmnotes.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clearmvvmnotes.model.Note;
import com.example.clearmvvmnotes.model.NoteRepository;

import java.util.List;

public class NoteViewModel extends ViewModel {
    private NoteRepository repository;
    private List<Note> allNotes;
    private MutableLiveData<List<Note>> listLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();

    public NoteViewModel(Application application) {
      repository=new NoteRepository(application);
      allNotes=repository.getAllNotes();
      setBooleanLiveData(true);
      setListLiveData(allNotes);
    }

    public MutableLiveData<Boolean> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setBooleanLiveData(boolean b) {
      mutableLiveData.setValue(b);
    }

    public void setListLiveData(List<Note> notes) {
      listLiveData.setValue(notes);
    }

    public MutableLiveData<List<Note>> getAllNotes(){
        return listLiveData;
    }


    public void insert(Note note){
        new InsertNoteAsyncTask(repository).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncTask(repository).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(repository).execute(note);
    }

    public void deleteAll(){
        new DeleteAllNotesAsyncTask(repository).execute();
    }

    private class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteRepository noteRepository;

        private InsertNoteAsyncTask(NoteRepository noteRepository){
            this.noteRepository=noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanLiveData(false);
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
            setBooleanLiveData(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteRepository noteRepository;

        private UpdateAsyncTask(NoteRepository noteRepository){
            this.noteRepository=noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanLiveData(false);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            final long start = System.currentTimeMillis();
            // wait 5 seconds (5000 milliseconds) until proceeding
            while (System.currentTimeMillis() - start < 5000) {
            }
            noteRepository.update(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setBooleanLiveData(true);
        }
    }


    private class DeleteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteRepository noteRepository;

        private DeleteAsyncTask(NoteRepository noteRepository){
            this.noteRepository=noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanLiveData(false);
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
            setBooleanLiveData(true);
        }
    }

    private class DeleteAllNotesAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteRepository noteRepository;

        private DeleteAllNotesAsyncTask(NoteRepository noteRepository){
            this.noteRepository=noteRepository;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setBooleanLiveData(false);
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
            setBooleanLiveData(true);
        }
    }



}
