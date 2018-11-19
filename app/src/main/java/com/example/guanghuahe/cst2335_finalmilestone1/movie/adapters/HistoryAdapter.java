package com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<MovieDTO> {
    protected List<MovieDTO> historyList;
    private int resourceID;


    public HistoryAdapter(Context context, int listItemViewId, List<MovieDTO> movieDTOList) {

        super(context, listItemViewId, movieDTOList);
        historyList = movieDTOList;
        resourceID = listItemViewId;
    }



    public View getView(int position, View convertView, ViewGroup parent){
        MovieDTO movie = getItem(position);

        View result =LayoutInflater.from(getContext()).inflate(resourceID, null);




        TextView title = result.findViewById(R.id.history_list_item_title);
        title.setText(movie.getMovieName());
        TextView year = result.findViewById(R.id.history_movie_year);
        year.setText(movie.getYear());
        ImageView image = result.findViewById(R.id.history_list_item_image);
        Bitmap bm = BitmapConverter.getBitmapFromUrl(movie.getPosterLink());
        Log.i("AA", "YOU MEI YOU TU A" + movie.getPosterLink());
        image.setImageBitmap(bm);

        return result;
    }


}
