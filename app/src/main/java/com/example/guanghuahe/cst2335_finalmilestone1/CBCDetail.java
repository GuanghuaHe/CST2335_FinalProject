package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CBCDetail extends Activity {

    protected static final String ACTIVITY_NAME = "CBCDetail";
    protected static final String url = "https://www.cbc.ca/cmlink/rss-world";

    private Button button;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbcdetail);

        button = findViewById(R.id.cbc_detail_button);
        textView = findViewById(R.id.cbc_detail_TextView);
        imageView = findViewById(R.id.cbc_detail_image);

        CBCQuery CBC = new CBCQuery();
        CBC.execute("https://www.cbc.ca/cmlink/rss-world");

    }


    public class CBCQuery extends AsyncTask<String, Integer, String> {
        int CBCTitle, read;
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("https://www.cbc.ca/cmlink/rss-world");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                InputStream stream = conn.getInputStream();

                XmlPullParser xpp = Xml.newPullParser();
                xpp.setInput(stream, null);
                CBCTitle = xpp.getAttributeCount();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                    read++;

                }

            } catch (MalformedURLException urlEX) {
                urlEX.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


            Log.i(ACTIVITY_NAME, "In doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(String string){
            textView.setText(stringBuilder);
        }
    }
}
