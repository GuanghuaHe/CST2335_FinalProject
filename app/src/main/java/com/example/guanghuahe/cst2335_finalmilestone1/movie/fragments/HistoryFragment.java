package com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.MovieDetail;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.HistoryAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HistoryFragment extends Fragment {
    private static final  String TAG = HistoryFragment.class.getSimpleName();
    private List<MovieDTO> historyList;
    private ListView historyView;
    private Context mainActivity;
    private HistoryAdapter historyAdapter;
    private static DatabaseHelper DbHelper;
    private static final String[] tags = {"shortestRuntime", "longestRuntime", "averageRuntime", "ealiestMoive", "lastestMoive", "averageYear"};
    private Integer[] values = new Integer[6];


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DbHelper = Movie.databaseHelper;
        historyList = DbHelper.getAllMovies();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View listLayout = inflater.inflate(R.layout.historylistview, null);

        historyView = listLayout.findViewById(R.id.history_list_view);


        historyAdapter = new HistoryAdapter(mainActivity, R.layout.hitstory_list_item, historyList);
        historyView.setAdapter(historyAdapter);





        if (historyList.size() == 0) historyView.setVisibility(View.INVISIBLE);
        return listLayout;
    }

    /**
     * when DB update
     */
    public void updateView() {
        historyList.clear();
        historyList.addAll(DbHelper.getAllMovies());
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * statistic by different order
     *
     * @param columnName
     */
    public void orderBy(String columnName) {

        switch (columnName) {
            case "runtime":
                historyList.sort(Comparator.comparing(MovieDTO::getRuntime));


                break;
            case "year":
                historyList.sort(Comparator.comparing(MovieDTO::getYear));


                break;
            case "rating":
                historyList.sort(Comparator.comparing(MovieDTO::getRatings_imDb));


                break;
            default:
                throw new IllegalArgumentException("can not find order by: " + columnName);

        }
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        historyView.setOnItemClickListener((a,b,c,d)-> {
            Log.e(TAG, "HISTORYFRAGMENT =====> MovieDetail Activity");
            //get selected item
            MovieDTO MOVIE = historyAdapter.getMovie(c);
            //go to detail
            Intent todetail = new Intent(mainActivity, MovieDetail.class);
            todetail.putExtra("Movie", MOVIE);
            startActivity(todetail);


        });


        super.onActivityCreated(savedInstanceState);
    }


    public void statistic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity).setTitle("History Statistic")
                .setMessage(getSatString()).setPositiveButton("Ok", (bd, v)->{
                    bd.dismiss();
                }).setCancelable(true);

        AlertDialog dialog = builder.show();
    }


    private String getSatString(){
        StringBuilder staInfo = new StringBuilder();
        Log.e(TAG, "runtimes ----------->" + historyList.get(2).getRuntime());
        List<Integer> runtimes =
                historyList.stream().map(MovieDTO::getRuntime).map(s->Integer.parseInt(s.trim().split(" ")[0])).collect(Collectors.toList());

       List<Integer> years =
                historyList.stream().map(MovieDTO::getYear).map(s->Integer.parseInt(s)).collect(Collectors.toList());

       runtimes.sort((a,b) -> a -b);
       values[0] = runtimes.get(0);
       values[1] = runtimes.get(runtimes.size()-1);
       values[2] = average(runtimes);
       years.sort((a,b)->a-b);
       values[3] = years.get(0);
       values[4] = years.get(years.size()-1);
       values[5] = average(years);

        for(int i = 0; i< tags.length; i++){
            staInfo.append(System.lineSeparator()).append(tags[i]).append(":   ").append(values[i]);
        }



        return staInfo.toString();
    }

    private Integer average(List<Integer> years) {
        return  years.stream().reduce(0,(a,b) ->a+b)/years.size();
    }
}
