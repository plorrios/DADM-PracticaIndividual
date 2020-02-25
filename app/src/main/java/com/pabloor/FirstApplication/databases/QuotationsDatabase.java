package com.pabloor.FirstApplication.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pabloor.FirstApplication.Quotation;

@Database(entities = {Quotation.class}, version = 1)
public abstract class QuotationsDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static QuotationsDatabase quotationsDatabase;

    public static synchronized QuotationsDatabase getInstance(Context context){

        if (quotationsDatabase == null) {
            quotationsDatabase = Room.databaseBuilder(context,QuotationsDatabase.class,"QuotationContract").allowMainThreadQueries().build();
        }

        return quotationsDatabase;
    }
}