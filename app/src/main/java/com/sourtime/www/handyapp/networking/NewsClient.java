package com.sourtime.www.handyapp.networking;


import android.content.Context;
import android.content.res.Resources;

import com.sourtime.www.handyapp.R;
import com.squareup.okhttp.OkHttpClient;

//import retrofit.RestAdapter;
//import retrofit.client.OkClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 08/12/2017.
 */

public class NewsClient {

    private String ENDPOINT = "https://newsapi.org/v2/";
    private String TOP_HEADLINES="top-headlines?sources=bbc-news&apiKey=";
    private static NewsAPI topHeadlinesAPI;
    private static NewsClient newsClient;
    private String apiKey;


    private NewsClient(Context c){
        Resources res = c.getResources();
        apiKey = res.getString(R.string.news_api_key);
    }

    public static NewsClient getInstance(Context c){
        if (newsClient == null) newsClient = new NewsClient(c);
        return newsClient;
    }

    public NewsAPI getTopHeadlinesAPI(){
        Retrofit retrofitTopHeadlines = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        topHeadlinesAPI = retrofitTopHeadlines.create(NewsAPI.class);
        return  topHeadlinesAPI;
    }


}
