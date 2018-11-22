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

    public static String getRouteInfo = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
    public static String getRouteInfoTrailer = "&routeNo=";
    public static final String ACTIVITY_NAME = "DisplayRouteInfor";
    List<String[]> list;

    Button refresh;
    OCRoute route;
    TextView routenoDestination_0, routenoDestination_1, routenoDestination_2;
    TextView direction_0, direction_1, direction_2;
    TextView startTime_0, startTime_1, startTime_2;
    TextView adjustedTime_0, adjustedTime_1, adjustedTime_2;
    TextView coordinates_0, coordinates_1, coordinates_2;
    TextView speed_0, speed_1, speed_2;
    String stationNum,routeNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocroute_infor);


        speed_0 = findViewById(R.id.speedView_0);
        speed_1 = findViewById(R.id.speedView_1);
        speed_2 = findViewById(R.id.speedView_2);
        refresh = findViewById(R.id.refreshRouteButton);
        routenoDestination_0 = findViewById(R.id.routenoDestinationView_0);
        routenoDestination_1 = findViewById(R.id.routenoDestinationView_1);
        routenoDestination_2 = findViewById(R.id.routenoDestinationView_2);
        direction_0 = findViewById(R.id.directionView_0);
        direction_1 = findViewById(R.id.directionView_1);
        direction_2 = findViewById(R.id.directionView_2);
        startTime_0 = findViewById(R.id.startTimeView_0);
        startTime_1 = findViewById(R.id.startTimeView_1);
        startTime_2 = findViewById(R.id.startTimeView_2);
        adjustedTime_0 = findViewById(R.id.adjustedTimeView_0);
        adjustedTime_1 = findViewById(R.id.adjustedTimeView_1);
        adjustedTime_2 = findViewById(R.id.adjustedTimeView_2);
        coordinates_0 = findViewById(R.id.coordinatesView_0);
        coordinates_1 = findViewById(R.id.coordinatesView_1);
        coordinates_2 = findViewById(R.id.coordinatesView_2);



      Bundle b =  getIntent().getParcelableExtra("bundle");
            stationNum = b.getString("stationno");
           routeNum = b.getString("routeno");






       /* new Update().execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        setDisplay();





        //connects to a action button to get refeshed data

        refresh.setOnClickListener((e) -> {
            Toast toast = Toast.makeText(this, getString(R.string.oc_refresh), Toast.LENGTH_SHORT);
            toast.show();
            OCRoute.updateData(getRouteInfo+stationNum+getRouteInfoTrailer+routeNum);

            setDisplay();
        });
    }

    private void setDisplay() {

        list = OCRoute.routeList;
        Log.e("OCRoute LIST DETAIL:",""+ list.get(0)[0]+ "\t" + list.get(0)[1]+"\t"+list.get(0)[2]+"\t"+list.get(0)[3]+"\t" + list.get(0)[4]);

        routenoDestination_0.setText(getString(R.string.oc_route) + routeNum + " " + list.get(0)[0]);
        Log.i(ACTIVITY_NAME, " see the details!" + list.get(0)[0]);
        routenoDestination_1.setText(getString(R.string.oc_route) + routeNum + " " + list.get(1)[0]);
        routenoDestination_2.setText(getString(R.string.oc_route) + routeNum + " " + list.get(2)[0]);


        direction_0.setText(getString(R.string.oc_direction) + list.get(0)[0]);
        direction_1.setText(getString(R.string.oc_direction) + list.get(1)[0]);
        direction_2.setText(getString(R.string.oc_direction) + list.get(2)[0]);


        startTime_0.setText(getString(R.string.oc_starttime) + list.get(0)[1]);
        startTime_1.setText(getString(R.string.oc_starttime) + list.get(1)[1]);
        startTime_2.setText(getString(R.string.oc_starttime) + list.get(2)[1]);

        adjustedTime_0.setText(getString(R.string.oc_adjustedtime) + list.get(0)[2]);
        adjustedTime_1.setText(getString(R.string.oc_adjustedtime) + list.get(1)[2]);
        adjustedTime_2.setText(getString(R.string.oc_adjustedtime) + list.get(2)[2]);


        coordinates_0.setText(getString(R.string.oc_latlong) + list.get(0)[3]);
        coordinates_1.setText(getString(R.string.oc_latlong) + list.get(1)[3]);
        coordinates_2.setText(getString(R.string.oc_latlong) + list.get(2)[3]);


        speed_0.setText(getString(R.string.oc_gpsspeed) + list.get(0)[4]);
        speed_1.setText(getString(R.string.oc_gpsspeed) + list.get(1)[4]);
        speed_2.setText(getString(R.string.oc_gpsspeed) + list.get(2)[4]);




    }

    /**
     * uses Async to get updated bus details from server
     */
    public class Update extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            OCRoute.updateData(getRouteInfo+stationNum+getRouteInfoTrailer+routeNum);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.i(ACTIVITY_NAME, e.toString());
                }
                OCRoute.updateData(getRouteInfo+stationNum+getRouteInfoTrailer+routeNum);


            return null;
        }


    }
}
