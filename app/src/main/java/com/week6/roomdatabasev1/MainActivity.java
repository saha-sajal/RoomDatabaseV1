package com.week6.roomdatabasev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    NoteDatabase noteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class, "notes-db").build();

        EditText editTextID = findViewById(R.id.editTextID);
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextContent = findViewById(R.id.editTextContent);
        TextView textViewResult = findViewById(R.id.textViewResult);

        Button save = findViewById(R.id.buttonSave);
        Button read = findViewById(R.id.buttonRead);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int noteID = Integer.parseInt(editTextID.getText().toString());
                String noteTitle = editTextTitle.getText().toString();
                String noteContent = editTextContent.getText().toString();

                Note note = new Note();
                note.setNid(noteID);
                note.setTitle(noteTitle);
                note.setContent(noteContent);



                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        noteDatabase.noteDao().insertNote(note);
                        List<Note> notes = noteDatabase.noteDao().getAllNotes();
                        Log.i("Data",notes.get(0).getTitle()+" "+notes.get(0).getContent());
                    }
                });






            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        List<Note> notes = noteDatabase.noteDao().getAllNotes();

                        String result = "";
                        for(Note note:notes)
                        {
                            result = result+note.getTitle()+"\n";
                        }

                        String finalResult = result;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewResult.setText(finalResult);
                            }
                        });

                    }
                });






            }
        });
    }
}