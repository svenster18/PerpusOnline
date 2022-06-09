package com.mohamadrizki.perpusonline.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String BOOK_TABLE_NAME = "books";
    static String USER_TABLE_NAME = "users";
    static String REQUEST_TABLE_NAME = "requests";

    static final class BookColumns implements BaseColumns {
        static String NAME = "name";
        static String AUTHOR = "author";
        static String COVER = "cover";
        static String SYNOPSIS = "synopsis";
    }

    static final class UserColumns implements BaseColumns {
        static String NAME = "name";
        static String EMAIL = "email";
        static String PASSWORD = "password";
        static String PHONE_NUMBER = "phone_number";
        static String DATE_OF_BIRTH = "date_of_birth";
    }

    static final class RequestColumns implements BaseColumns {
        static String BOOK_ID = "book_id";
        static String REQUESTER_ID = "book_id";
        static String RECEIVER_ID = "receiver_id";
        static String LATITUDE = "latitude";
        static String LONGITUDE = "longitude";
    }
}
