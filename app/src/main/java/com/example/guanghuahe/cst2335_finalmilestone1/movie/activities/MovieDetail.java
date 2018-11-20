package com.example.guanghuahe.cst2335_finalmilestone1.movie.activities;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
 * just show the template for mileStone 1.
 * logic business will update later
 */
public class MovieDetail extends AppCompatActivity {

    protected static final String TAG = "MovieDetail";
    private static final String URL_ID = "http://www.omdbapi.com/?plot=full&apikey=ce73c386&r=xml&i=";
    private MovieDTO active;
    private Button save;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
      //  movieProgressBar = findViewById(R.id.MovieProgressBar);

        active =  (MovieDTO) getIntent().getParcelableExtra("Movie");

        databaseHelper = Movie.databaseHelper;

        /**
         * check local first
         */
           String id = active.getImDbId();

        if( databaseHelper.readMovieDetail(id) == null) {

            MyTask myTask = new MyTask();
            myTask.execute(URL_ID + id);
        }
        save = findViewById(R.id.save_movie);
        save.setOnClickListener(e-> {

            databaseHelper.insertMovie(active);

            finish();
        });

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


        //onPreExecute方法用于在执行后台任务前做一些UI操作
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

                            active.setReleasedDate(parser.getAttributeValue(null, "released"));

                            active.setRatings_imDb(parser.getAttributeValue(null, "imdbRating"));

                            active.setRuntime(parser.getAttributeValue(null, "runtime"));

                            active.setGener(parser.getAttributeValue(null, "genre"));

                            active.setDirector(parser.getAttributeValue(null, "director"));

                            active.setActors(parser.getAttributeValue(null, "actors"));

                            active.setImage(BitmapConverter.getBitmapFromUrl(parser.getAttributeValue(null, "poster")));
                            Log.i(TAG, "   Image  isExist ?  =" +(active.getImage() == null));

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
          //  movieProgressBar.setVisibility(View.VISIBLE);
          //  movieProgressBar.setProgress(value[0]);
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