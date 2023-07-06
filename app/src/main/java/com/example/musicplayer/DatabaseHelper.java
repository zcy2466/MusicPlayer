package com.example.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="LoveMusic.db";
    public static final int VERSION=1;
    public static final String TABLE_NAME="My_LoveMusic";
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //建表语句
    public static final String My_LoveMusic = "create table My_LoveMusic (id INTEGER primary key autoincrement,Musicname text, artist text , urlpath text )";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(My_LoveMusic);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
