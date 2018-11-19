package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.HistoryAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;


import java.util.List;

public class HistoryFragment extends Fragment {

    private List<MovieDTO> historyList;
    private ListView historyView;
    private Context mainActivity;
    private HistoryAdapter historyAdapter;
    private DatabaseHelper DBhelper;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DBhelper= new DatabaseHelper(mainActivity);
        historyList = DBhelper.getAllMovies();


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View listLayout = inflater.inflate( R.layout.listview,null);

        historyView = listLayout.findViewById(R.id.list_view);


        historyAdapter = new HistoryAdapter(mainActivity,R.layout.hitstory_list_item, historyList);
        historyView.setAdapter(historyAdapter);
        if(historyList.size() == 0) historyView.setVisibility(View.INVISIBLE);


        return listLayout;
    }

    public HistoryAdapter getHistoryAdapter(){
        return this.historyAdapter;
    }



}
