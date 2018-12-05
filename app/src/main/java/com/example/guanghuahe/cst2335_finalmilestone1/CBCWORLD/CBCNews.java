package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;

//source: https://www.programcreek.com/java-api-examples/?code=frank-tan/XYZReader/XYZReader-master/XYZReader/src/main/java/com/example/xyzreader/ui/ArticleDetailFragment.java

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class CBCNews {

    /**
     * class variable
     * @sNews used for getting the new news
     * @mArticles  used to get the articles
     */
    private static CBCNews sNews;
    private ArrayList<CBCArticle> mArticles;


    public static CBCNews get(Context context) {
        if (sNews == null) {
            sNews = new CBCNews(context);
        }
        return sNews;
    }

    /**
     * Creates an array list of articles
     * param context
     */
    private CBCNews(Context context){
        this.mArticles = new ArrayList<CBCArticle>();

    }

    public void clearArticles() {
        this.mArticles = new ArrayList<CBCArticle>();
    }

    public CBCArticle getArticle (UUID id) {
        for (CBCArticle article: mArticles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public ArrayList<CBCArticle> getArticles(){
        return mArticles;
    }

    public void setArticles(ArrayList<CBCArticle> newNews){
        this.mArticles = newNews;
    }
}