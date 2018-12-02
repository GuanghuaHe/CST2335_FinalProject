package com.example.guanghuahe.cst2335_finalmilestone1;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class CBC_frame extends Activity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_frame);

        button= findViewById(R.id.frame_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CBCFragment cf = new CBCFragment();
                getFragmentManager().beginTransaction().replace(R.id.cbc_frameLayout,cf).commit();

            }
        });
    }


}
