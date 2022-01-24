package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UsersLoginData";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
            "    " + COLUMN_NAME + " varchar(200) NOT NULL ,\n" +
            "    " + COLUMN_LASTNAME + " varchar(200) NOT NULL ,\n" +
            "    " + COLUMN_EMAIL + " varchar(200) NOT NULL ,\n" +
            "    " + COLUMN_PASSWORD + " varchar(200) NOT NULL\n" +
            ");";


    public UserDatabase(Context context) {

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

    boolean addUser(String name, String lastname, String email, String password) {

        SQLiteDatabase sqlDb = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LASTNAME, lastname);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);


        return sqlDb.insert(TABLE_NAME, null, cv) != -1;

    }
    public boolean checkUser(String name,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select NAME, PASSWORD from USERS where NAME='"+ name +"' and PASSWORD='"+ password+"'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }

    Cursor getAllUsers() {
        SQLiteDatabase sqlDb = getReadableDatabase();
        return sqlDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    Cursor GetPassword(String name) {
        SQLiteDatabase sqlDb = getReadableDatabase();
        //String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_DEPT+" = ?";
        return sqlDb.rawQuery("select COLUMN_PASSWORD from Users where COLUMN_NAME = ?", new String[]{name});


    }

    boolean deleteUser(String lastname){
        SQLiteDatabase sqlDb = getWritableDatabase();
        return sqlDb.delete(TABLE_NAME,COLUMN_LASTNAME +" = ? ", new String[]{lastname}) == 1 ;
    }
}
