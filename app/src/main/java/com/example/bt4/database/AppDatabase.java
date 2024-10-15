package com.example.bt4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "BT4.db";

    public static final String ACCOUNT_TABLE = "Account";
    public static final String ACCOUNT_COLUMN_ID = "id";
    public static final String ACCOUNT_COLUMN_USERNAME = "username";
    public static final String ACCOUNT_COLUMN_PASSWORD = "password";
    public static final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE " + ACCOUNT_TABLE + " ("
            + ACCOUNT_COLUMN_ID + " TEXT PRIMARY KEY, "
            + ACCOUNT_COLUMN_USERNAME +" TEXT,"
            + ACCOUNT_COLUMN_PASSWORD + " TEXT)";

    public static final String SQL_DELETE_ACCOUNT_TABLE = "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    public static final String CLASS_TABLE = "Class";
    public static final String CLASS_COLUMN_ID = "id";
    public static final String CLASS_COLUMN_NAME = "name";
    public static final String CLASS_COLUMN_DEPARTMENT = "department";
    public static final String SQL_CREATE_CLASS_TABLE = "CREATE TABLE " + CLASS_TABLE + " ("
            + CLASS_COLUMN_ID + " TEXT PRIMARY KEY,"
            + CLASS_COLUMN_NAME + " TEXT,"
            + CLASS_COLUMN_DEPARTMENT + " TEXT)";
    public static final String SQL_DELETE_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE;


    public static final String STUDENT_TABLE = "Student";
    public static final String STUDENT_COLUMN_ID = "id";
    public static final String STUDENT_COLUMN_NAME = "name";
    public static final String STUDENT_COLUMN_DOB = "birthdate";
    public static final String STUDENT_COLUMN_CLASSID = "classId";
    public static final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE + " ("
            + STUDENT_COLUMN_ID + " TEXT PRIMARY KEY,"
            + STUDENT_COLUMN_NAME + " TEXT,"
            + STUDENT_COLUMN_CLASSID + " TEXT,"
            + STUDENT_COLUMN_DOB + " TEXT,"
            + "FOREIGN KEY (" + STUDENT_COLUMN_CLASSID + ") REFERENCES " + CLASS_TABLE + "(" + CLASS_COLUMN_ID + ") ON DELETE CASCADE)";
    public static final String SQL_DELETE_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE;

    public AppDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(android.database.sqlite.SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_CLASS_TABLE);
        db.execSQL(SQL_CREATE_STUDENT_TABLE);

        ContentValues values = new ContentValues();

        // Record 1
        values.put(ACCOUNT_COLUMN_ID, "1");
        values.put(ACCOUNT_COLUMN_USERNAME, "user1");
        values.put(ACCOUNT_COLUMN_PASSWORD, "password1");
        db.insert(ACCOUNT_TABLE, null, values);

        // Record 2
        values.put(ACCOUNT_COLUMN_ID, "2");
        values.put(ACCOUNT_COLUMN_USERNAME, "user2");
        values.put(ACCOUNT_COLUMN_PASSWORD, "password2");
        db.insert(ACCOUNT_TABLE, null, values);

        values.put(CLASS_COLUMN_ID, "1");
        values.put(CLASS_COLUMN_NAME, "user1");
        values.put(CLASS_COLUMN_DEPARTMENT, "password1");
        db.insert(CLASS_TABLE, null, values);


    }
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
        db.execSQL(SQL_DELETE_CLASS_TABLE);
        db.execSQL(SQL_DELETE_STUDENT_TABLE);
        onCreate(db);
    }
}
