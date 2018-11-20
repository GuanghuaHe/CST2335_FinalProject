package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Guanghua He on 2018-11-20.
 * Detail shows bus stats
 */

public class OCRoute {

    private boolean ready = false;
    private String stationNum;
    private String routeno;
    private String destination;
    private String coordinates;
    private String speed;
    private String startTime;
    private String adjustedTime;
    private String direction;



    public static String getRouteInfo = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
    public static String getRouteInfoTrailer = "&routeNo=";


    public OCRoute(String routeno, String destination, String direction, String stationNum) {

        this.direction = direction;
        this.stationNum = stationNum;
        this.routeno = routeno;
        this.destination = destination;

    }

    public String getRouteno() { return routeno; }
    public String getDestination() {
        return destination;
    }
    public String getStationNum() { return stationNum; }
    public String getCoordinates() { return coordinates; }
    public String getSpeed() { return speed; }
    public String getStartTime() { return startTime; }
    public String getAdjustedTime() { return adjustedTime; }
    public String getDirection() { return direction; }

    public void updateData() {
        new OCRouteQuery().execute("");
    }

    /**
     * uses Async to get bus details from server
     *
     */

    public class OCRouteQuery extends AsyncTask<String, Integer, String> {

        /**
         Load the data from the OCTranspo URL
         based on CST2335 – Graphical Interface Programming Lab 6
         */

        @Override
        protected String doInBackground(String... array) {
            Log.i("OCRoute constructor", "background activity start..");

            try {
                String urlstring = getRouteInfo.concat(stationNum);
                urlstring = urlstring.concat(getRouteInfoTrailer);
                urlstring = urlstring.concat(routeno);
                URL url = new URL(urlstring);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                Log.i("OCRoute constructor", "attempting parse..");
                parse(conn.getInputStream());
                Log.i("OCRoute constructor", "parse complete");
            } catch (Exception e) {
                Log.i("OCRoute constructor", "Error: " + e.toString());
                return ("Error: " + e.toString());
            }
            return null;
        }

        protected void parse(InputStream in) throws XmlPullParserException, IOException {
            String lastTag = "";
            boolean cont = true;
            boolean foundDirection = false;

            String fullCoordinates = "";
            try {

                //Use xml parser to load the data
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");


                int eventType = xpp.getEventType();

                //basically we cycle through the parser, we add data to our data object one piece at a time for each trip, at the end of each
                //trip we add that trip to our result array, then reset the data object for a new trip. Until we reach the end of our XML

                while ((eventType != XmlPullParser.END_DOCUMENT) && cont) {

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            lastTag = xpp.getName();
                            break;
                        case XmlPullParser.TEXT:
                            // Starts by looking for the entry tag
                            if (lastTag.equals("Direction") && xpp.getText().equals(direction)) {
                                foundDirection = true;
                            } else if (foundDirection) {
                                Log.i("TagValue", xpp.getText());
                                if (lastTag.equals("TripDestination"))
                                    destination = xpp.getText();
                                else if (lastTag.equals("TripStartTime"))
                                    startTime = xpp.getText();
                                else if (lastTag.equals("AdjustedScheduleTime"))
                                    adjustedTime = xpp.getText();
                                else if (lastTag.equals("Latitude"))
                                    fullCoordinates = (xpp.getText().concat("/"));
                                else if (lastTag.equals("Longitude"))
                                    coordinates = fullCoordinates.concat(xpp.getText());
                                else if (lastTag.equals("GPSSpeed")) {
                                    speed = xpp.getText();
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (xpp.getName().equals("Trip") && foundDirection) {
                                cont = false;
                                Log.i("Route", "breaking from parse");
                            }
                            break;
                        default:
                            break;
                    }
                    xpp.next();
                    eventType = xpp.getEventType();
                }
                Log.i("FinalValues", destination +" "+
                        startTime +" "+
                        adjustedTime +" "+
                        coordinates +" "+
                        speed);
            } finally {
                in.close();
                Log.i("OCRoute constructor","closed input stream");
            }
        }
        /**
         *   after the async task completes
         *   adds the data downloaded to the activity so it can be put in to the inflator and shown
         */
        @Override
        protected void onPostExecute(String result) {
            stationNum = ((stationNum != null) ? stationNum : "Info NA");
            routeno = ((routeno != null) ? routeno : "Info NA");
            destination = ((destination != null) ? destination : "Info NA");
            coordinates = ((coordinates != null) ? coordinates : "Info NA");
            speed = ((speed != null) ? speed : "Info NA");
            startTime = ((startTime != null) ? startTime : "Info NA");
            adjustedTime = ((adjustedTime != null) ? adjustedTime : "Info NA");
            direction = ((direction != null) ? direction : "Info NA");
            ready = true;
        }
    }
}
