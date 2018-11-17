package com.example.guanghuahe.cst2335_finalmilestone1.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String Movie_ID = "imDbId";
    private static final String Movie_Title = "title";
    private static final String Movie_Year = "year";
    private static final String Movie_Poster = "poster";
    private static final String[] COLUMNS = {Movie_ID, Movie_Title, Movie_Year, Movie_Poster};
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
                "imdbID TEXT PRIMARY KEY, " + Movie_Title + " TEXT, " + Movie_Year + " TEXT, " + Movie_Poster + " TEXT )";
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

        ContentValues values = new ContentValues();
        values.put(Movie_ID, Movie.getImDbId());
        values.put(Movie_Title, Movie.getMovieName());
        values.put(Movie_Year, Movie.getYear());
        values.put(Movie_Poster, Movie.getPosterLink());

        db.insert(table_Movies, null, values);
        db.close();
    }

    /**
     * find movie by id within database
     * @param id
     * @return  data transfer Object of movie
     */
    public MovieDTO readMovieDetail(String id) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(table_Movies, COLUMNS, Movie_ID+" = ?", new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        MovieDTO Movie = new MovieDTO();
        Movie.setImDbId(id);
        Movie.setMovieName(cursor.getString(1));
        Movie.setYear(cursor.getString(2));
        Movie.setPosterLink(cursor.getString(2));
        db.close();
        return Movie;
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
                movie = new MovieDTO();
                movie.setImDbId(cursor.getString(0));
                movie.setMovieName(cursor.getString(1));
                movie.setYear(cursor.getString(2));
                movie.setPosterLink(cursor.getString(3));
                Movies.add(movie);
            } while (cursor.moveToNext());
        }
        db.close();
        return Movies;
    }

    public int updateMovieInfo(MovieDTO movie) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Movie_ID, movie.getImDbId());
        values.put(Movie_Title, movie.getMovieName());
        values.put(Movie_Year, movie.getYear());
        values.put(Movie_Poster, movie.getPosterLink());

        int i = db.update(table_Movies, values, Movie_ID + " = ?", new String[]{movie.getImDbId()});
        db.close();
        return i;
    }

    public void updateAllMovies(List movies){
        ArrayList<MovieDTO> movieList = (ArrayList<MovieDTO>) movies;
        SQLiteDatabase db = getWritableDatabase();
        for(MovieDTO movie: movieList){
            ContentValues values = new ContentValues();
            values.put(Movie_ID, movie.getImDbId());
            values.put(Movie_Title, movie.getMovieName());
            values.put(Movie_Year, movie.getYear());
            values.put(Movie_Poster, movie.getPosterLink());
            try{
                int i = db.update(table_Movies, values, Movie_ID + " = ?", new String[]{movie.getImDbId()});
            }catch (Exception e){
                Log.d(TAG, "--->updateAllMovies  Movie Name"+movie.getMovieName()+" Exception:"+e.getMessage() );
            }

        }
        db.close();
    }

    /**
     * update history list
     * @param movies
     * @return
     */
    public List udpateHistoryList(List movies){
        ArrayList<MovieDTO> movieList = (ArrayList<MovieDTO>) movies;
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < movieList.size(); i++) {
            MovieDTO movie = movieList.get(i);
            try {
                Cursor cursor = db.query(table_Movies, COLUMNS, Movie_ID + " = ?", new String[]{movie.getImDbId()}, null, null, null, null);

                if (cursor != null && cursor.getCount() > 0) {
                    movie.setFavourite(true);
                    movieList.set(i, movie);
                }
            }catch (Exception e){
                Log.d(TAG, "--->udpateMovieListWitIsFavourite  Movie Name"+movie.getMovieName()+" Exception:"+e.getMessage() );
            }

        }
        db.close();
        return movieList;
    }

    /**
     * remove movie from databse
     * @param Movie
     */
    public void deleteMovie(MovieDTO Movie) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table_Movies, Movie_ID + " = ?", new String[]{Movie.getImDbId()});
        db.close();
    }

}
