/**
 * The activity for display stop information, when use clicked a stop number will invoke this activity
 * @Author: Guanghua He
 * @Version: 1.1
 * @Since:1.0
 */

package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity.OCRoute;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DisplayStopInfor extends Activity {


    String stopName = "";
    ArrayList<OCRoute> allRoutes = new ArrayList<>();
    ArrayList<String> routesInfo = new ArrayList<String>();
    ListView routes;
    ProgressBar progressBar;
    TextView stationNameView;
    Button delete;

    public static String getRouteInfo = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
    public static String getRouteInfoTrailer = "&routeNo=";

    int progress;
    String stationNumber;

    private Context ctx = this;
    private static boolean deleteStops = false;
    private static String lastStop = "";

    protected static final String ACTIVITY_NAME = "DisplayStopInfor";

    public static void resetDeleteStation() {
        deleteStops = false;
    }
    public static boolean getDeleteStops() {
        return deleteStops;
    }
    public static String getDeletedStationNo() { return lastStop; }
    public static String getRouteSummaryForStop = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocstops_infor);

        progress = 0;
        routes = findViewById(R.id.routesView);
        progressBar =  findViewById(R.id.progressBar);
        stationNameView =  findViewById(R.id.stopName);
        delete = findViewById(R.id.deleteStopButton);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i(ACTIVITY_NAME, "输入有错 Error: no kind of stop number");
        } else {
            stationNumber = extras.getString("stationNumber");
            stationNameView.setText("Bus stop: " + stopName);
        }

        new OCQuery().execute("");


        RouteAdapter adapter = new RouteAdapter(this);
        routes.setAdapter(adapter);


        delete.setOnClickListener((e) -> {
            Log.i(ACTIVITY_NAME, "删除按钮 click Delete button");
            deleteStops = true;
            lastStop = stationNumber;
            finish();
        });


        routes.setOnItemClickListener((parent, view, position, id) -> {
            String s = routesInfo.get(position);
            Log.i(ACTIVITY_NAME, "路线选定 Message: " + s);
            Intent intent = new Intent(DisplayStopInfor.this, DisplayRouteInfor.class);
            Bundle bundle = new Bundle();

            OCRoute.updateData(getRouteInfo+allRoutes.get(position).getStopNum()+getRouteInfoTrailer+allRoutes.get(position).getRouteno());
            bundle.putString("routeno", allRoutes.get(position).getRouteno());
            bundle.putString("stationno", allRoutes.get(position).getStopNum());
            intent.putExtra("bundle", bundle);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           startActivity(intent);


        });


    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }


    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    private void updateProgressBar(int u, int max) {
        progress += u;
        if (progress > max)
            progress = max;
        progressBar.setProgress(progress);
    }

    private void stationNotFoundDialog() {
        //   *************************************************   //
        /*      FOR FOLLOWING CODE BLOCK:
                Author: mkyong
                url: https://www.mkyong.com/android/android-custom-dialog-example/
        */
        Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.oc_dialog);
        dialog.setTitle("Bus Stop not found");


        TextView text =  dialog.findViewById(R.id.textDialog);
        text.setText(getString(R.string.oc_stop_w_no) + " " + stationNumber + " "+ getString(R.string.oc_stop_not_found));

        ImageView image =  dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.missing);

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener((x) -> dialog.dismiss());
        if(!isFinishing())dialog.show();
        //   *************************************************   //
    }





    public class RouteAdapter extends ArrayAdapter<String> {
        public RouteAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return (routesInfo.size());
        }

        @Override
        public String getItem(int position) {
            return routesInfo.get(position);
        }
        @NonNull
        @Override
        public View  getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = DisplayStopInfor.this.getLayoutInflater();

            View result = inflater.inflate(R.layout.oc_route, null);

            TextView routeText = result.findViewById(R.id.route_text);

            routeText.setText (getItem(position) );
            return result;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

    }



    public class OCQuery extends AsyncTask<String, Integer, String> {


        public ArrayList<OCRoute> routesList = new ArrayList<OCRoute>();

        private String currentRouteno;
        private String currentRouteDirection;
        private String currentRouteDestination;


        @Override
        protected String doInBackground(String... array) {
            Log.i(ACTIVITY_NAME, "开始查询 background activity started.............");



            try {
                     URL url = new URL(getRouteSummaryForStop + stationNumber);
              // URL url = new URL(getRouteSummaryForStop.concat(connStationNumber));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                updateProgressBar(10, 10);

                Log.i(ACTIVITY_NAME, "attempting parse..");
                parse(conn.getInputStream());
                Log.i(ACTIVITY_NAME, "parse complete");
            } catch (Exception e) {
                Log.i(ACTIVITY_NAME, "Error: " + e.toString());
                return ("Error: " + e.toString());
            }
            return null;
        }

        protected void parse(InputStream in) throws XmlPullParserException, IOException {
            String lastTag = "";
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");


                int eventType = xpp.getEventType();
                updateProgressBar(10,20);

                Log.i(ACTIVITY_NAME, "开始查询 Attempting parse: ");
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            Log.i(ACTIVITY_NAME, "找不到关键字 Tag found.");
                            lastTag = xpp.getName();
                            updateProgressBar(3,80);
                            Log.i(ACTIVITY_NAME, "找不到关键字 Tag is " + lastTag);
                            break;
                        case XmlPullParser.TEXT:
                            if (lastTag.equals("StopDescription")) {
                                Log.i(ACTIVITY_NAME, "车站找到了 Stop name found: ");
                                stopName = xpp.getText();
                                updateProgressBar(12,80);
                            } else if (lastTag.equals("RouteNo")) {
                                currentRouteno = xpp.getText();
                                updateProgressBar(12,80);
                            } else if (lastTag.equals("Direction")) {
                                currentRouteDirection = xpp.getText();
                                updateProgressBar(10,80);
                            } else if (lastTag.equals("RouteHeading")) {
                                currentRouteDestination = xpp.getText();
                                updateProgressBar(10,80);
                                routesList.add(new OCRoute(currentRouteno, currentRouteDestination, currentRouteDirection, stationNumber));
                            }
                            break;
                        default:
                            break;
                    }
                    xpp.next();
                    eventType = xpp.getEventType();
                }
            } finally {
                in.close();
                Log.i(ACTIVITY_NAME, "关闭接口 input stream is closed");
                updateProgressBar(100,90);
            }
        }


        @Override
        protected void onPostExecute(String result) {

            RouteAdapter adapter = new RouteAdapter(ctx);
            ListView routesview = findViewById(R.id.routesView);
            routesview.setAdapter(adapter);

            stationNameView.setText("Bus stop: " + stopName);

            for (OCRoute r : routesList) {
                String newRoute = "";
                newRoute = newRoute.concat(r.getRouteno());
                newRoute = newRoute.concat(" ");
                newRoute = newRoute.concat(r.getDestination());
                routesInfo.add(newRoute);
                adapter.notifyDataSetChanged();
                allRoutes.add(r);
                updateProgressBar(2,100);
            }
            updateProgressBar(100,100);

            if (stopName.equals(""))
                stationNotFoundDialog();
        }
    }
}
