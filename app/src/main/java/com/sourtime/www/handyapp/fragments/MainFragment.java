package com.sourtime.www.handyapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourtime.www.handyapp.R;
import com.sourtime.www.handyapp.models.Article;
import com.sourtime.www.handyapp.models.ArticleList;
import com.sourtime.www.handyapp.networking.NewsClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 07/12/2017.
 */

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private Call<ArticleList> topNewsHeadlinesAPI;
    private RecyclerView recyclerViewNews;
    private ArticleList topNewsArticlesList;
    private NewsListAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView called.");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerViewNews = rootView.findViewById(R.id.recyclerviewNews);
        StaggeredGridLayoutManager layoutManager;

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onViewCreated called.");
        super.onViewCreated(view, savedInstanceState);
        topNewsHeadlinesAPI = NewsClient.getInstance(getActivity()).getTopHeadlinesAPI().getTopHeadlines();
        recyclerViewNews.setAdapter(new NewsListAdapter(null));
    }

    @Override
    public void onResume() {
        Log.i(TAG,"onResume called.");
        super.onResume();
        if (newsAdapter == null){
            getTopNewsArticles();
        }
    }

    private void getTopNewsArticles(){
        topNewsHeadlinesAPI.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                Log.i(TAG,"response: " + response.body().getArticles().get(0).getTitle());
                topNewsArticlesList = response.body();
                populateNewsRecyclerView();
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Log.e(TAG,"error: " + t.getMessage());
            }
        });
    }

    private void populateNewsRecyclerView(){
        newsAdapter = new NewsListAdapter(topNewsArticlesList);
        recyclerViewNews.setAdapter(newsAdapter);
//        if (recyclerViewNews.getAdapter() == null){
//            Log.i(TAG,"adapter is null, creating adapter.");
//
//        }else{
////            newsAdapter.notifyDataSetChanged();
//        }
    }

    private class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.CustomNewsViewHolder> {
        ArticleList articleList;

        public NewsListAdapter(ArticleList articleList){
            super();
            this.articleList = articleList;
        }

        @Override
        public CustomNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_news_article,parent,false);
            return new CustomNewsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomNewsViewHolder holder, int position) {
            Article a = articleList.getArticles().get(position);

            holder.tvHeadline.setText(a.getTitle());
            holder.tvDescription.setText(a.getDescription());
            Picasso.with(getActivity())
                    .load(a.getUrlToImage())
                    .placeholder(R.mipmap.placeholdernews)
                    .resize(250, 250)
                    .centerCrop()
                    .into(holder.ivImage);
        }

        @Override
        public int getItemCount() {
            if (articleList != null){
                return articleList.getArticles().size();
            }else{
                return 0;
            }
        }

        class CustomNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvHeadline;
            private TextView tvDescription;
            private ImageView ivImage;

            public CustomNewsViewHolder(View itemView) {
                super(itemView);
                tvHeadline = itemView.findViewById(R.id.tvHeadline);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                ivImage = itemView.findViewById(R.id.ivImage);
            }


            @Override
            public void onClick(View view) {

            }
        }
    }

}
