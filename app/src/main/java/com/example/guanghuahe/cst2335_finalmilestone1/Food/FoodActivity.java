package com.example.guanghuahe.cst2335_finalmilestone1.Food;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "FoodActivity";

    /**
     * view items' variables
     **/
    ListAdapter adapter;
    Button food_searchButton;
    EditText food_editText;
    ListView list;
    ProgressBar food_progressBar;
    String food_search;
    FoodDatabaseHelper foodDatabaseHelper;
    SQLiteDatabase db;
    ArrayList<HashMap<String, String>> foodItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        food_searchButton = findViewById(R.id.searchItem);
        food_editText = findViewById(R.id.itemText);
        list = findViewById(R.id.food_list);
        food_progressBar = findViewById(R.id.progress);

        Snackbar.make(findViewById(android.R.id.content), R.string.foodSearch, Snackbar.LENGTH_SHORT).show();
        food_progressBar.setVisibility(View.VISIBLE);
        foodItemList = new ArrayList<>();

        foodDatabaseHelper = new FoodDatabaseHelper(this);
        db = foodDatabaseHelper.getWritableDatabase();

        /**
         * http request
         **/
        food_searchButton.setOnClickListener(click->{
            food_search = food_editText.getText().toString();
            food_search = food_search.replace(" ","%20");
            FoodQuery querry = new FoodQuery();
            querry.execute();
            food_progressBar.setVisibility(View.VISIBLE);
            food_editText.setText("");
        });


        list.setOnItemClickListener(( parent,  view,  position,  id)-> {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FoodActivity.this)
                        .setMessage("Do you want to add this to favourites").setTitle("Save")
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDatabase db = foodDatabaseHelper.getWritableDatabase();

                                HashMap<String, String> m = foodItemList.get(position);//it will get selected HashMap Stored in array list

                                String[] strArr = new String[m.values().size()];
                                int i = 0;
                              //for (HashMap<String, String> hash : foodItemList) {
                                    for (String current : m.values()) {
                                        strArr[i] = current;
                                        i++;
                                    }
                               // }

                                ContentValues cValues = new ContentValues();
                                cValues.put(FoodDatabaseHelper.KEY_LABEL,strArr[0]);
                                cValues.put(FoodDatabaseHelper.KEY_CALORIES,strArr[1]);
                                cValues.put(FoodDatabaseHelper.KEY_FAT,strArr[2]);
                                cValues.put(FoodDatabaseHelper.KEY_CARBS,strArr[3]);
                                db.insert(FoodDatabaseHelper.TABLE_NAME,"NullColumnName",cValues);
                                Toast toast = Toast.makeText(getApplicationContext(),R.string.Saved, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                dialogBuilder.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override


    protected void onDestroy(){
        super.onDestroy();
        foodDatabaseHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            Intent intent = new Intent(FoodActivity.this, FoodFavorites.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.search) {
            Snackbar.make(this.findViewById(android.R.id.content),R.string.foodSearch, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return true;
        }
        else if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
            builder.setMessage(getString(R.string.Author) + "\n" + "\n" + getString(R.string.HowTo)).setTitle(R.string.Help).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            }).show();
            return true;
        }
        else if (id == R.id.action_settings) {
            Toast.makeText(FoodActivity.this, "Settings clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FoodQuery extends AsyncTask<String, Integer, String> {
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar.make(FoodActivity.this.findViewById(android.R.id.content),R.string.jsonDL, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            //whenever new search, clear list first
            foodItemList.clear();

            try {
                URL url = new URL("https://api.edamam.com/api/food-database/parser?app_id=c4de01c3&app_key=f97ccfddafbb8caded9475fea9181daf&ingr=" + food_search);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder json_results = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    json_results.append(line + "\n");
                }
                String result = json_results.toString();


                JSONObject json_Object = new JSONObject(result);
                publishProgress(20);

                jsonArray = json_Object.getJSONArray("hints");

                for (int index = 0; index < jsonArray.length(); index++)
                    try {
                        JSONObject indexObject = jsonArray.getJSONObject(index);
                        JSONObject foodObject = indexObject.getJSONObject("food");
                        // Pulling items from the array
                        String  label = foodObject.getString("label");
                        //打印 LABEL
                        Log.e("label ========", label);
                        publishProgress(20);
                        JSONObject nutrition_Object = foodObject.getJSONObject("nutrients");
                        Log.i(ACTIVITY_NAME, nutrition_Object.toString());

                        publishProgress(60);
                        String calorieValue = nutrition_Object.getString("ENERC_KCAL");
                        Log.e("ENERC_KCAL ========", calorieValue);

                        publishProgress(80);
                        String  fatValue = nutrition_Object.getString("FAT");
                        Log.e("FAT ========", fatValue);

                        publishProgress(90);
                        String  carbValue = nutrition_Object.getString("CHOCDF");
                        Log.e("CHOCDF ========", carbValue);

                        publishProgress(100);

                        HashMap<String, String> food = new HashMap<>();
                        food.put("Label", label);
                        food.put("Calories", "Calories: "+ formatDecimal(calorieValue) );
                        Log.e("Calories ===转换后=====", formatDecimal(calorieValue));
                        food.put("Fat", "Fat: " + formatDecimal(fatValue) + "g");
                        Log.e("Fat ===转换后=====", formatDecimal(fatValue));
                        food.put("Carbs", "Carb: " + formatDecimal(carbValue)+ "g");
                        Log.e("Carbs ====转换后====", formatDecimal(carbValue));
                        foodItemList.add(food);

                    } catch (JSONException e) {
                    }
            }catch (Exception e)
            {
                Log.i("Exception", e.getMessage());
            }
            return "done";
        }

        @Override
        public void onProgressUpdate(Integer ... args){
            food_progressBar.setVisibility(View.VISIBLE);
            food_progressBar.setProgress(args[0]);
        }

        @Override
        public void onPostExecute(String s){
            super.onPostExecute(s);
            if (jsonArray == null || jsonArray.isNull(0)) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                View view = toast.getView();
                view.setBackgroundColor(Color.RED);
                toast.show();
            }else{

                adapter = new SimpleAdapter(FoodActivity.this, foodItemList,
                        R.layout.food_info, new String[]{ "Label","Calories", "Fat", "Carbs"},
                        new int[]{R.id.foodLabel, R.id.food_calories_view, R.id.food_fat_view, R.id.food_cabrbo_view});
                list.setAdapter(adapter);


            }
            food_progressBar.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * show results with two decimal
     * @param s
     * @return
     */
    private String formatDecimal(String s){
        Double dec = Double.parseDouble(s);
        DecimalFormat df = new DecimalFormat("##.00");
        return ""+df.format(dec);
       // return s.split(".",2)[0];
       /* int posDot = s.indexOf(".");
        if (posDot <= 0) return s;
        if (s.length() - posDot - 1 > 2)
        {
            s.substring(0, posDot + 4);
        }*/

       // return s;
    }
}
