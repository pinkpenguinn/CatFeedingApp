package com.example.catfeedingapp.db;
import android.provider.BaseColumns;

public class photoFormat {

    private photoFormat () {

    }
    // This class contains the Table and Column names for the Table where the the photos are stored in
    public class photoEntry implements BaseColumns {
        public static final String TABLE_NAME = "memories";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
    }

}

