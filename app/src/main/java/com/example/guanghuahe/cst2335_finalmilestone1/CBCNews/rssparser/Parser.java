package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import us.dasha.newsreader.util.Utils;

/**
 * Class parsing  XML  in  background thread
 *
 *
 */

public class Parser  extends AsyncTask<String , Void,String> implements Observer {

    private XMLParser xmlParser;
    private static ArrayList<Story> stories = new ArrayList<>();

    private OnTaskCompleted onTaskCompleted;

    public  Parser(){
        xmlParser = new XMLParser();
        xmlParser.addObserver(this);
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream response = conn.getInputStream();
            return  Utils.convertStreamToString(response);
        } catch (IOException exception) {
            exception.printStackTrace();
            onTaskCompleted.onError();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if (!"".equals(s)){
            try {
               xmlParser.parseXML(s);
            }catch (Exception exception){
                exception.printStackTrace();
                onTaskCompleted.onError();
            }
        }else{
            onTaskCompleted.onError();
        }
    }

    @Override
    public void update(Observable o, Object data) {
        stories = (ArrayList<Story>) data;
        onTaskCompleted.onTaskCompleted(stories);
    }

    /**
     * Register a callback to be invoked when string parsed
     *
     * @param onTaskCompleted  the callback that will run
     */
    public void onFinish(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    public interface  OnTaskCompleted{

        /**
         *
         *
         * @param stories
         */
        void onTaskCompleted(ArrayList<Story> stories);

        /**
         * The method will be executed if something is not executed correctly
         */
        void onError();
    }
}
