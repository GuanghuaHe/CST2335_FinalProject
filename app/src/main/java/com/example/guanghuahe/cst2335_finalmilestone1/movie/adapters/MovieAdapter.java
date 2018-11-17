package com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public  class MovieAdapter extends ArrayAdapter<MovieDTO> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    protected List<MovieDTO> movieList;
    private int resourceId;


    public MovieAdapter( Context context, int listItemViewId, List<MovieDTO> movieDTOList) {
        super(context, listItemViewId, movieDTOList);
        resourceId = listItemViewId;
        movieList = movieDTOList;

    }

    public void setMoviesList(List list){
        movieList = (ArrayList<MovieDTO>) list;
    }

    public MovieDTO getMovie(int indx){
        return movieList.get(indx);
    }

    public void setMovie(int indx, MovieDTO m){
        movieList.set(indx,m);
    }




    public View getView(int position, View convertView, ViewGroup parent){
        MovieDTO movie = getItem(position);

        View result =LayoutInflater.from(getContext()).inflate(resourceId, null);




        TextView title = result.findViewById(R.id.list_item_title);
        title.setText(movie.getMovieName());
        TextView year = result.findViewById(R.id.movie_year);
        year.setText(movie.getYear());
        ImageView image = result.findViewById(R.id.list_item_image);


        image.setImageBitmap(movie.getImage());

        return result;
    }


}