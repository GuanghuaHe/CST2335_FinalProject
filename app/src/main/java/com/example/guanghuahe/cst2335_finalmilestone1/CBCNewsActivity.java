package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCTranspo;


public class CBCNewsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "CBC";
    private Button searchButton, saveButton,quitButton, frameButton, asyButton;
    private ImageView imageView;
    private EditText editText;
    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        AlertDialog.Builder builder;

        switch (id){
            case R.id.cbc_item: intent = new Intent(this, CBCNewsActivity.class);
                startActivity(intent);
                break;

            case R.id.cbc_n_item: intent = new Intent(this, Nutrition.class);
                startActivity(intent);
                break;
            case R.id.cbc_movie_item: intent = new Intent(this, com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie.class);
                startActivity(intent);
                break;
            case R.id.cbc_oc_item: intent = new Intent(this, OCTranspo.class);
                startActivity(intent);
                break;
            case R.id.cbc_item_help:
                builder = new AlertDialog.Builder(CBCNewsActivity.this);
                builder.setTitle(R.string.cbc_helper_message)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onStop();
                            }
                        }).show();
                break;
            case R.id.cbc_item_quit:
                builder = new AlertDialog.Builder(CBCNewsActivity.this);
                builder.setMessage(R.string.cbc_button_quit);
                builder.setTitle("Quit?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("Response", "Quit the program");
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }

                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onStop();
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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
        asyButton = findViewById(R.id.cbc_asy);
        frameButton = findViewById(R.id.cbc_frame);
        toolbar = findViewById(R.id.cbc_ToolBar);
        setSupportActionBar(toolbar);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You clicked the save Button.", Toast.LENGTH_LONG ).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Are you sure your want to search?", Snackbar.LENGTH_LONG).setAction("Confirm", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CBCNewsActivity.this, CBC_frame.class);
                        startActivity(intent);
                        Log.i(ACTIVITY_NAME, "In onClick()");
                    }
                }).show();
            }
        });


        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        frameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CBCNewsActivity.this, CBC_frame.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });

        asyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CBCNewsActivity.this, CBCDetail.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "In onClick()");
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
