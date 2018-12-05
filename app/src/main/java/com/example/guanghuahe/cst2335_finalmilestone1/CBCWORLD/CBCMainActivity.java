package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;

//Import Statements
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.guanghuahe.cst2335_finalmilestone1.R;


public class CBCMainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new NewsListFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }
}