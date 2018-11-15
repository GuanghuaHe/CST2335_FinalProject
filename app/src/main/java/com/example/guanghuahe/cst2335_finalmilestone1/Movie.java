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
    /**
     * get app name which is type of string
     */
    private static final String ACTIVITY_NAME = Movie.class.getSimpleName();
    /**
     * declaration of View components including back-home button, add-title search button
     * search entry of editText view
     * progressBar to show process status
     */
    private Button goHome, searchButton;
    private EditText movieName;
    private ProgressBar movieProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Log.i(ACTIVITY_NAME, "In onCreate()");


        /**
         * assign values to references with objects from View of LayOut
         */
        goHome = findViewById(R.id.homeButton);
        searchButton = findViewById(R.id.buttonSearchMovie);
        movieName = findViewById(R.id.editText);
        movieProgressBar = findViewById(R.id.MovieProgressBar);

        /**
         * toast to show the message
         */
        Toast.makeText(this, "enter movie title to search", Toast.LENGTH_SHORT).show();

        /**
         * home button to go home page
          */
        goHome.setOnClickListener(e->{
            AlertDialog dialog = new AlertDialog.Builder(Movie.this)
                    .setTitle("Notice")
                    .setMessage("go back to home page")

                    /**
                     * set yes/no button on the alertDialog
                     */
                    .setNegativeButton("Cancel", (dg, which)->dg.dismiss())// cancel current operate
                    .setPositiveButton("OK",(dg,which)->{
                        /**
                         * go back to the main page of final project
                         */
                        Intent intent = new Intent(Movie.this, MainActivity.class);
                        startActivity(intent);
                        dg.dismiss();}).create();

            dialog.show();
        });

        /**
         * add input to search list
         * for mileStone 1 , just use it to call detail page.
         */
        searchButton.setOnClickListener(e->{
            Snackbar.make(e, "start to search", Snackbar.LENGTH_LONG).show();
            Intent toDetail = new Intent(this, MovieDetail.class);
            startActivity(toDetail);
        });

        /**
         * set progressBar visibility equal to true
         */
        movieProgressBar.setVisibility(View.VISIBLE);
   }

    /**
     * overridden methods with built-in logging.
     */
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
