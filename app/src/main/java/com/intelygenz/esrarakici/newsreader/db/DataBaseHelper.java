package com.intelygenz.esrarakici.newsreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by esrarakici on 28/04/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_for_news";
    public DataBaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE news ( link TEXT  , title TEXT , description TEXT ," +
                " date TEXT ,  imageLink TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrading database from old version to new version which will destroy all old data
        db.execSQL("Drop table if exists news" );
        onCreate(db);
    }
}
