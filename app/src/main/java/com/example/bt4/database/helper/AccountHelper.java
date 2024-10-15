package com.example.bt4.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bt4.data.Account;
import com.example.bt4.database.AppDatabase;

public class AccountHelper {
    private final AppDatabase db;

    public AccountHelper(Context context) {
        db = new AppDatabase(context);
    }

    // Method to add a new account
    public void addNew(Account account) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDatabase.ACCOUNT_COLUMN_ID, account.getId());
        values.put(AppDatabase.ACCOUNT_COLUMN_USERNAME, account.getUsername());
        values.put(AppDatabase.ACCOUNT_COLUMN_PASSWORD, account.getPassword());
        database.insert(AppDatabase.ACCOUNT_TABLE, null, values);
        database.close();
    }

    // Method to check credentials
    public Account checkCredential(String username, String password) {
        SQLiteDatabase database = db.getReadableDatabase();
        String[] projection = {
                AppDatabase.ACCOUNT_COLUMN_ID,
                AppDatabase.ACCOUNT_COLUMN_USERNAME,
                AppDatabase.ACCOUNT_COLUMN_PASSWORD
        };
        Cursor cursor = database.query(AppDatabase.ACCOUNT_TABLE, projection,
                AppDatabase.ACCOUNT_COLUMN_USERNAME + " = ? AND " + AppDatabase.ACCOUNT_COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);

        Account account = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String id = cursor.getString(0);
                String usr = cursor.getString(1);
                String psw = cursor.getString(2);
                account = new Account(id, usr, psw); // Create account object if found
            }
            cursor.close(); // Ensure cursor is closed
        }
        database.close(); // Ensure database is closed
        return account; // Return found account or null
    }

    // Other methods for account management can go here...
}
