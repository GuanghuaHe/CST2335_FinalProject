package com.example.guanghuahe.cst2335_finalmilestone1.Food;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

/**
 * Lloads fragment for favorites list
 */
public class FoodFavorites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_favorites);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.food_favorites,new FoodFragment());
        fragmentTransaction.commit();
    }
}