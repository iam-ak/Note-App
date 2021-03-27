package com.relativecoding.noteapp.Database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.relativecoding.noteapp.Dao.NoteDao;
import com.relativecoding.noteapp.Models.Note;


@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static com.relativecoding.noteapp.Database.NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized com.relativecoding.noteapp.Database.NoteDatabase getInstance(Context context){
        if(instance==null)
        {
            instance= Room.databaseBuilder(context, com.relativecoding.noteapp.Database.NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static Callback callback=new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsynTask(instance).execute();

        }
    };


    private static class PopulateDbAsynTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        public PopulateDbAsynTask(com.relativecoding.noteapp.Database.NoteDatabase noteDatabase) {
            noteDao=noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("Title 1","Description 1", 1));
            noteDao.insert(new Note("Title 2","Description 2", 2));
            noteDao.insert(new Note("Title 3","Description 3", 3));
            return null;
        }
    }

}
