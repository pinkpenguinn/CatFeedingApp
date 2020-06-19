package com.example.catfeedingapp.photomodel;

import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class photo {

    private static final float PREFERRED_WIDTH = 250;  // Images will be resized to this parameters
    private static final float PREFERRED_HEIGHT = 250;
    private String title;
    private String image;


    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_IMAGE = 2;

    public photo(Cursor cursor) { //constructor
        this.title = cursor.getString(COLUMN_TITLE);
        this.image = cursor.getString(COLUMN_IMAGE);
    }

    public photo(String title, Bitmap image) {  //constructor
        this.title = title;
        this.image = bitmapToString(resizeBitmap(image)); //resizes the image and converts it to a String
    }
    //This method accepts a bitmap and compresses it into a image with PNG format
    // and outputs it as an byte array OutputStream, baos
    //The output baos is stored in a byte array named b
    //b is passed to the Base64.encodeToString method which converts the byte data and returns a String
    //This String represents, the String format of the bitmap passed in the method
    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    //This method resizes the bitmap passed to it so that it can comfortable fit in the SQLite database
    //The width and height of the bitmap is retrieved and scaled down by dividing it by 250(the preferred height and width)
    //The new height and width is stored in matrix and the bitmap is created again ny passing the
    //x coordinate of first pixel = 0
    //y coordinate of first pixel = 0
    //Original width and height
    //matrix which the bitmap will be transformed by
    //The resized bitmap will be returned
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



    public String getTitle() {  // returns title of photo
        return this.title;
    }
   // This method accepts a String parameter (String formatted Image) and converts it back into a bitmap
   // The String is decoded back into a byte array and then converted back to a bitmap.
    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public Bitmap getImage() {   // returns image as a bitmap
        return stringToBitmap(this.image);
    }

    public String getImageAsString() { //returns image as it's String format
        return this.image;
    }


}
