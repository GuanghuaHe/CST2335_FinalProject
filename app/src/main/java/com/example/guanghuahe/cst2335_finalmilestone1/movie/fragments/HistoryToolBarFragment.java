package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;


public class HistoryToolBarFragment extends Fragment {
        private Button removeAll, byYear, statistic, byRuntime, byRating;
        private DatabaseHelper DB;
        private HistoryFragment historyFragment;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = Movie.databaseHelper;
        historyFragment = (HistoryFragment) getFragmentManager().findFragmentByTag("historyFragment");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tools = inflater.inflate(R.layout.histroytoolbar,null);
        removeAll  = tools.findViewById(R.id.removeAll);
        byRuntime = tools.findViewById(R.id.sort_byRuntime);
        byYear = tools.findViewById(R.id.sort_byYear);
        byRating = tools.findViewById(R.id.sort_byRating);
        statistic = tools.findViewById(R.id.radio_button);


        removeAll.setOnClickListener(e-> {
                    DB.removeAll();
            historyFragment.updateView();

                });
        byRuntime.setOnClickListener(e->{
            historyFragment.orderBy("runtime");

        });

        byYear.setOnClickListener(e->{
            historyFragment.orderBy("year");

        });

        byRating.setOnClickListener(e->{
            historyFragment.orderBy("rating");

        });

        statistic.setOnClickListener(e->{
            historyFragment.statistic();

        });
        return tools;
    }
}
