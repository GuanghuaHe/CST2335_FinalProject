package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;



//source:https://medium.com/@ssaurel/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class CBCArticle implements Parcelable{

    /**
     * @param mId used to identify an immutable universally unique id, used to retrieve
     * a type 4 pseudo randomly generated UUID
     * @param mHeadline variable used in the CBCArticle method to identify the headline
     *  and to store headline data to parcel object and to get the headline
     * @param mImageUrl used to retrieve and store the image url in the CBCArticle method and
     * @param mNewsUrl used to retrieve and store the news url in the CBCArticle method and
     * @param mDescription used to retrieve and store the CBCArticle description
     */
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
     * CBCArticle class constructor initializes class variables  mId, mHeadline, mImageUrl, mNewsUrl, mDescription
     * with parameters theHeadline, theImageURl, theNewsUrl, theDescription
     * param theHeadline
     * param theImageUrl
     * param theNewsUrl
     * param theDescription
     */
    public CBCArticle(String theHeadline, String theImageUrl, String theNewsUrl, String theDescription) {
        mId = (UUID.randomUUID());
        mHeadline = theHeadline;
        mImageUrl = theImageUrl;
        mNewsUrl = theNewsUrl;
        mDescription = theDescription;

    }

    /**
     * This constructor retrieves CBCArticle data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private CBCArticle(Parcel in){

        this.mId = (UUID) in.readSerializable();
        this.mHeadline = in.readString();
        this.mImageUrl = in.readString();
        this.mNewsUrl = in.readString();
        this.mDescription = in.readString();
    }

    /**
     * The writeToParcel method stores the CBCArticle data to the Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mId);
        dest.writeString(mHeadline);
        dest.writeString(mImageUrl);
        dest.writeString(mNewsUrl);
        dest.writeString(mDescription);

    }

    /**
     * createFromParcel method Generates a new instance of the Parcelable class from the CBCArticle class
     * whose data had been previously written by writeToParcel(Parcel dest, int flags)
     * newArray (int size) creates an array of the Parcelable CBCArticle class
     */
    public static final Creator<CBCArticle> CREATOR = new Creator<CBCArticle>() {

        @Override
        public CBCArticle createFromParcel(Parcel source) {
            return new CBCArticle(source);
        }

        @Override
        public CBCArticle[] newArray(int size) {
            return new CBCArticle[size];
        }
    };

    /**
     * getter to return the mId of type UUID
     * @return mId
     */
    public UUID getId() {
        return mId;
    }

    /**
     * Getter to return the headline
     * @return mHeadline
     */
    public String getHeadline() {
        return mHeadline;
    }

    /**
     * getter to return the imageUrl
     * @return
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * getter to return the news URL
     * @return mNewsUrl
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }

    /**
     * getter to return the article description
     * @return mDescription
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * toString method returns mHeadline
     * @return mHeadline
     */
    @Override
    public String toString(){
        return mHeadline;
    }
}