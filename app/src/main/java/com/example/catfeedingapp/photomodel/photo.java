package com.example.catfeedingapp.photomodel;

import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class photo {

    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;
    private String title;
    private String image;

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_IMAGE = 2;

    public photo(Cursor cursor) {
        this.title = cursor.getString(COLUMN_TITLE);
        this.image = cursor.getString(COLUMN_IMAGE);
    }

    public photo(String title, Bitmap image) {
        this.title = title;
        this.image = bitmapToString(resizeBitmap(image));
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }



    public String getTitle() {
        return this.title;
    }

    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Bitmap getImage() {
        return stringToBitmap(this.image);
    }

    public String getImageAsString() {
        return this.image;
    }


}
