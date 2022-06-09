package com.mohamadrizki.perpusonline.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mohamadrizki.perpusonline.model.Book;
import com.mohamadrizki.perpusonline.utils.JsonHelper;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    public static String DATABASE_NAME = "perpusonline";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_BOOK = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.BOOK_TABLE_NAME,
            DatabaseContract.BookColumns._ID,
            DatabaseContract.BookColumns.NAME,
            DatabaseContract.BookColumns.AUTHOR,
            DatabaseContract.BookColumns.COVER,
            DatabaseContract.BookColumns.SYNOPSIS
    );

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.USER_TABLE_NAME,
            DatabaseContract.UserColumns._ID,
            DatabaseContract.UserColumns.NAME,
            DatabaseContract.UserColumns.EMAIL,
            DatabaseContract.UserColumns.PASSWORD,
            DatabaseContract.UserColumns.PHONE_NUMBER,
            DatabaseContract.UserColumns.DATE_OF_BIRTH
    );

    private static final String SQL_CREATE_TABLE_REQUEST = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " FOREIGN KEY (%s) REFERENCES %s(%s)," +
                    " FOREIGN KEY (%s) REFERENCES %s(%s)," +
                    " FOREIGN KEY (%s) REFERENCES %s(%s))",
            DatabaseContract.REQUEST_TABLE_NAME,
            DatabaseContract.RequestColumns._ID,
            DatabaseContract.RequestColumns.BOOK_ID,
            DatabaseContract.RequestColumns.REQUESTER_ID,
            DatabaseContract.RequestColumns.RECEIVER_ID,
            DatabaseContract.RequestColumns.LATITUDE,
            DatabaseContract.RequestColumns.LONGITUDE,
            DatabaseContract.RequestColumns.BOOK_ID,
            DatabaseContract.BOOK_TABLE_NAME,
            DatabaseContract.BookColumns._ID,
            DatabaseContract.RequestColumns.REQUESTER_ID,
            DatabaseContract.USER_TABLE_NAME,
            DatabaseContract.UserColumns._ID,
            DatabaseContract.RequestColumns.RECEIVER_ID,
            DatabaseContract.USER_TABLE_NAME,
            DatabaseContract.UserColumns._ID
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BOOK);
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_REQUEST);
        JsonHelper jsonHelper = new JsonHelper(this.context);
        List<Book> bookList = jsonHelper.loadData();
        for (Book book : bookList) {
            db.execSQL(insert(book));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.BOOK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.REQUEST_TABLE_NAME);
        onCreate(db);
    }

    private String insert(Book book){
        return String.format("INSERT INTO books(name, author, cover, synopsis) " +
                        "VALUES ('%s', '%s', '%s', '%s');",
                book.getName().replace("'", "''"),
                book.getAuthor(),
                book.getCover(),
                book.getSynopsis().replace("'", "''"));
    }
}
