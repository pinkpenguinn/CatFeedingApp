package com.example.catfeedingapp.db;
import android.provider.BaseColumns;

public class photoFormat {

    private photoFormat () {

    }

    public class photoEntry implements BaseColumns {
        public static final String TABLE_NAME = "memories";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
    }

}

