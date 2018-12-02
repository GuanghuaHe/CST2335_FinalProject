package com.example.guanghuahe.cst2335_finalmilestone1.movie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * help class
 */
public class BitmapConverter {
    private static File dir;

    /**
     * get bitmap from urlpath (String) (from API)
     * @param urlStr
     * @return
     */

    public static Bitmap getBitmapFromUrl(String urlStr){

        URL url = null;
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

        }catch (MalformedURLException me){
           me.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
           if(connection != null) connection.disconnect();
        }
        return bitmap;
    }

    /**
     * convert bitmap into byte[]  used to save in database
     * @param bitmap
     * @return
     */
    public static byte[] getByte(Bitmap bitmap){
        if(bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();

    }

    public static Bitmap bytes2Bimap(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        } else {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
    }







}
