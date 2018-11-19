package com.example.guanghuahe.cst2335_finalmilestone1.movie.dto;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieDTO implements Parcelable{


    private String movieName;
    private String director;
    private String year;
    private String summary;
    private String posterLink;
    private String gener;
    private String releasedDate;
    private String actors;
    private String imDbId;
    private String ratings_imDb;
    private String type;
    private String runtime;








    public MovieDTO(Parcel in) {
        movieName = in.readString();
        director = in.readString();
        year = in.readString();
        summary = in.readString();
        posterLink = in.readString();
        gener = in.readString();
        releasedDate = in.readString();
        actors = in.readString();
        imDbId = in.readString();
        ratings_imDb = in.readString();
        type = in.readString();

    }

    public static final Parcelable.Creator<MovieDTO> CREATOR = new Parcelable.Creator<MovieDTO>() {
        @Override
        public MovieDTO createFromParcel(Parcel in) {
            return new MovieDTO(in);
        }

        @Override
        public MovieDTO[] newArray(int size) {
            return new MovieDTO[size];
        }
    };

    public MovieDTO() {
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImDbId() {
        return imDbId;
    }

    public void setImDbId(String imDbId) {
        this.imDbId = imDbId;
    }

    public String getRatings_imDb() {
        return ratings_imDb;
    }

    public void setRatings_imDb(String ratings_imDb) {
        this.ratings_imDb = ratings_imDb;
    }

    public String getRuntime() { return runtime; }

    public void setRuntime(String runtime) { this.runtime = runtime;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(director);
        dest.writeString(year);
        dest.writeString(summary);
        dest.writeString(posterLink);
        dest.writeString(gener);
        dest.writeString(releasedDate);
        dest.writeString(actors);
        dest.writeString(imDbId);
        dest.writeString(ratings_imDb);
        dest.writeString(type);

    }
}
