package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model class from network
 */

public class Story implements Parcelable {

    private String title;
    private String link;
    private Date pubDate;
    private String author;
    private List<String> categories;
    private String description;
    private String comment = "";


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void addCategory(String category) {
        if (categories == null)
            categories = new ArrayList<>();
        categories.add(category);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", pubDate=" + pubDate +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", comment=" + comment +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeLong(this.pubDate != null ? this.pubDate.getTime() : -1);
        dest.writeString(this.author);
        dest.writeStringList(this.categories);
        dest.writeString(this.description);
        dest.writeString(this.comment);
    }

    public Story() {
    }

    protected Story(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        long tmpPubDate = in.readLong();
        this.pubDate = tmpPubDate == -1 ? null : new Date(tmpPubDate);
        this.author = in.readString();
        this.categories = in.createStringArrayList();
        this.description = in.readString();
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

}
