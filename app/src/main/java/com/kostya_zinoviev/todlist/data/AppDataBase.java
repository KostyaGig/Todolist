package com.kostya_zinoviev.todlist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kostya_zinoviev.todlist.model.Note;

//@Database - ЗНАЧИТ база данных 1 парметр - entities,класс - модель
//2 - version db
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}


