package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCTranspo;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

import java.util.ArrayList;

 /**
     * This adapter updates/generates the list view elements
     * This was Largely based on CST2335 – Graphical Interface Programming Lab 4
     */
    public class StationAdapter extends ArrayAdapter<String> {

        private ArrayList<String> stopsList;
        private Context OCTranspo;


        public StationAdapter(Context ctx) {
            super(ctx, 0);
            OCTranspo = ctx;
            stopsList = ((OCTranspo)ctx).getStopsList();
        }

        @Override
        public int getCount() {
            return (stopsList.size());
        }

        @Override
        public String getItem(int position) {
            return stopsList.get(position);
        }

        @Override
        public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {


            View result = LayoutInflater.from(OCTranspo).inflate(R.layout.oc_stop, null);

            TextView stationText = result.findViewById(R.id.station_text);

            stationText.setText(getItem(position));

            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }