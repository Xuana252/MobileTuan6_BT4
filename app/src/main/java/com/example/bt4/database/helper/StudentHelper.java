package com.example.bt4.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bt4.data.Student;
import com.example.bt4.database.AppDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class StudentHelper {
    private final AppDatabase db;

    public StudentHelper(Context context) {
        db = new AppDatabase(context);
    }

    // Method to add a new student
    public void addNew(Student item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Generate a unique ID for the student
        String uniqueId = generateUniqueId();
        values.put(AppDatabase.STUDENT_COLUMN_ID, uniqueId);
        values.put(AppDatabase.STUDENT_COLUMN_NAME, item.getName());
        values.put(AppDatabase.STUDENT_COLUMN_DOB, item.getDob());
        values.put(AppDatabase.STUDENT_COLUMN_CLASSID, item.getClassId());

        database.insert(AppDatabase.STUDENT_TABLE, null, values);
        database.close();
    }

    // Method to update an existing student
    public boolean update(Student item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDatabase.STUDENT_COLUMN_NAME, item.getName());
        values.put(AppDatabase.STUDENT_COLUMN_DOB, item.getDob());

        int rowAffected = database.update(AppDatabase.STUDENT_TABLE, values,
                AppDatabase.STUDENT_COLUMN_ID + "= ?", new String[]{item.getID()});
        database.close();
        return rowAffected > 0;
    }

    // Method to delete a student by ID
    public boolean delete(String id) {
        SQLiteDatabase database = db.getWritableDatabase();
        int rowAffected = database.delete(AppDatabase.STUDENT_TABLE, AppDatabase.STUDENT_COLUMN_ID + "= ?", new String[]{id});
        database.close();
        return rowAffected > 0;
    }

    // Method to load all students
    public ArrayList<Student> loadAll() {
        SQLiteDatabase database = db.getReadableDatabase();
        String[] projection = {
                AppDatabase.STUDENT_COLUMN_ID,
                AppDatabase.STUDENT_COLUMN_NAME,
                AppDatabase.STUDENT_COLUMN_DOB,
                AppDatabase.STUDENT_COLUMN_CLASSID
        };

        Cursor cursor = database.query(AppDatabase.STUDENT_TABLE, projection, null, null, null, null, null);
        ArrayList<Student> items = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String birthdate = cursor.getString(2);
                String classId = cursor.getString(3);
                items.add(new Student(id, name, birthdate, classId));
            }
            cursor.close(); // Close the cursor after use
        }
        database.close(); // Close the database
        return items;
    }

    // Method to load students by class ID
    public ArrayList<Student> loadClass(String classId) {
        SQLiteDatabase database = db.getReadableDatabase();
        String[] projection = {
                AppDatabase.STUDENT_COLUMN_ID,
                AppDatabase.STUDENT_COLUMN_NAME,
                AppDatabase.STUDENT_COLUMN_DOB,
                AppDatabase.STUDENT_COLUMN_CLASSID
        };

        Cursor cursor = database.query(AppDatabase.STUDENT_TABLE, projection, AppDatabase.STUDENT_COLUMN_CLASSID + "= ?", new String[]{classId}, null, null, null);
        ArrayList<Student> items = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String birthdate = cursor.getString(2);
                String studentClass = cursor.getString(3);
                items.add(new Student(id, name, birthdate, studentClass));
            }
            cursor.close(); // Close the cursor after use
        }
        database.close(); // Close the database
        return items;
    }

    // Method to get a student by ID
    public Student getById(String id) {
        SQLiteDatabase database = db.getReadableDatabase();
        String[] projection = {
                AppDatabase.STUDENT_COLUMN_ID,
                AppDatabase.STUDENT_COLUMN_NAME,
                AppDatabase.STUDENT_COLUMN_DOB,
                AppDatabase.STUDENT_COLUMN_CLASSID
        };
        Cursor cursor = database.query(AppDatabase.STUDENT_TABLE, projection,
                AppDatabase.STUDENT_COLUMN_ID + "= ?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String birthdate = cursor.getString(2);
            String studentClass = cursor.getString(3);
            cursor.close();
            database.close();
            return new Student(id, name, birthdate, studentClass);
        }
        if (cursor != null) cursor.close(); // Ensure cursor is closed
        database.close(); // Ensure database is closed
        return null;
    }

    private String generateUniqueId() {
        StringBuilder id = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // You can modify this to include lowercase letters, symbols, etc.

        for (int i = 0; i < 3; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            id.append(characters.charAt(randomIndex));
        }

        return id.toString();
    }
}
