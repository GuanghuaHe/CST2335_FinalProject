package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;



//import statements
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Article implements Parcelable{

    //variables
    private UUID mId;
    private String mHeadline;
    private String mImageUrl;
    private String mNewsUrl;
    private String mDescription;

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Calls constructor
     * param theImage
     * param theQuestion
     * param theAnswer
     */
    public Article(String theHeadline, String theImageUrl, String theNewsUrl,  String theDescription) {
        mId = (UUID.randomUUID());
        mHeadline = theHeadline;
        mImageUrl = theImageUrl;
        mNewsUrl = theNewsUrl;
        mDescription = theDescription;

    }

    /**
     * Retrieving Article data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Article(Parcel in){

        this.mId = (UUID) in.readSerializable();
        this.mHeadline = in.readString();
        this.mImageUrl = in.readString();
        this.mNewsUrl = in.readString();
        this.mDescription = in.readString();
    }

    /**
     * Storing the Article data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mId);
        dest.writeString(mHeadline);
        dest.writeString(mImageUrl);
        dest.writeString(mNewsUrl);
        dest.writeString(mDescription);

    }
    public static final Creator<Article> CREATOR = new Creator<Article>() {

        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public UUID getId() {
        return mId;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public String toString(){
        return mHeadline;
    }
}