package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * main page of nutrition search
 */
public class Nutrition extends Activity {

    private static final String ACTIVITY_NAME = "Nutrition";


        private Button foodNutrition, goHome;
        private EditText searchText;
        private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
        /**
         * retrieve object related to layout
         */
        foodNutrition = findViewById(R.id.nutritionButton);
        goHome = findViewById(R.id.goHomeButton);
        progressBar = findViewById(R.id.FoodProgressBar);
        searchText = findViewById(R.id.FoodSearchText);



        Toast.makeText(Nutrition.this,"Nutrition is the best!", Toast.LENGTH_LONG).show();

        /**
         * button to jump back to home page of project
         * register intent for button action
         */
        goHome.setOnClickListener(e->{
            AlertDialog dialog = new AlertDialog.Builder(Nutrition.this)
                    .setTitle("Notice!")
                    .setMessage("You are leaving the page!")
                    /**
                     * cancel the action, stay at the current page
                     */
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    })
                    /**
                     * confirmed action, get back to home-page
                     */
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Nutrition.this, MainActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        });

        /**
         * start to search the DB of food nutrition
         * just show a message for now
         */
        foodNutrition.setOnClickListener((v)-> {
                Snackbar.make( v, "show nutrition List", Snackbar.LENGTH_LONG).show();

        });



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
