package com.example.admin.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 5/5/2015.
 */
public class MySQLLiteHelperBmi extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BmiDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BMI = "bmi";
    private static final String KEY_BMI = "bmi";

     /* A database class to create a database table and methods to insert,delete,show methods*/

    public MySQLLiteHelperBmi(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BMI_TABLE = "CREATE TABLE bmi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "bmi TEXT)";
        db.execSQL(CREATE_BMI_TABLE);

    }

    public void insertBmi(String bmi)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BMI, bmi);


        db.insert(TABLE_BMI,null,values);

        db.close();
    }



    public ArrayList<String>  getAllBmi()
    {
        ArrayList<String> data = new ArrayList<String>();
        String query = "SELECT * FROM " + TABLE_BMI;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Log.v("MyBooks", cursor.getString(0) + "-" + cursor.getString(1));
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
        db.delete(TABLE_BMI,null,null);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
