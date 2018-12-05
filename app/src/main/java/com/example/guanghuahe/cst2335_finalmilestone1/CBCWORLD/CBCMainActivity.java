package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;

//Import Statements
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.guanghuahe.cst2335_finalmilestone1.Food.FoodActivity;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCTranspo;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;


public class CBCMainActivity extends AppCompatActivity{
    final Context ctxt = this;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cbc_toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();

        switch (menuItemId) {
            case R.id.menuItemFood:
                Intent intent2 = new Intent(CBCMainActivity.this, FoodActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuItemMovie:
                Intent intent3 = new Intent(CBCMainActivity.this, Movie.class);
                startActivity(intent3);
                break;
            case R.id.menuItemOTC:
                Intent intent4 = new Intent(CBCMainActivity.this, OCTranspo.class);
                startActivity(intent4);
                break;
            case R.id.menuItemAbout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctxt).setTitle("CBC News Reader").setCancelable(false);

                String displayAboutString = getString(R.string.cbc_about);

                builder.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayAboutString)
                        .create();

                AlertDialog dialog = builder.show();

                //TextView messageView = dialog.findViewById(android.R.id.message);
                //TextView titleView = dialog.findViewById(android.R.id.title);
                break;

            case R.id.menuItemHelp:
                final AlertDialog.Builder builder_help = new AlertDialog.Builder(ctxt).setTitle("CBC News Reade").setCancelable(false);

                String displayHelpString = getString(R.string.cbc_help);

                builder_help.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayHelpString)
                        .create();

                AlertDialog dialog_help = builder_help.show();

                //TextView messageView = dialog.findViewById(android.R.id.message);
                //TextView titleView = dialog.findViewById(android.R.id.title);
                break;


        }

        return true;

    }
}