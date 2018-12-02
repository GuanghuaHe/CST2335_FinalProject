package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.MovieDetail;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.MovieAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * search fragment is used for receiving edit text which typed in edit blank
 * using this string to assemble new URL to ready http://www.omdbapi.com
 * using MovieDTO to store info and put brief info in a item view that will be loaded
 * in list view for main activity.
 */
public class MovieSearchFragment  extends Fragment {

    /**
     * for debugging use
     */
    private static final String TAG = MovieSearchFragment.class.getSimpleName();


    /**
     * Movie XML url
     */

    private static final String URL = "http://www.omdbapi.com/?apikey=ce73c386&r=xml&s=";

    /**
     *  searchView :  layout used to hold a list of result.
     *  searchList: collection for movies (MovieDTO).
     *  movieAdapter: implementation of ArrayAdapter, help to CRUD search list
     */
    private ListView searchView;
    private List<MovieDTO> searchList;
    private MovieAdapter movieAdapter;
    private Context mainActivity;
    private ProgressBar progressBar;


    /**
     * step 1, build relationship with main activity
     * get reference of context
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = context;

    }

    /**
     * step 2 initialize relatives
     * progressBar to show the progress of loading process
     * searchList to hold the list of movies loaded from URL-searching
     * movieAdapter to load movie items,  it will be set on ListView which is fragment created
     * next overridden method called onCreateView.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = Movie.getMovieProgressBar();
        searchList = new ArrayList<>();
        movieAdapter = new MovieAdapter(mainActivity, R.layout.list_view_item,searchList );


    }


    /**
     *before create View for searching list, use bundle to receive the edit text
     * use the text to search url by using AsyncTask object processing in background
     * get view from inflater manager delivered from argument
     * setup Adapter
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get title from bundle which is sent by main activity
        Bundle bundle = getArguments();
        String title = bundle.getString("TITLE");

        Log.i(TAG, "get " + title + " from bundle");


        // config security
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // open back system thread to process searching
        new MyTask().execute(URL+this.convertString(title));
        View view = inflater.inflate( R.layout.movielistview,null);
        searchView = view.findViewById(R.id.movie_list_view);
        searchView.setAdapter(movieAdapter);


        return view;
    }

    /**
     * as long as activity created, set item listener to the list view
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {



        searchView.setOnItemClickListener((parent,b,c,d)-> {

            //get selected item
            MovieDTO MOVIE = movieAdapter.getMovie(c);
            //go to detail
            Intent todetail = new Intent(mainActivity, MovieDetail.class);
            todetail.putExtra("Movie", MOVIE);
            startActivity(todetail);


        });

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * inner class AsyncTask to read info from URLs
     */
    class MyTask extends AsyncTask<String, Integer, String> {

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
            Log.i(TAG, "doInBackground() called");
            try {
                URL url = new URL(params[0]);

                HttpURLConnection hcn = (HttpURLConnection)url.openConnection();
                hcn.setRequestMethod("GET");
                hcn.setReadTimeout(10000 /* milliseconds */);
                hcn.setConnectTimeout(150000 /* milliseconds */);

                XmlPullParser parser = Xml.newPullParser();


                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                parser.setInput(hcn.getInputStream(), null);


                Log.i(TAG, "already get inputStream" +hcn.getInputStream().toString());

                int eventType = parser.getEventType();
                int progressValue = 0;
                while(eventType != XmlPullParser.END_DOCUMENT){
                    MovieDTO movie = new MovieDTO();
                    if(eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("result")) {
                            movie.setMovieName(parser.getAttributeValue(null, "title"));

                            progressBar.setProgress(progressValue ++);

                            movie.setYear(parser.getAttributeValue(null, "year"));
                            progressBar.setProgress(progressValue ++);
                            movie.setImDbId(parser.getAttributeValue(null, "imdbID"));
                            progressBar.setProgress(progressValue ++);
                            movie.setType(parser.getAttributeValue(null, "type"));
                            progressBar.setProgress(progressValue +=5);
                            movie.setPosterLink(parser.getAttributeValue(null, "poster"));
                            progressBar.setProgress(progressValue +=5);
                            movie.setImage(BitmapConverter.getBitmapFromUrl(parser.getAttributeValue(null, "poster")));

                            //add into list
                            searchList.add(movie);


                        }

                    }
                    eventType = parser.next();

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
            progressBar.setProgress(value[0]);
        }

        @Override
        protected  void onPostExecute(String args){

            /**
             * set UI to show the moive info (title, year, post)
             *
             */
            movieAdapter.notifyDataSetChanged();
            Toast.makeText(mainActivity,"click item to see detail and save", Toast.LENGTH_SHORT ).show();
        }






    }

    /**
     * convert string for URL replacement
     * @param s
     * @return
     */
    public String convertString(String s){
        return s.replaceAll(" ", "+");
    }


    @Override
    public void onResume() {
        super.onResume();
        /**
         * when ListView changed, notify related Adapter.
         */
        movieAdapter.notifyDataSetChanged();
    }
}
