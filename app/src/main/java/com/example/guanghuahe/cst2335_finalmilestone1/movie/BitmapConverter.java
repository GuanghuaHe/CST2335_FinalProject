package com.example.guanghuahe.cst2335_finalmilestone1.movie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapConverter {


    /**
     * get bitmap from urlpath (String)
     * @param urlStr
     * @return
     */

    public static Bitmap getBitmapFromUrl(String urlStr){
        Log.i("图片地址===========", urlStr);
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
}
