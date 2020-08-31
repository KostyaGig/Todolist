package com.kostya_zinoviev.todlist.screens.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kostya_zinoviev.todlist.App;
import com.kostya_zinoviev.todlist.R;
import com.kostya_zinoviev.todlist.model.Note;

public class NoteDetailActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";
    private Note note;
    private EditText edText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        initToolbar();
        edText = findViewById(R.id.edText);

        if (getIntent().hasExtra(EXTRA_NOTE) && getIntent() != null) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            edText.setText(note.text);
        } else {
            note = new Note();
        }


    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.create_note_text);
    }

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saved_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //back button
                finish();
            break;
            case R.id.saved:
                //saved button
                if (edText.getText().length() > 0) {
                    //Проверяем на пустоту и если не пустая заполняем нашу модель Note
                    note.text = edText.getText().toString();
                    note.done = false;
                    note.timestamp = System.currentTimeMillis();
                    //Проверяем новая ли заметка и если новая вставяем в таблицу
                    if (getIntent().hasExtra(EXTRA_NOTE)) {
                        //Это редактирование заметки
                        //Обновляем заметку
                        App.getInstance().getNoteDao().update(note);
                    } else {
                        //Это новая заметка
                        //Если она новая ,вставляем ее
                        App.getInstance().getNoteDao().insert(note);
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
