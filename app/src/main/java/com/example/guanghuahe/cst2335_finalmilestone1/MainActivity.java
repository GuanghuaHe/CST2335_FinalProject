package com.example.guanghuahe.cst2335_finalmilestone1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView mc1, pat2, mov3, oc4;
    Button aboutBtn;
    Intent intent;
    final Context ctxt = this;

    final String PERSON_1 = "Teng Fan";
    final String PERSON_2 = "Zhenyu Yin";
    final String PERSON_3 = "Aisha";
    final String PERSON_4 = "Guanghua He";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize buttons
        mc1 = findViewById(R.id.mcBtn1);
        pat2 = findViewById(R.id.patBtn2);
        mov3 = findViewById(R.id.movieBtn3);
        oc4 = findViewById(R.id.ocBtn4);
        aboutBtn = findViewById(R.id.aboutButton);

        // button on click methods
        mc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        mov3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Movie.class);
                startActivity(intent);
               // Log.i(ACTIVITY_NAME, "In onClick()");
            }
        });

        oc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DisplayBusStops.class);
                startActivity(intent);
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctxt).setTitle("CST2335_18F Final Project").setCancelable(false);

                String displayAboutString = "\nFood Nutrition by " + PERSON_1 + "\n\n"
                        + "Movie Serch App by " + PERSON_2 + "\n\n"
                        + "CBC News by " + PERSON_3 + "\n\n"
                        + "OC Transpo App by " + PERSON_4;

                builder.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayAboutString)
                        .create();

                AlertDialog dialog = builder.show();

                TextView messageView = dialog.findViewById(android.R.id.message);
                TextView titleView = dialog.findViewById(android.R.id.title);
                if(titleView != null){
                    titleView.setGravity(Gravity.CENTER);
                }
                messageView.setGravity(Gravity.CENTER);
            }
        });

    }

}
