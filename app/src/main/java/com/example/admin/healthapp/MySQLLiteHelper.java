package com.example.admin.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by redpower1989 on 4/5/2015.
 */
public class MySQLLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HealthDB.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_CALORIES_BURNED="burned";


 /* A database class to create a database table and methods to insert,delete,show methods*/

    private static final String KEY_BURNED = "burned";



    public MySQLLiteHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        String CREATE_BURNED_TABLE = "CREATE TABLE burned(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "burned TEXT)";
        sqLiteDatabase.execSQL(CREATE_BURNED_TABLE);






    }



    public void insertBunred(String burned)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BURNED, burned);

        db.insert(TABLE_CALORIES_BURNED,null,values);

        db.close();
    }



    public ArrayList<String> getAllBunred()
    {
        ArrayList<String> data = new ArrayList<String>();
        String query = "SELECT * FROM " + TABLE_CALORIES_BURNED;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Log.v("MyBooks", cursor.getString(0) +"-"+ cursor.getString(1));
                data.add(cursor.getString(1));
            }
            while(cursor.moveToNext());
        }

        db.close();
        return data;

    }



    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALORIES_BURNED,null,null);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
