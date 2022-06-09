package com.mohamadrizki.perpusonline.helper;

import android.database.Cursor;

import com.mohamadrizki.perpusonline.db.DatabaseContract;
import com.mohamadrizki.perpusonline.model.Book;
import com.mohamadrizki.perpusonline.model.User;

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

    public static User mapCursorToObject(Cursor userCursor) {
        User user = null;
        if (userCursor.moveToNext()) {
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns._ID));
            String email = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.EMAIL));
            String password = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.PASSWORD));
            String phoneNumber = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.PHONE_NUMBER));
            String dateOfBirth = userCursor.getString(userCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.DATE_OF_BIRTH));
            user = new User(id, email, password, phoneNumber, dateOfBirth);
        }

        return user;
    }
}
