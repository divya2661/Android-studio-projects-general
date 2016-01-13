package com.example.dibbi.aaditya_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by dibbi on 12/1/16.
 */
public class SqliteHandler extends SQLiteOpenHelper{

    private static final String TAG = SqliteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "student_info";
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String CONTACT = "contact_no";
    private static final String FACEBOOK_LINK = "facebook_link";
    private static final String TWITTER_LINK = "twitter_link";
    private static final String DOB = "dob";
    private static final String ADDRESS = "address";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";


    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       // Log.d("oncreate", "sqlitehandler");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + EMAIL + " TEXT UNIQUE,"
                + CONTACT+" TEXT,"
                + FACEBOOK_LINK + " TEXT,"
                + TWITTER_LINK + " TEXT,"
                + DOB + " DATE,"
                +ADDRESS + " TEXT,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";

        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database table user created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String first_name,String last_name, String email,String contact,
                        String fb_link,String twt_link,
                        String dob,String address, String uid, String created_at) {

        Log.d("database","coming to addUser");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, first_name); // Name
        values.put(LAST_NAME, last_name); // Name
        values.put(EMAIL, email); // Email
        values.put(CONTACT,contact);
        values.put(FACEBOOK_LINK,fb_link);
        values.put(TWITTER_LINK,twt_link);
        values.put(DOB,dob);
        values.put(ADDRESS,address);
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        Log.d("database", "coming here");
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("first_name", cursor.getString(1));
            user.put("last_name", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("contact", cursor.getString(4));
            user.put("facebook_link", cursor.getString(5));
            user.put("twitter_link", cursor.getString(6));
            user.put("dob", cursor.getString(7));
            user.put("address", cursor.getString(8));
            user.put("uid", cursor.getString(9));
            user.put("created_at", cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
