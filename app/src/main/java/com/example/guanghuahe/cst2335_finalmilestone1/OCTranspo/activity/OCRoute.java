/**
 * The activity for oc route, when clicked a route number from
 * OCTranspo will invoke this activity
 * @Author: Guanghua He
 * @Version: 1.1
 * @Since:1.0
 */

package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanghua He on 2018-11-20.
 * Detail shows bus stats
 */

public class OCRoute {

    private boolean ready = false;
    private String stopsNum;
    private String routeno;
    private String destination;
    private String coordinates;
    private String speed;
    private String startTime;
    private String adjustedTime;
    private String direction;
    public static final List<String[]> routeList = new ArrayList<>();


    private OCRoute() {
    }

    public OCRoute(String routeno, String destination, String direction, String stationNum) {

        this.direction = direction;
        this.stopsNum = stationNum;
        this.routeno = routeno;
        this.destination = destination;

    }




    public String getRouteno() {
        return routeno;
    }

    public String getDestination() {
        return destination;
    }

    public String getStopNum() {
        return stopsNum;
    }

    public String getCoordinates() {
        return coordinates;
    }


    public static void updateData(String ss) {
        new OCRoute().new OCRouteQuery().execute(ss);
        Log.e("网址是什么 URL ==", ss);
    }


    /**
     * uses Async to get bus details from server
     */

    class OCRouteQuery extends AsyncTask<String, Integer, String> {

