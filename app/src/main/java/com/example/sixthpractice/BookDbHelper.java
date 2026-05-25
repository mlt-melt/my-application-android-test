package com.example.sixthpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BookDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "books.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_BOOKS = "books";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_YEAR = "year";

    public BookDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BOOKS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_AUTHOR + " TEXT NOT NULL, "
                + COL_YEAR + " INTEGER NOT NULL)");

        db.execSQL("INSERT INTO " + TABLE_BOOKS + " (" + COL_TITLE + ", " + COL_AUTHOR + ", " + COL_YEAR + ") VALUES "
                + "('Clean Code', 'Robert C. Martin', 2008),"
                + "('Effective Java', 'Joshua Bloch', 2018),"
                + "('Kotlin in Action', 'Dmitry Jemerov', 2017)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}
