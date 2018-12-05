package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;
//import statements

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class News {

    //Constants
    private static News sNews;
    private ArrayList<Article> mArticles;


    public static News get(Context context) {
        if (sNews == null) {
            sNews = new News(context);
        }
        return sNews;
    }

    /**
     * Creates cards List
     * param context
     */
    private News(Context context){
        this.mArticles = new ArrayList<Article>();

    }

    public void clearArticles() {
        this.mArticles = new ArrayList<Article>();
    }

    public Article getArticle (UUID id) {
        for (Article article: mArticles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public ArrayList<Article> getArticles(){
        return mArticles;
    }

    public void setArticles(ArrayList<Article> newNews){
        this.mArticles = newNews;
    }
}