        /**
         * Load the data from the OCTranspo URL
         * based on CST2335 – Graphical Interface Programming Lab 6
         */
        List<String[]> tempList = new ArrayList<>();
        @Override
        protected String doInBackground(String... array) {
            Log.i("OCRoute constructor", "开始查询 background activity start..");

            try {

                URL url = new URL(array[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                Log.i("OCRoute constructor", "开始搜寻关键字attempting parse..");
                parse(conn.getInputStream());
                Log.i("OCRoute constructor", "搜寻完成parse complete");
            } catch (Exception e) {
                Log.i("OCRoute constructor", "发生错误Error: " + e.toString());
                return ("Error: " + e.toString());
            }
            return null;
        }

        protected void parse(InputStream in) throws XmlPullParserException, IOException {

            try {

                //Use xml parser to load the data
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");


                int eventType;


                //basically we cycle through the parser, we add data to our data object one piece at a time for each trip, at the end of each
                //trip we add that trip to our result array, then reset the data object for a new trip. Until we reach the end of our XML
                String tagName;
                String[] temp = null;

                while((eventType = xpp.next()) != XmlPullParser.END_DOCUMENT){
                    Log.e("jinlai","jinlaiba" );
                    if(eventType == XmlPullParser.START_TAG ){
                        Log.e("jin de lai ","222222222222" );


                             tagName = xpp.getName();




                                if("TripDestination".equalsIgnoreCase(tagName)) {
                                    temp = new String[9];
                                    temp[0] = xpp.nextText();
                                    Log.e("destination", "" + temp[0]);
                                }
                                else if("TripStartTime".equalsIgnoreCase(tagName)) {
                                    temp[1] = xpp.nextText();
                                    Log.e("start  time", "" + temp[1]);
                                }
                                else if("AdjustedScheduleTime".equalsIgnoreCase(tagName)){
                                    temp[2] = xpp.nextText();
                                    Log.e("adjust time", "" + temp[2]);
                                }
                                else if("AdjustmentAge".equalsIgnoreCase(tagName)){
                                    temp[3] = xpp.nextText();
                                    Log.e("AdjustmentAge ", "" + temp[3]);
                                }else if("LastTripOfSchedule".equalsIgnoreCase(tagName)){
                                    temp[4] = xpp.nextText();
                                    Log.e("LastTripOfSchedule ", "" + temp[4]);
                                }else if("BusType".equalsIgnoreCase(tagName)){
                                    temp[5] = xpp.nextText();
                                    Log.e("BusType ", "" + temp[5]);

                                }
                                else if("Latitude".equalsIgnoreCase(tagName)) {

                                    temp[6] = xpp.nextText();
                                    Log.e("latitude   ", "" + temp[6]);
                                }
                                else if("Longitude".equalsIgnoreCase(tagName)){
                                    temp[7] = xpp.nextText();
                                    Log.e("longitude  ", "" + temp[7]);
                                }
                                else if("GPSSpeed".equalsIgnoreCase(tagName)) {
                                    temp[8] = xpp.nextText();
                                    Log.e("GPS Speed  ", "" + temp[8]);
                                    routeList.add(temp);
                                }else{}





                    }


                }
                /*while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() != XmlPullParser.START_TAG) {
                        if (xpp.getEventType() == XmlPullParser.END_TAG) {
                            // if we are in an end tag, and that tag is called trip, then add the trip to our result array
                            if (xpp.getName().equalsIgnoreCase("trip")) {
                                Log.e("dada", " " + temp);
                            }
                        }
                        continue;
                    } else if (xpp.getName().equalsIgnoreCase("trip")) {
                        temp = new String[5];
                        String name = xpp.getName();
                        // Starts by looking for the entry tag
                        if (name.equalsIgnoreCase("TripDestination")) {
                            xpp.next();
                            temp[0] = xpp.getText();
                            Log.i("TripDestination", "Route " + temp[0]);
                        }
                        if (name.equalsIgnoreCase("TripStartTime")) {
                            xpp.next();
                            temp[1] = xpp.getText();
                            Log.i("TripStartTime", "TripStartTime " + temp[1]);
                        }
                        if (name.equalsIgnoreCase("AdjustedScheduleTime")) {
                            xpp.next();
                            temp[2] = xpp.getText();
                            Log.i("AdjustedScheduleTime", "AdjustedScheduleTime " + temp[2]);
                        }
                        if (name.equalsIgnoreCase("AdjustmentAge")) {
                            xpp.next();
                            String tempText = xpp.getText();
                            Log.i("AdjustmentAge", "AdjustmentAge " + tempText);
                        }
                        if (name.equalsIgnoreCase("LastTripOfSchedule")) {
                            xpp.next();
                            String tempText = xpp.getText();
                            Log.i("LastTripOfSchedule", "LastTripOfSchedule " + tempText);
                        }
                        if (name.equalsIgnoreCase("BusType")) {
                            xpp.next();
                            String tempText = xpp.getText();
                            Log.i("BusType", "BusType " + tempText);
                        }
                        if (name.equalsIgnoreCase("GPSSpeed")) {
                            xpp.next();
                            temp[4] = xpp.getText();
                            Log.i("GPSSpeed", "GPSSpeed " + temp[4]);
                            temp = new String[5];
                        }
                        if (name.equalsIgnoreCase("Latitude")) {
                            xpp.next();
                            gps += xpp.getText();
                            Log.i("Latitude", "Latitude " + gps);
                        }
                        if (name.equalsIgnoreCase("Longitude")) {
                            xpp.next();
                            gps += " /" + xpp.getText();
                            Log.i("Longitude", "Longitude " + gps);
                        }
                        temp[3] = gps;
                        routeList.add(temp);
                    }
                }*/

            } finally {
                in.close();
                Log.i("OCRoute constructor", "关闭连接 input stream is closed");
                Log.e("OCRoute LIST DETAIL:", "" + routeList.get(0)[0] + "\t" + routeList.get(0)[1] + "\t" + routeList.get(0)[2] + "\t" + routeList.get(0)[3] + "\t" + routeList.get(0)[4]);
                // Log.e("OCRoute LIST DETAIL:",""+ routeList.get(1)[0]+ "\t" + routeList.get(1)[1]+"\t"+routeList.get(1)[2]+"\t"+routeList.get(1)[3]+"\t" + routeList.get(1)[4]);
                // Log.e("OCRoute LIST DETAIL:",""+ routeList.get(2));
            }

        }

        /**
         * after the async task completes
         * adds the data downloaded to the activity so it can be put in to the inflator and shown
         */
        @Override
        protected void onPostExecute(String result) {

            stopsNum = ((stopsNum != null) ? stopsNum : "Info NA");
            routeno = ((routeno != null) ? routeno : "Info NA");
            destination = ((destination != null) ? destination : "Info NA");
            coordinates = ((coordinates != null) ? coordinates : "Info NA");
            speed = ((speed != null) ? speed : "Info NA");
            startTime = ((startTime != null) ? startTime : "Info NA");
            adjustedTime = ((adjustedTime != null) ? adjustedTime : "Info NA");
            direction = ((direction != null) ? direction : "Info NA");
            ready = true;

            routeList.clear();
            routeList.addAll(tempList);

        }
    }
}