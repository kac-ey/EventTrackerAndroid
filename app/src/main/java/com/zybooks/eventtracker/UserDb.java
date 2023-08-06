package com.zybooks.eventtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class UserDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "userinfo.db";
    public static final String TABLENAME = "user";
    static final int VER = 1;
    String query;

    public UserDb(Context context) {
        super(context, DB_NAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query = "create table " + TABLENAME + "(username text primary key, password text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        query = "drop table if exists " + TABLENAME + "";
        db.execSQL(query);
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long result = db.insert(TABLENAME, null, cv);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLENAME + " where username = ? and password = ?", new String[] {username,password});
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
