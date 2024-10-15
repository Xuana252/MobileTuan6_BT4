package com.example.bt4.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bt4.data.MyClass;
import com.example.bt4.database.AppDatabase;

import java.util.ArrayList;

public class ClassHelper {
    private final AppDatabase db;

    public ClassHelper(Context context) {
        db = new AppDatabase(context);
    }

    // Method to add a new class
    public void addNew(MyClass item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        String classId = generateUniqueId();
        values.put(AppDatabase.CLASS_COLUMN_ID,classId);
        values.put(AppDatabase.CLASS_COLUMN_NAME, item.getClassName());
        values.put(AppDatabase.CLASS_COLUMN_DEPARTMENT, item.getClassDepartment());
        database.insert(AppDatabase.CLASS_TABLE, null, values);
        database.close();
    }

    // Method to update an existing class
    public boolean update(MyClass item) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDatabase.CLASS_COLUMN_NAME, item.getClassName());
        values.put(AppDatabase.CLASS_COLUMN_DEPARTMENT, item.getClassDepartment());
        int rowAffected = database.update(AppDatabase.CLASS_TABLE, values,
                AppDatabase.CLASS_COLUMN_ID + "= ?", new String[]{item.getClassID()});
        database.close();
        return rowAffected > 0;
    }

    // Method to delete a class by ID
    public boolean delete(String id) {
        SQLiteDatabase database = db.getWritableDatabase();
        int rowAffected = database.delete(AppDatabase.CLASS_TABLE, AppDatabase.CLASS_COLUMN_ID + "= ?", new String[]{id});
        database.close();
        return rowAffected > 0;
    }

    // Method to load all classes
    public ArrayList<MyClass> loadAll() {
        SQLiteDatabase database = db.getReadableDatabase();
        String[] projection = {
                AppDatabase.CLASS_COLUMN_ID,
                AppDatabase.CLASS_COLUMN_NAME,
                AppDatabase.CLASS_COLUMN_DEPARTMENT,
        };

        Cursor cursor = database.query(AppDatabase.CLASS_TABLE, projection, null, null, null, null, null);
        ArrayList<MyClass> items = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String department = cursor.getString(2);
                items.add(new MyClass(id, name, department));
            }
            cursor.close(); // Ensure the cursor is closed after use
        }
        database.close(); // Close the database
        return items;
    }

    // Method to get a class by ID
    public MyClass getById(String id) {
        SQLiteDatabase database = db.getReadableDatabase(); // Use getReadableDatabase for fetching
        String[] projection = {
                AppDatabase.CLASS_COLUMN_ID,
                AppDatabase.CLASS_COLUMN_NAME,
                AppDatabase.CLASS_COLUMN_DEPARTMENT,
        };
        Cursor cursor = database.query(AppDatabase.CLASS_TABLE, projection,
                AppDatabase.CLASS_COLUMN_ID + "= ?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String department = cursor.getString(2);
            cursor.close();
            database.close();
            return new MyClass(id, name, department);
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
