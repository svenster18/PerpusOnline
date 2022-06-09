package com.mohamadrizki.perpusonline.db;

import static com.mohamadrizki.perpusonline.db.DatabaseContract.USER_TABLE_NAME;
import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.EMAIL;
import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.PASSWORD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper {
    private static final String DATABASE_TABLE = USER_TABLE_NAME;
    private DatabaseHelper dataBaseHelper;
    private static SQLiteDatabase database;

    private UserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    private static volatile UserHelper INSTANCE;

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryByEmail(String email) {
        return database.query(
                DATABASE_TABLE,
                null,
                EMAIL + " = ? ",
                new String[]{email},
                null,
                null,
                null,
                "1");
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
}
