/**
 * The SQLite database helper for Octranspo activity and OCRoute activity
 * @Author: Guanghua He
 * @Version: 1.1
 * @Since:1.0
 */


package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guanghua He on 2018-10-16.
 */

public class OCDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "stations.db";
    public static final int VERSION_NUM = 1;

    public static final String TABLE_NAME = "stations";
    public static final String STATION_NO = "station_number";
    public static final String STATION_NAME = "station_name";


    public static final String TABLE_NAME_ROUTES = "routes";
    public static final String ROUTE_NO = "route_number";

    public OCDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATION_NO + " text, " + STATION_NAME +  " text);");

        db.execSQL("CREATE TABLE " + TABLE_NAME_ROUTES + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + ROUTE_NO +  " text);");

        Log.i("OCDatabaseHelper", "Calling onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES);
        Log.i("OCDatabaseHelper", "看看版本号是啥 Execusing onUpgrade(), oldVersion="
                + oldVer + ". newVersion=" + newVer + ".");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES);
        Log.i("OCDatabaseHelper", "Calling onDowngrade(), oldVersion="
                + oldVer + ". newVersion=" + newVer + ".");
        onCreate(db);
    }

}
