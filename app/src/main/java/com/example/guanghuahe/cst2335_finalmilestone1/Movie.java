package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Movie extends Activity {

    private static final String ACTIVITY_NAME = Movie.class.getSimpleName();
    private Button goHome, searchButton;
    private EditText movieName;
    private ProgressBar movieProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Log.i(ACTIVITY_NAME, "In onCreate()");


        //get view components
        goHome = findViewById(R.id.homeButton);
        searchButton = findViewById(R.id.buttonSearchMovie);
        movieName = findViewById(R.id.editText);
        movieProgressBar = findViewById(R.id.MovieProgressBar);


        Toast.makeText(this, "enter movie title to search", Toast.LENGTH_SHORT).show();

        // home button to go home page
        goHome.setOnClickListener(e->{
            AlertDialog dialog = new AlertDialog.Builder(Movie.this)
                    .setTitle("Notice")
                    .setMessage("go back to home page")
                    .setNegativeButton("Cancel", (dg, which)->dg.dismiss())
                    .setPositiveButton("OK",(dg,which)->{
                        Intent intent = new Intent(Movie.this, MainActivity.class);
                        startActivity(intent);
                        dg.dismiss();}).create();

            dialog.show();
        });


        searchButton.setOnClickListener(e->{
            Snackbar.make(e, "start to search", Snackbar.LENGTH_LONG).show();
            Intent toDetail = new Intent(this, MovieDetail.class);
            startActivity(toDetail);
        });


        movieProgressBar.setVisibility(View.VISIBLE);
   }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
