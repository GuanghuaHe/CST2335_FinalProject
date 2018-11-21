package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCRoute;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

import java.util.List;

/**
 * This class displays the stop data
 */

public class DisplayRouteInfor extends Activity {
    public static final String ACTIVITY_NAME = "DisplayRouteInfor";


    Button refresh;
    OCRoute route;
    TextView routenoDestination;
    TextView direction;
    TextView startTime;
    TextView adjustedTime;
    TextView coordinates;
    TextView speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocroute_infor);


        speed = findViewById(R.id.speedView);
        refresh = findViewById(R.id.refreshRouteButton);
        routenoDestination = findViewById(R.id.routenoDestinationView);
        direction = findViewById(R.id.directionView);
        startTime = findViewById(R.id.startTimeView);
        adjustedTime = findViewById(R.id.adjustedTimeView);
        coordinates =  findViewById(R.id.coordinatesView);
        Bundle extras = getIntent().getExtras();


        route = new OCRoute(extras.getString("routeno"), extras.getString("destination"),
                extras.getString("direction"), extras.getString("stationNum")
        );

        new Update().executeOnExecutor( ((r) -> {r.run();}),"");

        //connects to a action button to get refeshed data

        refresh.setOnClickListener((e) -> {
            Toast toast = Toast.makeText(this, getString(R.string.oc_refresh), Toast.LENGTH_SHORT);
            toast.show();
            route.updateData();
            setDisplay();
        });
    }

    private void setDisplay() {
        List<String[]>  list = route.routeList;

        for(int i = 0; i< list.size(); i++) {
            routenoDestination.setText(getString(R.string.oc_route) + route.getRouteno()+ " " + list.get(i)[i]);
            direction.setText(getString(R.string.oc_direction) + route.getDirection());
            startTime.setText(getString(R.string.oc_starttime) + route.getStartTime());
            adjustedTime.setText(getString(R.string.oc_adjustedtime) + route.getAdjustedTime());
            coordinates.setText(getString(R.string.oc_latlong) + route.getCoordinates());
            speed.setText(getString(R.string.oc_gpsspeed) + route.getSpeed());
        }
    }

    /**
     * uses Async to get updated bus details from server
     *
     */
    public class Update extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            route.updateData();
            if (route.getStartTime() == null || route.getSpeed() == null || route.getCoordinates() == null || route.getAdjustedTime() == null) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.i(ACTIVITY_NAME, e.toString());
                }
                route.updateData();
            }
            setDisplay();
            return null;
        }
    }
}
