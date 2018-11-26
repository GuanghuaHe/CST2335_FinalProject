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

/**
 * this Adapter just for searching.
 *
 * loading the movies from url to List view locally
 *
 * click item to see detail page and then ready to save.
 *
 *
 * a movie as long as saved will be stored in SQLiteDatabse for later user
 */
public  class MovieAdapter extends ArrayAdapter<MovieDTO> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    protected List<MovieDTO> movieList;
    private int resourceId;


    public MovieAdapter( Context context, int listItemViewId, List<MovieDTO> movieDTOList) {
        super(context, listItemViewId, movieDTOList);
        resourceId = listItemViewId;
        movieList = movieDTOList;

    }



    public MovieDTO getMovie(int indx){
        return movieList.get(indx);
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




        return result;
    }


}