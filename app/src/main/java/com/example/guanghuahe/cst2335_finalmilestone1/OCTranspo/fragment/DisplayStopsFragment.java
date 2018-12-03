/**
 * The fragment for listview of octanspo bus stop
 * @Author: Guanghua He
 * @Version: 1.1
 * @Since:1.0
 */

package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

/*

Each Activity must use a fragment somewhere in its graphical interface.
 */

public class DisplayStopsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocstops, container, false);
    }

}