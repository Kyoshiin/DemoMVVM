package com.roy.mvvmexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao(); //to access db operations from NoteDao interface, code created by room

    //synchronized -> only 1 thread can access this method, hence only 1 instance will exits
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database") // for filename of db
                    .fallbackToDestructiveMigration()   // when db version update
                    .addCallback(roomCallback) //loading data into database
                    .build();
        }
        return instance;
    }

    //For loading the database for first time with some data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //class for populating db for first time on background thread
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","desc1",1));
            noteDao.insert(new Note("Title2","desc2",2));
            noteDao.insert(new Note("Title3","desc3",3));
            return null;
        }
    }
}
