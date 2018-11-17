package com.example.guanghuahe.cst2335_finalmilestone1.movie.activities;


import android.app.AlertDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.util.Xml;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.EditorInfo;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.CBC;
import com.example.guanghuahe.cst2335_finalmilestone1.MainActivity;
import com.example.guanghuahe.cst2335_finalmilestone1.Nutrition;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.MovieAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Movie extends AppCompatActivity {
    /**
     * get app name which is type of string
     */
    protected static final String TAG = Movie.class.getSimpleName();
    /**
     * searchList
     */
    private static final List<MovieDTO> movies = new ArrayList<>();
    /**
     * declaration of View components including back-home button, add-title search button
     * search entry of editText view
     * progressBar to show process status
     */

    private Button clearText, searchButton, historyButton;
    private EditText movieName;
    private ListView listView;
    private ProgressBar movieProgressBar;
    private MyTask mTask;
    private MovieAdapter movieAdapter;

    private DatabaseHelper movieDB; // 数据库只存历史记录， 每次查询出来的清单，经过选择后自动进入数据库










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        clearText = findViewById(R.id.clear_txt);
        searchButton = findViewById(R.id.buttonSearchMovie);
        historyButton = findViewById(R.id.buttonSearchHistory);
        movieName = findViewById(R.id.editText);
        movieProgressBar = findViewById(R.id.MovieProgressBar);

        /**
         * construct Adapter
         */

        movieAdapter = new MovieAdapter(Movie.this, R.layout.list_view_item,movies);
        listView = findViewById(R.id.list_movie_search);
        listView.setAdapter(movieAdapter);



        /**
         * set ActionBar
         *
         */
       // setSupportActionBar(toolbar);

        /**
         * toast to show the message
         */
        Toast.makeText(this, "enter movie title to search", Toast.LENGTH_SHORT).show();

        /**
         * add input to search list
         * for mileStone 1 , just use it to call detail page.
         */
        searchButton.setOnClickListener(e->{
            Snackbar.make(e, "start to search", Snackbar.LENGTH_LONG).show();
            performSearch(movieName.getText().toString());
            /*Intent toDetail = new Intent(this, MovieDetail.class);
            startActivity(toDetail);*/
        });

        /**
         * set progressBar visibility equal to true
         */
        movieProgressBar.setVisibility(View.VISIBLE);

        /**
         * editText entry => start search
         */
        movieName = (EditText)findViewById(R.id.editText);
        movieName.setOnEditorActionListener((v, actionId, event) ->{
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(movieName.getText() != null && movieName.getText().length() > 0)
                        performSearch(movieName.getText().toString().trim());
                    return true;
                }
                return false;

        });

        clearText.setOnClickListener( (v) ->movieName.setText(""));
   }

    /**
     * load tool bar
     * @param menu
     * @return
     */
   @Override
    public boolean onCreateOptionsMenu(Menu menu){
           getMenuInflater().inflate(R.menu.moive_menu, menu);
           return true;
   }

    /**
     * set event hanlder for tool bar including Home, OCTransport, Nutrition,CBC NEWS
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemID = menuItem.getItemId();

        switch (menuItemID) {
            case R.id.movieItem1:
                AlertDialog dialog = new AlertDialog.Builder(Movie.this)
                        .setTitle("Notice")
                        .setMessage("go back to home page")

                        /**
                         * set yes/no button on the alertDialog
                         */
                        .setNegativeButton("Cancel", (dg, which) -> dg.dismiss())// cancel current operate
                        .setPositiveButton("OK", (dg, which) -> {
                            /**
                             * go back to the main page of final project
                             */
                            Intent intent = new Intent(Movie.this, MainActivity.class);
                            startActivity(intent);
                            dg.dismiss();
                        }).create();

                dialog.show();

                break;
            case R.id.movieItem2:
                Intent intent2 = new Intent(Movie.this, OCTranspo.class);
                startActivity(intent2);
                break;
            case R.id.movieItem3:
                Intent intent3 = new Intent(Movie.this, Nutrition.class);
                startActivity(intent3);
                break;
            case R.id.movieItem4:
                Intent intent4 = new Intent(Movie.this, CBC.class);
                startActivity(intent4);
                break;
        }
        return true;
    }




    /**
     * Movie XML url
     */

    private static final String URL = "http://www.omdbapi.com/?type=movie&plot=full&apikey=ce73c386&r=xml&s=";
    private static final String URL_ID = "http://www.omdbapi.com/?plot=full&apikey=ce73c386&r=xml&i=";


    private void performSearch(String title) {
        Log.i(TAG, "step1");
        movies.clear();
        mTask = new MyTask();
        mTask.execute(URL+title);
        movieName.setText("");
        movieName.setEnabled(false);

    }

     class MyTask extends AsyncTask<String, Integer, String> {




        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");

        }

         /**doInBackground  loading movie Object from URL search (can not modify UI within this method)
          *
          * @param params
          * @return
          */
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                Log.i(TAG, "step2" + url +"");
                HttpURLConnection hcn = (HttpURLConnection)url.openConnection();
                hcn.setRequestMethod("GET");
                hcn.setReadTimeout(10000 /* milliseconds */);
                hcn.setConnectTimeout(15000 /* milliseconds */);

                XmlPullParser parser = Xml.newPullParser();
                //we don't use namespaces
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                Log.i(TAG, "step2.3");
                parser.setInput(hcn.getInputStream(), null);
                Log.i(TAG, "step2.5");
                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    MovieDTO movie = new MovieDTO();
                    if(eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("result")) {
                            movie.setMovieName(parser.getAttributeValue(null, "title"));
                            publishProgress(25);
                           movie.setYear(parser.getAttributeValue(null, "year"));
                            publishProgress(50);
                            movie.setImDbId(parser.getAttributeValue(null, "imDbId"));
                            publishProgress(75);
                            movie.setType(parser.getAttributeValue(null, "type"));
                            movie.setPosterLink(parser.getAttributeValue(null, "poster"));
                            movie.setImage(getBitmapFromUrl(parser.getAttributeValue(null, "poster")));
//                            //debug
                          Log.i(TAG, "step3"+parser.getAttributeValue(null, "title"));
                            movies.add(movie);
                        }

                    }
                    eventType = parser.next();
                    /**
                     * for each movie insert into DB. 这只是每次搜索的结果，不存入数据库。
                     */
                    //movieDB.insertMovie(movie);
                    /**
                     * show the list currently
                     */

                    //debug
                      //  Log.i(TAG, "step3"+movieAdapter.getMovie(0).getImDbId());
                }

                hcn.disconnect();


            }catch (MalformedURLException e){
                e.printStackTrace();

            }catch (XmlPullParserException xe){
                xe.printStackTrace();
            }catch(FileNotFoundException fe){
                fe.printStackTrace();
            }
            catch (IOException ie){
                ie.printStackTrace();
            }
            return null;
        }

        @Override
        protected  void onProgressUpdate(Integer ... value){
            movieProgressBar.setVisibility(View.VISIBLE);
            movieProgressBar.setProgress(value[0]);
        }

        @Override
        protected  void onPostExecute(String args){
            movieProgressBar.setVisibility(View.INVISIBLE);
            /**
             * set UI to show the moive info (title, year, post)
             *
             */
            movieAdapter.notifyDataSetChanged();
        }


        private boolean isExist(String file){
            return getBaseContext().getFileStreamPath(file).exists();
        }
         /**
          * get bitmap from urlpath (String)
          * @param urlStr
          * @return
          */

         public Bitmap getBitmapFromUrl(String urlStr){
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

             }catch (Exception e){
                 e.printStackTrace();
             }finally {
                 connection.disconnect();
             }
             return bitmap;
         }
    }














    /**
     * overridden methods with built-in logging.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

}
