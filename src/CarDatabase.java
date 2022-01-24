package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CarDatabase  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CarsData";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Cars";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_TYPE = "type";
    private static final String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
            "    " + COLUMN_BRAND + " varchar(200) NOT NULL ,\n" +
            "    " + COLUMN_MODEL + " varchar(200) NOT NULL,\n" +
            "    " + COLUMN_YEAR + " INTEGER NOT NULL,\n" +
            "    " + COLUMN_COLOR + " varchar(200) NOT NULL,\n" +
            "    " + COLUMN_TYPE + " varchar(200) NOT NULL\n" +
            ");";



    public CarDatabase(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
            db.execSQL(sql);
            onCreate(db);
        }

    }
    boolean addCars(String brand,String model, int year, String color, String type){

        SQLiteDatabase sqlDb = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BRAND, brand);
        cv.put(COLUMN_MODEL, model);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_TYPE, type);

        return sqlDb.insert(TABLE_NAME, null, cv) != -1;

    }

    Cursor getAllCars(){
        SQLiteDatabase sqlDb = getReadableDatabase();
        return sqlDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    Cursor GetDepartment(String dept){
        SQLiteDatabase sqlDb = getReadableDatabase();
        //String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_DEPT+" = ?";
        return sqlDb.rawQuery("select * from students where department = ?", new String[]{dept});


    }

    /*
    boolean updateStudent(int id,String name, String lastname, String dept, int grade){

        SQLiteDatabase sqlDb = getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_LASTNAME,lastname);
        cv.put(COLUMN_DEPT,dept);
        cv.put(COLUMN_GRADE, grade);

        return sqlDb.update(TABLE_NAME,cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;

}
     */


    boolean remove(String brand,String model){
        SQLiteDatabase sqlDb = getWritableDatabase();
        return sqlDb.delete(TABLE_NAME,COLUMN_BRAND +" = ? AND "+ COLUMN_MODEL +  " = ?", new String[]{String.valueOf(brand),model}) == 1 ;
    }


}