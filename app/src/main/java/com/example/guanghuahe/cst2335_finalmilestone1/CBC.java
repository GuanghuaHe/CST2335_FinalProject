package com.example.guanghuahe.cst2335_finalmilestone1;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CBC extends Activity {

    protected static final String ACTIVITY_NAME = "CBC";
    private Button searchButton, saveButton,quitButton;
    private ImageView imageView;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        searchButton = (Button) findViewById(R.id.cbc_search);
        saveButton = (Button) findViewById(R.id.cbc_save);
        imageView = (ImageView) findViewById(R.id.cbc_image_welcome);
        editText = (EditText) findViewById(R.id.cbc_editText);
        quitButton = (Button) findViewById(R.id.cbc_quit);


        // save button on click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You clicked the save Button.", Toast.LENGTH_LONG ).show();
            }
        });

        //search button on click
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Are you sure your want to search?", Snackbar.LENGTH_LONG).setAction("Confirm", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });

        //quit button on click
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
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

    private void showDialog() {
        Dialog dialog=new AlertDialog.Builder(this)
                .setTitle("Exit the application?")//set the title
                .setMessage("Are you sure you want to exit this page?")//set the context
                .setIcon(R.drawable.cbcquit)
                //confirm button
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                //cancel button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();//create the dialog
        dialog.show();//show the dialog
    }
}