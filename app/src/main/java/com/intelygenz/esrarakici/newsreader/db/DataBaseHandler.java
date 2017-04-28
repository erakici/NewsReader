package com.intelygenz.esrarakici.newsreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esrarakici on 28/04/2017.
 */

public class DataBaseHandler {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;
    //methods for all table


    public DataBaseHandler(Context context, int version) {
        dbHelper = new DataBaseHelper(context, version);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable(String tableName) {
        database.delete( tableName, null, null);
    }


 //news table method

    public void insertNewsInfo(NewsItem newsItem) {
        ContentValues cv = new ContentValues();

        cv.put("link"              ,  newsItem.Link );
        cv.put("title"             ,  newsItem.Title );
        cv.put("description"       ,  newsItem.Description );
        cv.put("date"              ,  newsItem.Date );
        cv.put("imageLink"         ,  newsItem.ImageLink );

        database.insert("news" , null, cv);
    }


    public List<NewsItem> getAllNews() {
        List<NewsItem> newsList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select link " +
                " , title , description , date , imageLink" +
                " FROM news", new String[]{});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NewsItem newsInfo = new NewsItem(cursor.getString(1),cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4));

            newsList.add(newsInfo);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();

        return newsList;
    }




}
