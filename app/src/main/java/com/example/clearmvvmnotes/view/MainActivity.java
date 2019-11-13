package com.example.clearmvvmnotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clearmvvmnotes.R;
import com.example.clearmvvmnotes.adapter.NoteAdapter;
import com.example.clearmvvmnotes.model.Note;
import com.example.clearmvvmnotes.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private RecyclerView recyclerView;
    private AlertDialog mAlertDialog;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        setTheAdapter();
        observeTheList();
        observeForAlertDialog();
        swipeToDelete();

    }


    private void swipeToDelete(){
        //To swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //get the object that wnat to be delete
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                //Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //Initialize the floating button that add a new note
        FloatingActionButton buttonAddNote = findViewById(R.id.floatingActionButton);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    private void initRecyclerView(){
        //Initialize the recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void setTheAdapter(){
        //Set adapter for the recyclerview
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observeTheList(){
        //To observe the view model for a change of the list
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });
    }

    private void observeForAlertDialog(){
        //Create the dialog for the loading
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Loading");
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        //Create the observer for the live data that changes in the async task for add method.
        noteViewModel.getMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    mAlertDialog.show();
                } else {
                    mAlertDialog.hide();
                }
            }
        });
    }



}
