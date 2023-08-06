package com.zybooks.eventtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class EventDb extends SQLiteOpenHelper {

    public static final String TABLENAME = "eventlist.db";
    public static final int VER = 1;
    String query;
    SQLiteDatabase db;

    public EventDb(Context context) {
        super(context, TABLENAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query = "create table " + TABLENAME + "(id integer primary key autoincrement, title text, date text, startTime text, endTime text, description text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        query = "drop table if exists " + TABLENAME + "";
        db.execSQL(query);
        onCreate(db);
    }

    public Boolean checkEvent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLENAME, null);
        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void insertEvent(String title, String date, String start, String end, String description) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("startTime", start);
        contentValues.put("endTime", end);
        contentValues.put("description", description);

        db.insert(TABLENAME, null, contentValues);
        db.close();
    }

    /*
    public void updateEvent(String title, String date, String start, String end, String description) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("startTime", start);
        contentValues.put("endTime", end);
        contentValues.put("description", description);

        db.update("events", values, "title = ?", new String[]{title});
        db.close();
    }

    public void deleteEvent() {
        db = this.getWritableDatabase();
        db.delete("events", "title = ?", new String[] {});
        db.close();
    } */
}
