package com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;


import java.util.ArrayList;
import java.util.List;

public  class MovieAdapter extends ArrayAdapter<MovieDTO> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    protected List<MovieDTO> movieList;
    private int resourceId;
    private Button addMovie;

    public MovieAdapter( Context context, int listItemViewId, List<MovieDTO> movieDTOList) {
        super(context, listItemViewId, movieDTOList);
        resourceId = listItemViewId;
        movieList = movieDTOList;

    }

    public void setMoviesList(List<MovieDTO> list){
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




        TextView title = result.findViewById(R.id.history_list_item_title);
        title.setText(movie.getMovieName());
        TextView year = result.findViewById(R.id.history_movie_year);
        year.setText(movie.getYear());
        ImageView image = result.findViewById(R.id.list_item_image);
        image.setImageBitmap(BitmapConverter.getBitmapFromUrl(movie.getPosterLink()));


        /**
         * add movie from search list
         */
        addMovie = result.findViewById(R.id.addItem);

        addMovie.setOnClickListener(e->Movie.databaseHelper.insertMovie(movie));

        return result;
    }


}