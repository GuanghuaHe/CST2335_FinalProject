package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.MovieDetail;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.MovieAdapter;
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

    private static final String URL = "http://www.omdbapi.com/?plot=full&apikey=ce73c386&r=xml&s=";

    /**
     *  searchView :  layout used to hold a list of result.
     *  searchList: collection for movies (MovieDTO).
     *  movieAdapter: implementation of ArrayAdapter, help to CRUD search list
     */
    private ListView searchView;
    private List<MovieDTO> searchList;
    private MovieAdapter movieAdapter;
    private Context mainActivity;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchList = new ArrayList<>();
        movieAdapter = new MovieAdapter(mainActivity, R.layout.list_view_item,searchList );
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get title from bundle which is sent by main activity
        Bundle bundle = getArguments();
        String title = bundle.getString("TITLE");

        Log.i(TAG, "get " + title + "from bundle");

        // open back system thread to process searching
        new MyTask().execute(URL+title);
        View view = inflater.inflate( R.layout.listview,null);
        searchView = view.findViewById(R.id.list_view);
        searchView.setAdapter(movieAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {



        searchView.setOnItemClickListener((a,b,c,d)-> {

            //拿到选择对象
            MovieDTO MOVIE = movieAdapter.getMovie(c);
            //调用方法读取电影其余信息，完成后跳转detail 页面
            Intent todetail = new Intent(mainActivity, MovieDetail.class);
            todetail.putExtra("MOVIE", MOVIE);
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
                Log.i(TAG, "URL  ====="  + params[0]);
                HttpURLConnection hcn = (HttpURLConnection)url.openConnection();
                hcn.setRequestMethod("GET");
                hcn.setReadTimeout(5000 /* milliseconds */);
                hcn.setConnectTimeout(10000 /* milliseconds */);

                XmlPullParser parser = Xml.newPullParser();


                Log.i(TAG, "GET CONNECTION");


                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                parser.setInput(hcn.getInputStream(), null);


                Log.i(TAG, "already get inputStream" +hcn.getInputStream().toString());

                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    MovieDTO movie = new MovieDTO();
                    if(eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("result")) {
                            movie.setMovieName(parser.getAttributeValue(null, "title"));

                            movie.setYear(parser.getAttributeValue(null, "year"));

                            movie.setImDbId(parser.getAttributeValue(null, "imdbID"));


                            movie.setType(parser.getAttributeValue(null, "type"));

                            movie.setPosterLink(parser.getAttributeValue(null, "poster"));



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

        }

        @Override
        protected  void onPostExecute(String args){

            /**
             * set UI to show the moive info (title, year, post)
             *
             */
            movieAdapter.notifyDataSetChanged();

        }


        private boolean isExist(String file){
            return mainActivity.getFileStreamPath(file).exists();
        }






    }

}
