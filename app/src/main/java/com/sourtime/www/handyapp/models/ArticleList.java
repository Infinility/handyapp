package com.sourtime.www.handyapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 08/12/2017.
 */

public class ArticleList {
    @SerializedName("articles")
    private List<Article> articles;

    public List<Article> getArticles(){
        return articles;
    }
}
