package com.example.expensetrackersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerRegistration extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "registration.db";
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    public DatabaseHandlerRegistration(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + " TEXT," +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USER_ID};
        String selection = COL_EMAIL + "=? AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean loggedIn = cursor.moveToFirst();
        cursor.close();
        return loggedIn;
    }
}