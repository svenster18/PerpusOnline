package com.mohamadrizki.perpusonline.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String BOOK_TABLE_NAME = "books";
    static String USER_TABLE_NAME = "users";
    static String REQUEST_TABLE_NAME = "requests";

    public static final class BookColumns implements BaseColumns {
        public static String NAME = "name";
        public static String AUTHOR = "author";
        public static String COVER = "cover";
        public static String SYNOPSIS = "synopsis";
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
        static String REQUESTER_ID = "requester_id";
        static String RECEIVER_ID = "receiver_id";
        static String LATITUDE = "latitude";
        static String LONGITUDE = "longitude";
    }
}
