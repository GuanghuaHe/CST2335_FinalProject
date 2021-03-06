package com.example.guanghuahe.cst2335_finalmilestone1.Food;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * setting up database for the food activity
 */
public class FoodDatabaseHelper extends SQLiteOpenHelper {
    /**
     * Database variables
     */
    private static final String ACTIVITY_NAME = "FoodDatabaseHelper";
    static String DATABASE_NAME = "Favorites.db";
    static int VERSION_NUM = 1;
    final static String KEY_ID = "_id";
    final static String KEY_LABEL = "Label";
    final static String KEY_CALORIES = "Calories";
    final static String KEY_FAT = "Fat";
    final static String KEY_CARBS = "Carbs";
    final static String TABLE_NAME = "Favorites";
    final static String creatTable = "CREATE TABLE " + TABLE_NAME +"("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ KEY_LABEL +" TEXT NOT NULL, "+ KEY_CALORIES +" TEXT NOT NULL, "+ KEY_FAT +" TEXT NOT NULL, " + KEY_CARBS +  " TEXT NOT NULL);";


    /**
     * Construct database
     * @param context
     */
    public FoodDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION_NUM);
    }

    /**
     * creates the table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "OnCreate");
        db.execSQL(creatTable);
    }

    /**
     * drops old version and upgrade to new version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("FoodDatabaseHelper", "oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Old version = " + oldVersion + " New version = " + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}