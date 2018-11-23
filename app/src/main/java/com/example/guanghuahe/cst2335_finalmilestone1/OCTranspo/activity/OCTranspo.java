package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;
import com.example.guanghuahe.cst2335_finalmilestone1.Nutrition;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.database.OCDatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.fragment.DisplayInfoFragment;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters.DisplayStopInfor;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.fragment.DisplayStopsFragment;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

/**
 * Start Activity for Guanghua's OCtranspo app
 */

public class OCTranspo extends AppCompatActivity {

    final Context ctxt = this;
    private ProgressBar ocProgressBar;
    protected static final String ACTIVITY_NAME = "OCTranspoActivity";
    private Context ctx;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int currentStationIndex = 0;
    boolean menuOn = false;
    StationAdapter adapter;
    ListView stations;
    EditText stationInput;
    Button addStation;
    ArrayList<String> stationsList = new ArrayList<>();
    ArrayList<String> stationsNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOCtranspo);
        //setSupportActionBar(toolbar);

        // This section connects to the database and loads a query into the cursor
        OCDatabaseHelper dbHelper = new OCDatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
        setContentView(R.layout.activity_octranspo);

        stations = (ListView) findViewById(R.id.stationsView);
        stationInput = (EditText) findViewById(R.id.stopNoInput);
        addStation = (Button) findViewById(R.id.addStopNoButton);
        adapter = new StationAdapter(this);
        stations.setAdapter(adapter);
        ocProgressBar = findViewById(R.id.ocProgressBar);

        Toast toast = Toast.makeText(this, "Guanghua's OCTranspo Toast", Toast.LENGTH_LONG);
        toast.show();
        ocProgressBar.setVisibility(View.VISIBLE);

        Log.i(ACTIVITY_NAME, "Attempted query:    SELECT " +
                OCDatabaseHelper.STATION_NAME + ", " +
                OCDatabaseHelper.STATION_NO + " FROM " +
                OCDatabaseHelper.TABLE_NAME);

        cursor = db.rawQuery("SELECT " +
                OCDatabaseHelper.STATION_NAME + ", " +
                OCDatabaseHelper.STATION_NO + " FROM " +
                OCDatabaseHelper.TABLE_NAME, null, null);
        cursor.moveToFirst();
        //The data has been loaded into the cursor
        //Look through the cursor and add data from the database to the array - to show the data in the listview
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "Current cursor position: " + cursor.getPosition());
            String newStation = "Bus Stop number: ";
            newStation = newStation.concat(cursor.getString(1));

            stationsList.add(newStation);
            stationsNumbers.add(cursor.getString(1));
            cursor.moveToNext();
        }

        //When you click the floating action button it adds a bus number - CST2335 – Graphical Interface Programming Lab 3

        addStation.setOnClickListener((e) -> {
            String s = stationInput.getText().toString();
            boolean valid = false;
            try {
                int a = Integer.parseInt(s);
                valid = true;
            } catch (Exception ex) {
                valid = false;
            }
            if (s.length() == 0)
                valid = false;

            if (valid) {
                ContentValues newData = new ContentValues();

                newData.put(OCDatabaseHelper.STATION_NAME, "NAME_NOT_FOUND");
                newData.put(OCDatabaseHelper.STATION_NO, s);

                db.insert(OCDatabaseHelper.TABLE_NAME, OCDatabaseHelper.STATION_NAME, newData);

                String newStation = "Bus Stop number: ";
                newStation = newStation.concat(s);
                stationsList.add(newStation);
                stationsNumbers.add(s);
                stationInput.setText("");
                adapter.notifyDataSetChanged();
            } else {
                Snackbar wronginput = Snackbar.make(findViewById(android.R.id.content), getString(R.string.oc_badinput), Snackbar.LENGTH_SHORT);
                wronginput.show();
                stationInput.setText("");
            }
        });

        //Saves the text to the database, then loads the OCTranspoStop activit with the Stop number added
        stations.setOnItemClickListener((parent, view, position, id) -> {
            String s = stationsList.get(position);
            Log.i(ACTIVITY_NAME, "Message: " + s);
            String stationNumber = stationsNumbers.get(position);
            //Passes the input text to new activity when starting the activity
            Intent i = new Intent(OCTranspo.this, DisplayStopInfor.class);
            i.putExtra("stationNumber", stationNumber);
            currentStationIndex = position;
            startActivity(i);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.oc_toolbar_menu, menu);
        return true;
    }


    /**
     * This class is used to connect all the other app links
     * This was Largely based on CST2335 – Graphical Interface Programming Lab 8
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();

        switch (menuItemId) {
            case R.id.menuItemFood:
                Intent intent2 = new Intent(OCTranspo.this, Nutrition.class);
                startActivity(intent2);
                break;
            case R.id.menuItemMovie:
                Intent intent3 = new Intent(OCTranspo.this, Movie.class);
                startActivity(intent3);
                break;
            case R.id.menuItemCBC:
                Intent intent4 = new Intent(OCTranspo.this, Movie.class);
                startActivity(intent4);
                break;
            case R.id.menuItemAbout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctxt).setTitle("OC Transpo App").setCancelable(false);

                String displayAboutString = getString(R.string.about);

                builder.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayAboutString)
                        .create();

                AlertDialog dialog = builder.show();

                TextView messageView = dialog.findViewById(android.R.id.message);
                //TextView titleView = dialog.findViewById(android.R.id.title);
                break;
        }

        return true;

    }


    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");

        if (DisplayStopInfor.getDeleteStation() == true) {
            Log.i(ACTIVITY_NAME, "Deleting station no " + currentStationIndex);
            String[] params = new String[1];
            params[0] = stationsNumbers.get(currentStationIndex);
            db.delete(OCDatabaseHelper.TABLE_NAME, OCDatabaseHelper.STATION_NO + "=?", params);

            adapter = new StationAdapter(this);
            stations.setAdapter(adapter);

            stationsList.remove(currentStationIndex);
            stationsNumbers.remove(currentStationIndex);
            adapter.notifyDataSetChanged();


            Snackbar welcome = Snackbar.make(findViewById(android.R.id.content),
                    "Station " + DisplayStopInfor.getDeletedStationNo() + " has been deleted", Snackbar.LENGTH_SHORT);
            welcome.show();

            DisplayStopInfor.resetDeleteStation();
        }

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
        db.close();
        super.onDestroy();
    }


    // FOLLOWING METHOD ChangeFragment written by 'ProgrammingKnowledge', Mar 5\ 2015.
    // URL: https://www.youtube.com/watch?v=FF-e6CnBwYY
    public void ChangeFragment(View view) {
        Log.i(ACTIVITY_NAME, "Changing fragment..");

        Fragment fragment;
        if (view == findViewById(R.id.helpButton)) {
            Log.i(ACTIVITY_NAME, "Togglebutton was clickerood..");
            ListView stationsView = (ListView) (findViewById(R.id.stationsView));
            if (menuOn) {
                fragment = new DisplayStopsFragment();
                stationsView.setVisibility(View.VISIBLE);
            } else {
                fragment = new DisplayInfoFragment();
                stationsView.setVisibility(View.INVISIBLE);
            }

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentSpace, fragment);
            ft.commit();


            menuOn = !menuOn;
        }
    }

    /**
     * This adapter updates/generates the list view elements
     * This was Largely based on CST2335 – Graphical Interface Programming Lab 4
     */
    public class StationAdapter extends ArrayAdapter<String> {
        public StationAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return (stationsList.size());
        }

        @Override
        public String getItem(int position) {
            return stationsList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = OCTranspo.this.getLayoutInflater();

            View result = inflater.inflate(R.layout.oc_stop, null);

            TextView stationText = (TextView) result.findViewById(R.id.station_text);
            stationText.setText(getItem(position));

            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}



