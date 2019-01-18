package com.example.fahri.firstdb.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "data.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "data_table";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_HOMEWORK = "HOMEWORK";
    public static final String COLUMN_LECTURE = "LECTURE";
    public static final String COLUMN_DEADLINE = "DEADLINE";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COLUMN_DAY+" TEXT, " +COLUMN_HOMEWORK+" TEXT, "+COLUMN_LECTURE+" TEXT, " +
                ""+COLUMN_DEADLINE+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData (String day, String homework, String lecture, String deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DAY, day);
        contentValues.put(COLUMN_HOMEWORK, homework);
        contentValues.put(COLUMN_LECTURE, lecture);
        contentValues.put(COLUMN_DEADLINE, deadline);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME+"\norder by "+COLUMN_ID+" desc",null);
        return res;
    }

    public boolean updateData(String  id, String day, String homework, String lecture, String deadline){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_DAY, day);
        contentValues.put(COLUMN_HOMEWORK, homework);
        contentValues.put(COLUMN_LECTURE, lecture);
        contentValues.put(COLUMN_DEADLINE, deadline);
        db.update(TABLE_NAME,contentValues, "id = ?",new String []{id});
        return true;
    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
}
