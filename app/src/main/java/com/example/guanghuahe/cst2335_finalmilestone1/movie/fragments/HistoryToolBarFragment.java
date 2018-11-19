package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.HistoryAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;


public class HistoryToolBarFragment extends Fragment {
        private Button removeAll, byYear, byRuntime, byRating;
        private DatabaseHelper DB;
        private HistoryAdapter historyAdapter;
        private Context mainActivity;
        //private Fragment historyListFragment;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DatabaseHelper(mainActivity);
        historyAdapter = new HistoryFragment().getHistoryAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tools = inflater.inflate(R.layout.histroytoolbar,null);
        removeAll  = tools.findViewById(R.id.removeAll);
        byRuntime = tools.findViewById(R.id.sort_byRuntime);
        byYear = tools.findViewById(R.id.sort_byYear);
        byRating = tools.findViewById(R.id.sort_byRating);


        removeAll.setOnClickListener(e->{
                DB.removeAll();
            if(historyAdapter != null)historyAdapter.notifyDataSetChanged();
        });

        byRuntime.setOnClickListener(e->{

        });
        return tools;
    }
}
