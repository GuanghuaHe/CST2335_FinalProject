package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database;

public class Сonstants {


    /**
     * Static SQL -- create table {@link DatabaseStory.StoryEntry#TABLE_NAME}
     */
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseStory.StoryEntry.TABLE_NAME + " (" +
                    DatabaseStory.StoryEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseStory.StoryEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DatabaseStory.StoryEntry.COLUMN_NAME_LINK + " TEXT," +
                    DatabaseStory.StoryEntry.COLUMN_NAME_PUB_DATE + " TEXT," +
                    DatabaseStory.StoryEntry.COLUMN_NAME_AUTHOR + " TEXT," +
                    DatabaseStory.StoryEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    /**
     * Static SQL -- delete table {@link DatabaseStory.StoryEntry#TABLE_NAME}
     */
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseStory.StoryEntry.TABLE_NAME;
}