package com.example.sixthpractice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.sixthpractice.provider";
    public static final String PATH_BOOKS = "books";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_BOOKS);

    private static final int BOOKS = 1;
    private static final int BOOK_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, PATH_BOOKS, BOOKS);
        URI_MATCHER.addURI(AUTHORITY, PATH_BOOKS + "/#", BOOK_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        BookDbHelper helper = new BookDbHelper(getContext());
        db = helper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        int match = URI_MATCHER.match(uri);

        if (match == BOOKS) {
            cursor = db.query(BookDbHelper.TABLE_BOOKS, projection, selection, selectionArgs, null, null, sortOrder);
        } else if (match == BOOK_ID) {
            String idSelection = BookDbHelper.COL_ID + "=?";
            String[] idArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            cursor = db.query(BookDbHelper.TABLE_BOOKS, projection, idSelection, idArgs, null, null, sortOrder);
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (URI_MATCHER.match(uri) != BOOKS) {
            throw new IllegalArgumentException("Insert not supported for URI: " + uri);
        }
        long rowId = db.insert(BookDbHelper.TABLE_BOOKS, null, values);
        if (rowId <= 0) {
            throw new IllegalStateException("Insert failed for URI: " + uri);
        }

        Uri insertedUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(insertedUri, null);
        }
        return insertedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        int match = URI_MATCHER.match(uri);

        if (match == BOOKS) {
            count = db.delete(BookDbHelper.TABLE_BOOKS, selection, selectionArgs);
        } else if (match == BOOK_ID) {
            String idSelection = BookDbHelper.COL_ID + "=?";
            String[] idArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            count = db.delete(BookDbHelper.TABLE_BOOKS, idSelection, idArgs);
        } else {
            throw new IllegalArgumentException("Delete not supported for URI: " + uri);
        }

        if (count > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int count;
        int match = URI_MATCHER.match(uri);

        if (match == BOOKS) {
            count = db.update(BookDbHelper.TABLE_BOOKS, values, selection, selectionArgs);
        } else if (match == BOOK_ID) {
            String idSelection = BookDbHelper.COL_ID + "=?";
            String[] idArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            count = db.update(BookDbHelper.TABLE_BOOKS, values, idSelection, idArgs);
        } else {
            throw new IllegalArgumentException("Update not supported for URI: " + uri);
        }

        if (count > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = URI_MATCHER.match(uri);
        if (match == BOOKS) {
            return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".books";
        }
        if (match == BOOK_ID) {
            return "vnd.android.cursor.item/vnd." + AUTHORITY + ".books";
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
}
