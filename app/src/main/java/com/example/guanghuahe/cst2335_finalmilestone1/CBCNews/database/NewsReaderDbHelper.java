package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser.Story;

import static com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database.Сonstants.SQL_CREATE_ENTRIES;
import static com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database.Сonstants.SQL_DELETE_ENTRIES;


/**
 * Class contains a useful set of APIs for managing your database.
 * When you use this class to obtain references to your database,
 * the system performs the potentially long-running operations of creating
 * and updating the database only when needed and not during app startup.
 *
 * @link  https://developer.android.com/training/data-storage/sqlite
 */

public class NewsReaderDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "News_Read.db";


    /**
     * Class constructor.
     */
    public NewsReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    /**
     * The method that saved Story in the database
     *
     * @param  story   story  (save Object)
     * @return         id saved story
     */
    public  long insertStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseStory.StoryEntry.COLUMN_NAME_TITLE, story.getTitle());
        values.put(DatabaseStory.StoryEntry.COLUMN_NAME_LINK, story.getLink());
        values.put(DatabaseStory.StoryEntry.COLUMN_NAME_PUB_DATE, story.getPubDate().toString());
        values.put(DatabaseStory.StoryEntry.COLUMN_NAME_AUTHOR, story.getAuthor());
        values.put(DatabaseStory.StoryEntry.COLUMN_NAME_DESCRIPTION, story.getDescription());
        long id = db.insert(DatabaseStory.StoryEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }


    /**
     * The method return counts saved stories in Table
     *
     * @return   counts saved stories
     */
    public long getStoryCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        return DatabaseUtils.queryNumEntries(db, DatabaseStory.StoryEntry.TABLE_NAME);
    }

}
