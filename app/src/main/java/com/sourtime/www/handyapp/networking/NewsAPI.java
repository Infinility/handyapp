package com.sourtime.www.handyapp.networking;

import com.sourtime.www.handyapp.models.Article;
import com.sourtime.www.handyapp.models.ArticleList;

import java.util.List;

//import retrofit.Callback;
//import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 08/12/2017.
 */

public interface NewsAPI {

    @GET("top-headlines?sources=bbc-news&apiKey=b6ce7e9957fb4cf69174f0b467a7e168")
    Call<ArticleList> getTopHeadlines();

}
