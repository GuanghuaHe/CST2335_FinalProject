package com.example.guanghuahe.cst2335_finalmilestone1.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.guanghuahe.cst2335_finalmilestone1.movie.BitmapConverter;
import com.example.guanghuahe.cst2335_finalmilestone1.movie.dto.MovieDTO;

import java.util.ArrayList;


import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * version number
     */
    private static final int database_VERSION = 1;
    /**
     * database name
     */
    private static final String database_NAME = "Movie.db";
    /**
     * table name
     */
    private static final String table_Movies = "Movies";
    /**
     * columns
     */
    private static final String Movie_ID = "imdbID";
    private static final String Movie_Title = "title";
    private static final String Movie_Year = "year";
    private static final String Movie_Poster = "poster";
    private static final String Movie_Runtime = "runtime";
    private static final String Movie_Rate = "Rating";
    private static final String Movie_Image = "image";
    private static final String Movie_Direcotr = "director";
    private static final String Movie_Plot = "plot";
    private static final String Movie_Actors = "actors";
    private static final String Movie_Type = "type";
    private static final String Movie_Gener = "gener";









    private static final String[] COLUMNS =
            {Movie_ID, Movie_Title, Movie_Year,Movie_Runtime,Movie_Rate,Movie_Poster, Movie_Image,
                    Movie_Direcotr,Movie_Plot,Movie_Actors,Movie_Type,Movie_Gener};
    /**
     * current context name for log track
     */
    private static final String TAG = DatabaseHelper.class.getSimpleName();


    /**
     * constructor
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * SQL statement to create movie table
         */
        String CREATE_Movie_TABLE = "CREATE TABLE " + table_Movies + " ( " +
                "imdbID TEXT PRIMARY KEY, " + Movie_Title + " TEXT, " + Movie_Year + " TEXT, "+ Movie_Runtime + " TEXT, "+ Movie_Rate + " TEXT, " +Movie_Poster + " TEXT, "+ Movie_Image + " Blob(20)," +
                Movie_Direcotr + " TEXT, " + Movie_Plot + " TEXT, "+ Movie_Actors +" TEXT, " + Movie_Type + " TEXT, "+ Movie_Gener +" TEXT )";
        // execute SQL statement
        sqLiteDatabase.execSQL(CREATE_Movie_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_Movies);
        this.onCreate(sqLiteDatabase);
    }

    /**
     * add a movie into Database
     * @param Movie selected movie from search list to be added
     */
    public void insertMovie(MovieDTO Movie) {

        SQLiteDatabase db = this.getWritableDatabase();
        String id = Movie.getImDbId();
        long count = DatabaseUtils.queryNumEntries(db, table_Movies,Movie_ID + "=?", new String[] {id});
        if(count == 0) {
            ContentValues values = new ContentValues();
            values.put(Movie_ID, Movie.getImDbId());
            values.put(Movie_Title, Movie.getMovieName());
            values.put(Movie_Year, Movie.getYear());
            values.put(Movie_Poster, Movie.getPosterLink());
            values.put(Movie_Runtime, Movie.getRuntime());
            values.put(Movie_Rate, Movie.getRatings_imDb());
            values.put(Movie_Image, BitmapConverter.getByte(Movie.getImage()));
            values.put(Movie_Direcotr, Movie.getDirector());
            values.put(Movie_Plot,Movie.getSummary());
            values.put(Movie_Actors,Movie.getActors());
            values.put(Movie_Type,Movie.getType());
            values.put(Movie_Gener,Movie.getGener());




            db.insert(table_Movies, null, values);
            db.close();
        }
    }


    /**
     * find movie by id within database, but required details are more than table info within database
     *
     * we use loading url instead.
     * @param id
     * @return  data transfer Object of movie
     */
    public MovieDTO readMovieDetail(String id) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(table_Movies, COLUMNS, Movie_ID+" = ?", new String[]{id}, null, null, null, null);
        if (cursor != null && cursor.getCount()>0){
           Log.e(TAG,"COLUMN 0: ----"+ cursor.getColumnName(0));
            Log.e(TAG,"COLUMN 1: ----"+ cursor.getColumnName(1));
            Log.e(TAG,"COLUMN 2: ----"+ cursor.getColumnName(2));
            Log.e(TAG,"COLUMN 3: ----"+ cursor.getColumnName(3));
            Log.e(TAG,"COLUMN 4: ----"+ cursor.getColumnName(4));
            Log.e(TAG,"COLUMN 5: ----"+ cursor.getColumnName(5));
            Log.e(TAG,"COLUMN 6: ----"+ cursor.getColumnName(6));
            Log.e(TAG,"COLUMN 7: ----"+ cursor.getColumnName(7));
            Log.e(TAG,"COLUMN 8: ----"+ cursor.getColumnName(8));
            Log.e(TAG,"COLUMN 9: ----"+ cursor.getColumnName(9));
            Log.e(TAG,"COLUMN 10: ----"+ cursor.getColumnName(10));
            Log.e(TAG,"COLUMN 11: ----"+ cursor.getColumnName(11));
            cursor.moveToFirst();
            MovieDTO Movie = loadMovieDTO(cursor);

             db.close();
        return Movie;
        }
        return null;
    }


    /**
     *  read all of movies in the current databse, put into items of ListView
     * @return
     */
    public List getAllMovies() {
        List Movies = new ArrayList();
        String query = "SELECT  * FROM " + table_Movies;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MovieDTO movie = null;
        if (cursor.moveToFirst()) {
            do {
                movie = loadMovieDTO(cursor);
                Movies.add(movie);
            } while (cursor.moveToNext());
        }
        db.close();
        return Movies;
    }



    public void updateAllMovies(List movies){
        ArrayList<MovieDTO> movieList = (ArrayList<MovieDTO>) movies;
        SQLiteDatabase db = getWritableDatabase();
        for(MovieDTO Movie: movieList){
            ContentValues values = new ContentValues();
            values.put(Movie_ID, Movie.getImDbId());
            values.put(Movie_Title, Movie.getMovieName());
            values.put(Movie_Year, Movie.getYear());
            values.put(Movie_Poster, Movie.getPosterLink());
            values.put(Movie_Runtime, Movie.getRuntime());
            values.put(Movie_Rate, Movie.getRatings_imDb());
            values.put(Movie_Image, BitmapConverter.getByte(Movie.getImage()));
            try{
                int i = db.update(table_Movies, values, Movie_ID + " = ?", new String[]{Movie.getImDbId()});
            }catch (Exception e){
                Log.d(TAG, "--->updateAllMovies  Movie Name"+Movie.getMovieName()+" Exception:"+e.getMessage() );
            }

        }
        db.close();
    }



    /**
     * remove movie from databse
     * @param Movie
     */
    public int deleteMovie(MovieDTO Movie) {
        Log.e("SQLite", "----delete a movie----");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            Log.i(TAG, "ROWS-COUNT =  ----BEFORE----->" +getRowsCount() + " PK-VALUE --->" + Movie.getImDbId());
            db.execSQL("delete from " + table_Movies + " where imdbID = ?",
                    new Object[] {Movie.getImDbId() });
            Log.i(TAG, "ROWS-COUNT =  ----AFTER----->" +getRowsCount() + " !" );
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        return 1;
    }

    public void removeAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table_Movies, "1", null);
        db.close();
    }


    /**
     *
     * private help method for code reusing.
     * @param cursor
     * @return
     */
    private MovieDTO loadMovieDTO(Cursor cursor){
        MovieDTO Movie = new MovieDTO();
        Movie.setImDbId(cursor.getString(0));
        Movie.setMovieName(cursor.getString(1));
        Movie.setYear(cursor.getString(2));
        Movie.setRuntime(cursor.getString(3));
        Movie.setRatings_imDb(cursor.getString(4));
        Movie.setPosterLink(cursor.getString(5));
        Movie.setImage(BitmapConverter.bytes2Bimap(cursor.getBlob(6)));
        Movie.setDirector(cursor.getString(7));
        Movie.setSummary(cursor.getString(8));
        Movie.setActors(cursor.getString(9));
        Movie.setType(cursor.getString(10));
        Movie.setGener(cursor.getString(11));
        return Movie;
    }

    private long getRowsCount(){
        SQLiteDatabase db = getWritableDatabase();
        return DatabaseUtils.queryNumEntries(db, table_Movies);
    }
}
