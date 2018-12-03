package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

public class CBCActivity extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cbc_general_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_statistics:
                startStatisticsActivity();
                break;
            case R.id.action_help:
                showHelpDialog();
                break;
            default:
                break;
        }
        return true;
    }

    void showHelpDialog(){
        new HelpDialog().show(getSupportFragmentManager(),"help:dialog");
    }

    /**
     * Call method should go to a {@link StatisticsActivity}.
     */
    void startStatisticsActivity(){
        Intent intent = new Intent(this,StatisticsActivity.class);
        startActivity(intent);
    }

}
