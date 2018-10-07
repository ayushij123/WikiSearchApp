package com.aj.wikisearchapp.db;

import android.content.Context;
import android.os.AsyncTask;

import com.aj.wikisearchapp.remote.Note;
import com.aj.wikisearchapp.NoteDao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)//
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

private static RoomDatabase.Callback roomCallback = new Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new PopulateDBAsyncAsk(instance).execute();
    }
};

    private static class PopulateDBAsyncAsk extends AsyncTask<Void,Void,Void>{
        private  NoteDao noteDao;

        private PopulateDBAsyncAsk(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Desc", 1));
            return null;
        }
    }
}
