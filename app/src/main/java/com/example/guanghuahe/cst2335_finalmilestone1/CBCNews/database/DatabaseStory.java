package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database;

import android.provider.BaseColumns;

final  class DatabaseStory {

    private DatabaseStory() {}
    /**
     * Class database empty
     */
    static class StoryEntry implements BaseColumns {
        /**
         * Names  of table  columns
         */
        static final String TABLE_NAME = "story";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_LINK = "link";
        static final String COLUMN_NAME_PUB_DATE = "pubDate";
        static final String COLUMN_NAME_AUTHOR = "author";
        static final String COLUMN_NAME_DESCRIPTION = "description";
    }

}
