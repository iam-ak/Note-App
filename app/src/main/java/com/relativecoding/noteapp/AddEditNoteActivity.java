package com.relativecoding.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private NumberPicker numberPickerPriority;

    public static final String EXTRA_ID=
            "com.example.practice.EXTRA_ID";
    public static final String EXTRA_TITLE=
            "com.example.practice.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION=
            "com.example.practice.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.example.practice.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.et_text_title);
        etDescription = findViewById(R.id.et_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent=getIntent();
        if(intent.hasExtra(com.relativecoding.noteapp.AddEditNoteActivity.EXTRA_ID)){
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(com.relativecoding.noteapp.AddEditNoteActivity.EXTRA_TITLE) );
            etDescription.setText(intent.getStringExtra(com.relativecoding.noteapp.AddEditNoteActivity.EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(com.relativecoding.noteapp.AddEditNoteActivity.EXTRA_PRIORITY,1));
        }else{
            setTitle("Add Note");
        }


    }

    private void saveNote() {

        String title=etTitle.getText().toString();
        String description=etDescription.getText().toString();
        int priority=numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data=new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id=getIntent().getIntExtra(EXTRA_ID,1);
        if ((id!=-1)){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_note:
                saveNote();
                return true;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}