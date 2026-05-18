package com.example.fifthpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "appdata.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE = "items";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESC = "description";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_CATEGORY = "category";
    public static final String COL_NOTE = "note";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_QUANTITY + " INTEGER, " +
                COL_CATEGORY + " TEXT, " +
                COL_NOTE + " TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, item.getTitle());
        cv.put(COL_DESC, item.getDescription());
        cv.put(COL_QUANTITY, item.getQuantity());
        cv.put(COL_CATEGORY, item.getCategory());
        cv.put(COL_NOTE, item.getNote());
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE, new String[]{COL_ID, COL_TITLE, COL_DESC, COL_QUANTITY, COL_CATEGORY, COL_NOTE},
                COL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Item item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
            cursor.close();
            db.close();
            return item;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, item.getTitle());
        cv.put(COL_DESC, item.getDescription());
        cv.put(COL_QUANTITY, item.getQuantity());
        cv.put(COL_CATEGORY, item.getCategory());
        cv.put(COL_NOTE, item.getNote());
        int rows = db.update(TABLE, cv, COL_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
        return rows > 0;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
