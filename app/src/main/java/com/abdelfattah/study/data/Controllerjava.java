package com.abdelfattah.study.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.Nullable;

public class Controllerjava {

    private DBHelper db;
    private SQLiteDatabase readdata;
    private SQLiteDatabase writedata;
    public Controllerjava(Context contex)
    {
        db=new DBHelper(contex);
        readdata=db.getReadableDatabase();
        readdata=db.getWritableDatabase();


    }


}
