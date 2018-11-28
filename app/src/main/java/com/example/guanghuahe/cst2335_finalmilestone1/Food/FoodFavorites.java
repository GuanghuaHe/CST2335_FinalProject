package com.example.guanghuahe.cst2335_finalmilestone1.Food;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

/**
 * Activity for favorites list, loads fragment into the blank frame
 */
public class FoodFavorites extends AppCompatActivity {
    /**
     * On Create method, loads fragment into the blank frame
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_favorites);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        ftrans.replace(R.id.favorites,new FoodFragment()); //load a fragment into the framelayout
        ftrans.commit(); //actually load it
    }
}