import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by redpower1989 on 4/5/2015.
 */
public class MySQLLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HealthDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BMI = "BMI";
    private static final String TABLE_CALORIES_BURNED="BURNED";
    private static final String TABLE_CALORIES_CONSUMED = "CONSUMED";


    private static final String KEY_ID = "id";
    private static final String KEY_BMI = "bmi";
    private static final String KEY_BURNED = "burned";
    private static final String KEY_CONSUMED = "consumed";


    public MySQLLiteHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_BMI_TABLE = "CREATE TABLE" + TABLE_BMI +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "bmi TEXT)";
        sqLiteDatabase.execSQL(CREATE_BMI_TABLE);

        String CREATE_BURNED_TABLE = "CREATE TABLE" + TABLE_CALORIES_BURNED +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "burned TEXT)";
        sqLiteDatabase.execSQL(CREATE_BURNED_TABLE);

        String CREATE_CONSUMED_TABLE = "CREATE TABLE" + TABLE_CALORIES_CONSUMED +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "consumed TEXT)";
        sqLiteDatabase.execSQL(CREATE_CONSUMED_TABLE);




    }

    public void insertConsumed(String consumed)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONSUMED, consumed);


        db.insert(TABLE_CALORIES_CONSUMED,null,values);

        db.close();
    }

    public void insertBmi(String bmi)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BMI, bmi);


        db.insert(TABLE_BMI,null,values);

        db.close();
    }

    public void insertBunred(String burned)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BURNED, burned);

        db.insert(TABLE_CALORIES_BURNED,null,values);

        db.close();
    }

    public void getAllBmi()
    {
        String query = "SELECT * FROM " + TABLE_BMI;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Log.v("MyBooks", cursor.getString(0) + "-" + cursor.getString(1));
            }
            while(cursor.moveToNext());
        }

        db.close();

    }

    public void getAllBunred()
    {
        String query = "SELECT * FROM " + TABLE_CALORIES_BURNED;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Log.v("MyBooks", cursor.getString(0) +"-"+ cursor.getString(1));
            }
            while(cursor.moveToNext());
        }

        db.close();

    }

    public void getAllConsumed()
    {
        String query = "SELECT * FROM " + TABLE_CALORIES_CONSUMED;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do
            {
                Log.v("MyBooks", cursor.getString(0) +"-"+ cursor.getString(1));
            }
            while(cursor.moveToNext());
        }

        db.close();

    }
    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BMI,null,null);
        db.delete(TABLE_CALORIES_BURNED,null,null);
        db.delete(TABLE_CALORIES_CONSUMED,null,null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
