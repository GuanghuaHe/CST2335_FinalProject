package com.example.guanghuahe.cst2335_finalmilestone1.movie.activities;


import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.EditorInfo;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.CBC;

import com.example.guanghuahe.cst2335_finalmilestone1.Nutrition;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCTranspo;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.adapters.HistoryAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.database.DatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments.HistoryFragment;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments.HistoryToolBarFragment;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.fragments.MovieSearchFragment;


public class Movie extends AppCompatActivity {

    /**
     * get activity name for debugging use
     */
    protected static final String TAG = Movie.class.getSimpleName();

    public static DatabaseHelper databaseHelper;


    /**
     * declaration of View components including back-home button, add-title search button
     * search entry of editText view
     * progressBar to show process status
     */

    private Button clearText, searchButton, historyButton;
    private EditText editText;
    private  static  ProgressBar movieProgressBar;




    /**
     * fragment management
     */
    private FragmentManager fm;
    private FragmentTransaction ft;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_home);


        databaseHelper = new DatabaseHelper(this);
        //subviews
        clearText = findViewById(R.id.clear_txt);
        searchButton = findViewById(R.id.buttonSearchMovie);
        historyButton = findViewById(R.id.buttonSearchHistory);
        editText = findViewById(R.id.moviesearch_editText);
        movieProgressBar = findViewById(R.id.MovieProgressBar);




        /**
         * Fragment Manager
         */
        fm = getSupportFragmentManager();
        /**
         * show history list as long as activity shows up if history is not empty
         */
        if(databaseHelper.getAllMovies().size() > 0) {
            ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.movie_list_view_layout, new HistoryFragment());
            ft.commit();
        }
        /**
         * editText entry => start search
         */

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        editText = (EditText) findViewById(R.id.moviesearch_editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (editText.getText() != null && editText.getText().length() > 0)
                   /* if(imm.isActive()&&getCurrentFocus()!=null) {
                        if (getCurrentFocus().getWindowToken() != null) {*/
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                  /*      }
                    }*/
                        return true;
            }


            return false;

        });

        /**
         * event handlers
         */
        searchButton.setOnClickListener(e -> {
            movieProgressBar.setVisibility(View.VISIBLE);
            //check input
            if (editText.getText() != null && editText.getText().length() > 0) {

                //show the Snackbar.
                Snackbar.make(e, "start to search", Snackbar.LENGTH_LONG).show();

                //get input from edit text
                String title = editText.getText().toString().trim();

                // get instance of search list fragment
                Fragment searchFragment = new MovieSearchFragment();

                //deliver the title to fragment for later use
                Bundle bundle = new Bundle();
                bundle.putString("TITLE", title);
                searchFragment.setArguments(bundle);

                //replace the text view of main activity with fragment which contained a fresh search-list
                ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.movie_list_view_layout, searchFragment);
                ft.commit();

                editText.setText("");
            } else {
                Snackbar.make(e, "please enter movie title", Snackbar.LENGTH_LONG).show();
            }
        });

        historyButton.setOnClickListener(e -> {
            movieProgressBar.setVisibility(View.GONE);

            // get instance of search list fragment
            Fragment historyFragment = new HistoryFragment();

            Toast.makeText(this, "go to history", Toast.LENGTH_SHORT).show();
            ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.movie_list_view_layout, historyFragment, "historyFragment");

            HistoryToolBarFragment toolBarFragment = new HistoryToolBarFragment();
            Bundle bundle = new Bundle();


            ft.replace(R.id.movie_button_layout, toolBarFragment);
            ft.commit();


        });


        clearText.setOnClickListener((v) -> editText.setText(""));

        /**
         * set progressBar visibility equal to true
         */
        movieProgressBar.setVisibility(View.VISIBLE);
        /**
         * toast to show the message
         */
        Toast.makeText(this, "enter movie title to search", Toast.LENGTH_SHORT).show();
    }


    /**
     * share progressBar with fragments
     *
     */
    public static ProgressBar getMovieProgressBar(){
        return movieProgressBar;
    }




    /**
     * load tool bar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moive_menu, menu);
        return true;
    }

    /**
     * set event hanlder for tool bar including Home, OCTransport, Nutrition,CBC NEWS
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemID = menuItem.getItemId();

        switch (menuItemID) {
            case R.id.movieItem1:
                AlertDialog dialog = new AlertDialog.Builder(Movie.this)
                        .setTitle(getApplicationContext().getString(R.string.movie_about))
                        .setMessage(getApplicationContext().getString(R.string.help))

                        /**
                         * set yes/no button on the alertDialog
                         */
                        .setNegativeButton("OK", (dg, which) -> dg.dismiss())// cancel current operate
                        .create();

                dialog.show();

                break;
            case R.id.movieItem2:
                Intent intent2 = new Intent(Movie.this, OCTranspo.class);
                startActivity(intent2);
                break;
            case R.id.movieItem3:
                Intent intent3 = new Intent(Movie.this, Nutrition.class);
                startActivity(intent3);
                break;
            case R.id.movieItem4:
                Intent intent4 = new Intent(Movie.this, CBC.class);
                startActivity(intent4);
                break;
        }
        return true;
    }


    /**
     * overridden methods with built-in logging.
     */
    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this, "movie saved successfully", Snackbar.LENGTH_LONG).show();
        Log.i(TAG, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

}
