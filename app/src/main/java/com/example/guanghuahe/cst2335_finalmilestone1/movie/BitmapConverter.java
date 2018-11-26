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

public class BitmapConverter {
    private static File dir;

    /**
     * get bitmap from urlpath (String)
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




    /**
     * save bitmap locally
     * @param bitmap
     * @param fileName
     */
    public static void saveBitmap(Bitmap bitmap, String fileName){
         dir = new File("image");//设置保存路径
        try (FileOutputStream out = new FileOutputStream(new File(dir, "bitmap" + fileName+".png"))) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            Log.i("BitmapConverter","save image successfully");
        } catch (IOException e) {
            Log.i("BitmapConverter","save image failed");
            e.printStackTrace();
        }
        /*File avaterFile = new File(dir, fileName+".jpg");//设置文件名称

                if(avaterFile.exists()){
                     avaterFile.delete();
                }
                try {
                 avaterFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(avaterFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                     fos.flush();
                    fos.close();
                    } catch (IOException e) {
                     e.printStackTrace();
                     }*/


    }


    public static Bitmap loadLocalImage(String fileName) {

        try {
            FileInputStream fis = new FileInputStream(dir + "/" + "bitmap" + fileName + ".png");
            Log.i("BitmapConverter","load local image successfully");
            return BitmapFactory.decodeStream(fis);


        } catch (Exception e) {
            Log.i("BitmapConverter","load local image failed   =======null");
            e.printStackTrace();
        }

return null;
    }

}
