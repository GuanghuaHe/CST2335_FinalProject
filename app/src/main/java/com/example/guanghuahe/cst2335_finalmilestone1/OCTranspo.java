package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static android.widget.Toast.LENGTH_LONG;

public class OCTranspo extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "OCTranspo";
    private Button byStop;
    private Button deleteALL;
    private EditText userEnter;
    private Button goHomeO;
    private ProgressBar ocProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        Log.i(ACTIVITY_NAME, "In onCreate()");

         byStop = (Button) findViewById(R.id.byStop);
         deleteALL = (Button) findViewById(R.id.deleteALL);
         userEnter = (EditText) findViewById(R.id.userEnter);
         goHomeO = (Button) findViewById(R.id.goHomeO);
        ocProgressBar = findViewById(R.id.ocProgressBar);


        Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT).show();


        byStop.setOnClickListener(e->{
                Snackbar.make(e, "Searching", Snackbar.LENGTH_LONG).show();
                Intent toDetail = new Intent(this, OCDetails.class);
                startActivity(toDetail);
        });

        ocProgressBar.setVisibility(View.VISIBLE);

        deleteALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "deleting", Snackbar.LENGTH_LONG).show();
            }
        });


        goHomeO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(OCTranspo.this)
                        .setTitle("Notice!")
                        .setMessage("Exit")

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(OCTranspo.this, MainActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
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


}