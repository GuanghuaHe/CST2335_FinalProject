package com.example.guanghuahe.cst2335_finalmilestone1.movie.activities;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * a instance of this class will reveive a movie from search page
 *
 * use AsyncTask to load Url second time to assemble info totally.
 *
 * then setText to de page showing detail of movie
 *
 */
public class MovieDetail extends AppCompatActivity {

    protected static final String TAG = "MovieDetail";
    private static final String URL_ID = "https://www.omdbapi.com/?apikey=ce73c386&r=xml&i=";
    private MovieDTO active;
    private Button save;
    private DatabaseHelper databaseHelper;
    private ProgressBar historyProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        active =  (MovieDTO) getIntent().getParcelableExtra("Movie");

        databaseHelper = Movie.databaseHelper;

        historyProgressBar = findViewById(R.id.history_ProgressBar);

        /**
         * check local first
         */
        String id = active.getImDbId();
        MovieDTO temp = databaseHelper.readMovieDetail(id);
        /**
         * if selected movie has not exist in DB, or saved from search list by click add button
         * load movie from URL doing in the back process.
         */
        if(temp == null || temp.getSummary() == null) {
            MyTask myTask = new MyTask();
            myTask.execute(URL_ID + id);

            /**
             * if selected movie has exist in DB, load movie from database
             */
        }else{active = temp; startPostDetail();}
        save = findViewById(R.id.save_movie);
        save.setOnClickListener(e-> {databaseHelper.insertMovie(active);



                finish();


        });


        /**
         * give a hint showing how to save locally
         *
         */

        Toast.makeText(this, "click save button to store in DateBase", Toast.LENGTH_LONG).show();

    }




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

    class MyTask extends AsyncTask<String, Integer, String> {


        //onPreExecute, only do some UI operation
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");

        }

        /**
         * doInBackground  loading movie Object from URL search (can not modify UI within this method)
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection hcn = (HttpURLConnection) url.openConnection();
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


                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("movie")) {
                            active.setSummary(parser.getAttributeValue(null, "plot"));
                                publishProgress(20);
                            active.setReleasedDate(parser.getAttributeValue(null, "released"));
                            publishProgress(30);
                            active.setRatings_imDb(parser.getAttributeValue(null, "imdbRating"));
                            publishProgress(40);
                            active.setRuntime(parser.getAttributeValue(null, "runtime"));
                            publishProgress(50);
                            active.setGener(parser.getAttributeValue(null, "genre"));
                            publishProgress(60);
                            active.setDirector(parser.getAttributeValue(null, "director"));
                            publishProgress(70);
                            active.setActors(parser.getAttributeValue(null, "actors"));
                            publishProgress(80);
                            active.setImage(BitmapConverter.getBitmapFromUrl(parser.getAttributeValue(null, "poster")));
                            Log.i(TAG, "   Image  isExist ?  =" +(active.getImage() == null));
                            publishProgress(100);
                        }
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException xe) {
                xe.printStackTrace();
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
           historyProgressBar.setVisibility(View.VISIBLE);
          historyProgressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String args) {
           // movieProgressBar.setVisibility(View.INVISIBLE);
            startPostDetail();
        }


        private boolean isExist(String file) {
            return getBaseContext().getFileStreamPath(file).exists();
        }
    }

    /**
     * display movie details.
     */
    private void startPostDetail(){
        TextView movieName = (TextView) findViewById(R.id.movie_name_value);
        movieName.setText(active.getMovieName());

        TextView movieYear = (TextView) findViewById(R.id.year_value);
        movieYear.setText(active.getYear());

        TextView movieRelease = (TextView) findViewById(R.id.release_value);
        movieRelease.setText(active.getReleasedDate());

        TextView movieGenre = (TextView) findViewById(R.id.genre_value);
        movieGenre.setText(active.getGener());

        TextView movieSummary = (TextView) findViewById(R.id.summary_value);
        movieSummary.setText(active.getSummary());

        TextView movieActors = (TextView) findViewById(R.id.actors_value);
        movieActors.setText(active.getActors());

        TextView movieRatings = (TextView) findViewById(R.id.rating_value);
        movieRatings.setText(active.getRatings_imDb());

        TextView movieType = (TextView) findViewById(R.id.type_value);
        movieType.setText(active.getType());

        TextView movieDirector = (TextView) findViewById(R.id.movie_director_value);
        movieDirector.setText(active.getDirector());

        ImageView imageView = (ImageView) findViewById(R.id.movie_detail_image_View);

        imageView.setImageBitmap(active.getImage());
    }
}
