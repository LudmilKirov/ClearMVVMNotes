package com.example.clearmvvmnotes.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.clearmvvmnotes.R;
import com.example.clearmvvmnotes.model.Note;
import com.example.clearmvvmnotes.viewmodel.AddEditNoteActivityViewModel;
import com.example.clearmvvmnotes.viewmodel.MainActivityViewModel;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.example.noteappmvmm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.noteappmvmm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.noteappmvmm.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "com.example.noteappmvmm.EXTRA_ID";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private AddEditNoteActivityViewModel addEditNoteActivityViewModel;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        addEditNoteActivityViewModel = ViewModelProviders.of(this).get(AddEditNoteActivityViewModel.class);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker);
        //Set the priorities of the tasks
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        //Only if it is update
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

        //Create the dialog for the loading
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Loading");
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        //Create the observer for the live data that changes in the async task for add method.
        addEditNoteActivityViewModel.getBooleanForDialog().observe(this, new Observer<Boolean>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        //To use the many that created
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    //To handle clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
//                View view = new View(this);
//                hideKeybaord(view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        //Work as form and not communicate with other
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_LONG).show();
            return;
        }

//        //To send data to the activity that started this one
//        Intent data = new Intent();
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_PRIORITY, priority);

        Note note = new Note(title,description,priority);
        addEditNoteActivityViewModel.insert(note);
        //Put only if is -1
//        int id = getIntent().getIntExtra(EXTRA_ID, -1);
//        if (id != -1) {
//            data.putExtra(EXTRA_ID, id);
//        }
//
//        setResult(RESULT_OK, data);

        Toast.makeText(AddEditNoteActivity.this,"Test",Toast.LENGTH_SHORT).show();
        finish();
    }
}
