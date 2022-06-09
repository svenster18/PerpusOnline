package com.mohamadrizki.perpusonline.helper;

import android.database.Cursor;

import com.mohamadrizki.perpusonline.db.DatabaseContract;
import com.mohamadrizki.perpusonline.model.Book;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Book> mapCursorToArrayList(Cursor booksCursor) {
        ArrayList<Book> booksList = new ArrayList<>();
        while (booksCursor.moveToNext()) {
            int id = booksCursor.getInt(booksCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns._ID));
            String name = booksCursor.getString(booksCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.NAME));
            String author = booksCursor.getString(booksCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.AUTHOR));
            String cover = booksCursor.getString(booksCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.COVER));
            String synopsis = booksCursor.getString(booksCursor.getColumnIndexOrThrow(DatabaseContract.BookColumns.SYNOPSIS));
            booksList.add(new Book(id, name, author, cover, synopsis));
        }

        return booksList;
    }
}
