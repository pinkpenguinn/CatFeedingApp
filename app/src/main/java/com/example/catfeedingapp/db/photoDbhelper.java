package com.example.catfeedingapp.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import com.example.catfeedingapp.photomodel.photo;

public class photoDbhelper extends SQLiteOpenHelper {
    private static final String TEXT = " TEXT";
    private static final String INTEGER = " INTEGER";
    private static final String COMMA_SEPERATOR = ",";
    private static final String DATABASE_NAME = "photoAlbum.db";
    private static final int DATABASE_VERSION = 1;



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String CreateEntries = "CREATE TABLE " + photoFormat.photoEntry.TABLE_NAME +
            "(" + photoFormat.photoEntry._ID + INTEGER + " PRIMARY KEY" + COMMA_SEPERATOR +
            photoFormat.photoEntry.COLUMN_TITLE + TEXT + COMMA_SEPERATOR +
            photoFormat.photoEntry.COLUMN_IMAGE + TEXT + ")";

    public photoDbhelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateEntries);
    }

    public Cursor readAllMemories() {
        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                photoFormat.photoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );


        }

    public boolean addMemory(photo photo) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(photoFormat.photoEntry.COLUMN_TITLE, photo.getTitle());
        values.put(photoFormat.photoEntry.COLUMN_IMAGE, photo.getImageAsString());

        return db.insert(photoFormat.photoEntry.TABLE_NAME, null, values) != -1;

    }

    public Cursor getData (String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }




}
