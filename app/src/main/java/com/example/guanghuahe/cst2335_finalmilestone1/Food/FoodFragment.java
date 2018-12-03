package com.example.guanghuahe.cst2335_finalmilestone1.Food;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.guanghuahe.cst2335_finalmilestone1.R;


public class FoodFragment extends Fragment {

    FoodDatabaseHelper dbHelper;
    SQLiteDatabase db;
    FoodActivity parent;

   public FoodFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle infoToPass = getArguments();
//        super.onCreate(savedInstanceState);

        dbHelper = new FoodDatabaseHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        parent = new FoodActivity();
        View view = inflater.inflate(R.layout.fragment_food,container,false);

        //toolbar = (Toolbar) view.findViewById(R.id.toolbar); //set up toolbar
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //if (this.getActivity().getActionBar() != null) this.getActivity().getActionBar().setDisplayShowTitleEnabled(false);
        //setHasOptionsMenu(false); //needed to make option menus to appear
       // toolbar = (Toolbar) view.findViewById(R.id.toolbar); //set up toolbar
       // ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        if (this.getActivity().getActionBar() != null) this.getActivity().getActionBar().setDisplayShowTitleEnabled(false);
//        setHasOptionsMenu(false); //needed to make option menus to appear



        Cursor foods = db.rawQuery("select * from " + FoodDatabaseHelper.TABLE_NAME, null);
        ListView favList= view.findViewById(R.id.food_favorite_listview);


        String[] columns = new String[] { FoodDatabaseHelper.KEY_LABEL, FoodDatabaseHelper.KEY_CALORIES, FoodDatabaseHelper.KEY_FAT, FoodDatabaseHelper.KEY_CARBS };
        // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
        int[] to = new int[] { R.id.foodLabel, R.id.food_calories_view, R.id.food_fat_view, R.id.food_cabrbo_view };
        favList.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.food_info, foods, columns, to));


        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.DeleteMenu).setTitle(R.string.Delete)
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which ) {
                                SQLiteDatabase db = dbHelper.getReadableDatabase();
                                db.delete(FoodDatabaseHelper.TABLE_NAME, FoodDatabaseHelper.KEY_ID + "=?", new String[] {Long.toString(id)});
                                Toast.makeText(getActivity(), "Deleted id:" + id, Toast.LENGTH_SHORT).show();
                                Cursor foods = db.rawQuery("select * from " + FoodDatabaseHelper.TABLE_NAME, null);
                                favList.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.food_info, foods, columns, to));
                            }

                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                dialogBuilder.show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Snackbar.make(getActivity().findViewById(android.R.id.content),R.string.favoritesList, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }
        if (id == R.id.search) {
            Intent intent = new Intent(getActivity(), FoodActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.Author) + "\n" + "\n" + getString(R.string.HowTo)).setTitle(R.string.Help).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            }).show();
            return true;
        }
        else if (id == R.id.action_settings) {
            Toast.makeText(getActivity(), "Settings clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}