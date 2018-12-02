package com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.MovieDetail;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;


import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class HistoryAdapter extends ArrayAdapter<MovieDTO> {
    private static final String TAG = "HISTORYADAPTER";
    private DatabaseHelper databaseHelper;
    protected List<MovieDTO> historyList;
    private Context mainActivity;
    private int resourceID;

    /**
     * constructor to initial Objects in need.
     * @param context
     * @param listItemViewId
     * @param movieDTOList
     */
    public HistoryAdapter(Context context, int listItemViewId, List<MovieDTO> movieDTOList) {

        super(context, listItemViewId, movieDTOList);
        historyList = movieDTOList;
        resourceID = listItemViewId;
        databaseHelper = Movie.databaseHelper;
        mainActivity = context;
    }


    /**
     * load history list view item which hold the brief info of a movie
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent){
        MovieDTO movie = getItem(position);

        View result =LayoutInflater.from(getContext()).inflate(resourceID, null);


        TextView title = result.findViewById(R.id.history_list_item_title);
        title.setText(movie.getMovieName());
        TextView year = result.findViewById(R.id.history_movie_year);
        year.setText(movie.getYear());
        ImageView image = result.findViewById(R.id.history_list_item_image);
        image.setImageBitmap(movie.getImage());


        /**
         * remove a item: a, from list; b. from Database; c. notify history Adapter.
         */
        Button remove = result.findViewById(R.id.removeItem);
        remove.setOnClickListener(e->{
            historyList.remove(movie);

            databaseHelper.deleteMovie(movie);
            Log.i(TAG, "AFTER  :  movie id:  -->" + movie.getImDbId());
            notifyDataSetChanged();

        });

        return result;
    }


    public MovieDTO getMovie(int indx){
        return historyList.get(indx);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
