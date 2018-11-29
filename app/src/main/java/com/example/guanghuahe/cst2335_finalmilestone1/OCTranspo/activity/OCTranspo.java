package com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.Food.FoodActivity;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters.DisplayStopInfor;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.adapters.StationAdapter;
import com.example.guanghuahe.cst2335_finalmilestone1.OCTranspo.database.OCDatabaseHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.activities.Movie;

import java.util.ArrayList;

/**
 * Start Activity for Guanghua He's OCtranspo app
 */

public class OCTranspo extends AppCompatActivity {

    final Context ctxt = this;
    //private ProgressBar ocProgressBar;
    protected static final String ACTIVITY_NAME = "OCTranspoActivity";
    private Context ctx;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int currentStopIndex = 0;
    StationAdapter adapter;
    ListView stops;
    EditText stationInput;
    Button addStation;
    ArrayList<String> stopsList = new ArrayList<>();
    ArrayList<String> stopsNumbers = new ArrayList<>();
    Button delStation;

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

        stops =  findViewById(R.id.stationsView);
        stationInput =  findViewById(R.id.stopNoInput);
        addStation =  findViewById(R.id.addStopNoButton);
        adapter = new StationAdapter(this);
        stops.setAdapter(adapter);
        //ocProgressBar = findViewById(R.id.ocProgressBar);

        Toast toast = Toast.makeText(this, "Guanghua's OCTranspo Toast", Toast.LENGTH_LONG);
        toast.show();
        //ocProgressBar.setVisibility(View.VISIBLE);

        Log.e(ACTIVITY_NAME, "尝试查询 query:    SELECT " +
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
            Log.e(ACTIVITY_NAME, " 当前光标位置 Current cursor position: " + cursor.getPosition());
            String newStop = "Bus Stop number: ";
            newStop = newStop.concat(cursor.getString(1));

            stopsList.add(newStop);
            stopsNumbers.add(cursor.getString(1));
            cursor.moveToNext();
        }

        //When you click the floating action button it adds a bus number - CST2335 – Graphical Interface Programming Lab 3

        addStation.setOnClickListener((e) -> {
            String string = stationInput.getText().toString();
            boolean valid;
            try {
                valid = true;
            } catch (Exception ex) {
                valid = false;
            }
            if (string.length() == 0)
                valid = false;

            if (valid) {
                ContentValues newValues = new ContentValues();

                newValues.put(OCDatabaseHelper.STATION_NAME, "NAME_NOT_FOUND");
                newValues.put(OCDatabaseHelper.STATION_NO, string);

                db.insert(OCDatabaseHelper.TABLE_NAME, OCDatabaseHelper.STATION_NAME, newValues);

                String newStop = "Bus Stop number: ";
                newStop = newStop.concat(string);
                boolean add = stopsList.add(newStop);
                stopsNumbers.add(string);
                stationInput.setText("");
                adapter.notifyDataSetChanged();
            } else {
                Snackbar wronginput = Snackbar.make(findViewById(android.R.id.content), getString(R.string.oc_wronginput), Snackbar.LENGTH_SHORT);
                wronginput.show();
                stationInput.setText("");
            }

        });

        //When you click the delete action button it deletes all the  bus stop numbers - CST2335 – Graphical Interface Programming Lab 3

        delStation = findViewById(R.id.delAllStopNoButton);
        delStation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopsList.clear();
                adapter.notifyDataSetChanged();
            }
        });


        //Saves the text to the database, then loads the OCTranspoStop activity with the Stop number added
        stops.setOnItemClickListener((parent, view, position, id) -> {
            String ostring = stopsList.get(position);
            Log.i(ACTIVITY_NAME, "站点名称 Message: " + ostring);
            String stopNumber = stopsNumbers.get(position);
            //Passes the input text to new activity when starting the activity
            Intent i = new Intent(OCTranspo.this, DisplayStopInfor.class);
            i.putExtra("stationNumber", stopNumber);
            currentStopIndex = position;
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
                Intent intent2 = new Intent(OCTranspo.this, FoodActivity.class);
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

                String displayAboutString = getString(R.string.oc_about);

                builder.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayAboutString)
                        .create();

                AlertDialog dialog = builder.show();

                //TextView messageView = dialog.findViewById(android.R.id.message);
                //TextView titleView = dialog.findViewById(android.R.id.title);
                break;

            case R.id.menuItemHelp:
                final AlertDialog.Builder builder_help = new AlertDialog.Builder(ctxt).setTitle("OC Transpo App").setCancelable(false);

                String displayHelpString = getString(R.string.oc_help);

                builder_help.setPositiveButton("Ok", null)
                        .setIcon(R.drawable.about_icon)
                        .setMessage(displayHelpString)
                        .create();

                AlertDialog dialog_help = builder_help.show();

                //TextView messageView = dialog.findViewById(android.R.id.message);
                //TextView titleView = dialog.findViewById(android.R.id.title);
                break;


        }

        return true;

    }


    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");

        if (DisplayStopInfor.getDeleteStops()) {
            Log.i(ACTIVITY_NAME, "删除站点 Deleting station no " + currentStopIndex);
            String[] params = new String[1];
            params[0] = stopsNumbers.get(currentStopIndex);
            db.delete(OCDatabaseHelper.TABLE_NAME, OCDatabaseHelper.STATION_NO + "=?", params);

            adapter = new StationAdapter(this);
            stops.setAdapter(adapter);

            stopsList.remove(currentStopIndex);
            stopsNumbers.remove(currentStopIndex);
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
    /*
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
*/
    /**
     * This adapter updates/generates the list view elements
     * This was Largely based on CST2335 – Graphical Interface Programming Lab 4
     */
//    public class StationAdapter extends ArrayAdapter<String> {
//        public StationAdapter(Context ctx) {
//            super(ctx, 0);
//        }
//
//        @Override
//        public int getCount() {
//            return (stopsList.size());
//        }
//
//        @Override
//        public String getItem(int position) {
//            return stopsList.get(position);
//        }
//
//        @Override
//        public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater inflater = OCTranspo.this.getLayoutInflater();
//
//            View result = inflater.inflate(R.layout.oc_stop, null);
//
//            TextView stationText = result.findViewById(R.id.station_text);
//
//            stationText.setText(getItem(position));
//
//            return result;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//    }

    public ArrayList<String> getStopsList() {
        return stopsList;
    }
}



