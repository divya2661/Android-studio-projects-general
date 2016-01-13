package com.example.dibbi.sqlite_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by dibbi on 30/12/15.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employee.db";
    public static final String CONTACT_TABLE_NAME = "contacts";
    public static final String CONTACT_COLUMN_ID = "id";
    public static final String CONTACT_COLUMN_NAME = "name";
    public static final String CONTACT_COLUMN_EMAIL = "email";
    public static final String CONTACT_COLUMN_CITY = "city";
    public static final String CONTACT_COLUMN_PHONE = "phone";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table contacts " +
                "(id integer primary key, name text, email text, city text, phone text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String name,String email,String city, String phone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  =new ContentValues();

        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("city",city);
        contentValues.put("phone", phone);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id="+id+"", null);
        return res;
    }

    public int numberOfRows(){

        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,CONTACT_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name,String email, String city,String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("city",city);
        contentValues.put("phone", phone);

        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );

        return true;
    }

    public Integer deleteContact(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("contacts",
                "id = ?",
                new String[]{Integer.toString(id)}
                );
    }

    public ArrayList<String> getAllContacts(){

        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select *from contacts",null);
        res.moveToFirst();

        while(res.isAfterLast()==false){
            array_list.add(res.getString(res.getColumnIndex(CONTACT_COLUMN_NAME)));
            res.moveToNext();
        }
    return array_list;
    }
}
