package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    ListView routeDetailList;
    String stationNum,routeNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocroute_infor);

        routeDetailList = findViewById(R.id.route_detail_list);
        refresh = findViewById(R.id.refreshRouteButton);



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
        RouteDetailAdapter adapter = new RouteDetailAdapter(this, R.layout.route_detail_item, list);
        routeDetailList.setAdapter(adapter);




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


    class RouteDetailAdapter extends ArrayAdapter{
    TextView busDetail, routeDestination, direction, startTime, adjustTime,coordination, speed;
        int resourceID;


        public RouteDetailAdapter( Context context, int resource, List objects) {
            super(context, resource, objects);
            resourceID = resource;

        }


        @NonNull
        @Override
        public View getView(int position,  @Nullable View convertView,  @NonNull ViewGroup parent) {
            String[] info = (String[])getItem(position);
            View result = getLayoutInflater().inflate(resourceID, null);
            busDetail = result.findViewById(R.id.bus_details);
            busDetail.setText("Route:" + routeNum);
            routeDestination = result.findViewById(R.id.routenoDestinationView);
            routeDestination.setText("direction: " + info[0]);
            direction = result.findViewById(R.id.directionView);
            direction.setText("start time: " + info[1]);
            startTime = result.findViewById(R.id.startTimeView);
            startTime.setText("adjusted time: " + info[2]);
            adjustTime = result.findViewById(R.id.adjustedTimeView);
            adjustTime.setText("Lantitude: " + info[6]);

            coordination = result.findViewById(R.id.coordinatesView);
            coordination.setText("Longitude: " + info[7]);
            speed = result.findViewById(R.id.speedView);
            speed.setText("GPS speed: " + info[8]);
            return result;
        }
    }
}
