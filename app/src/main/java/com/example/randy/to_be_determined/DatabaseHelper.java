package com.example.randy.to_be_determined;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Randy on 5/1/2016.
 *
 * Resources:
 * http://developer.android.com/training/basics/data-storage/databases.html
 * http://developer.android.com/guide/topics/data/data-storage.html#db
 * http://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#onUpgrade%28android.database.sqlite.SQLiteDatabase,%20int,%20int%29
 * http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#query%28java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String[],%20java.lang.String,%20java.lang.String,%20java.lang.String,%20java.lang.String%29
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    //PRIVATE CONSTANT VARIABLES
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SpotSwap";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table users(_id integer primary key autoincrement, " +
                "username text not null, password text not null, email text not null);");
        db.execSQL("create table posts(_id integer primary key autoincrement, " +
                "username text not null, location text not null, floor text not null, " +
                "numseats text not null, description text, windowseat integer, " +
                "poweroutlet integer, scanner integer, whiteboard integer, " +
                "maccomputers integer, rockingchair integer, imguri text, " +
                "reserved text not null);");

        /* Add an admin account to the user database */
        ContentValues values = new ContentValues();
        values.put("username", "Admin");
        values.put("password", "Admin");
        values.put("email", "brunecz1@umbc.edu");
        db.insert("users", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
