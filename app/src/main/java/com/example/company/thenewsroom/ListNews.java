package com.example.company.thenewsroom;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.company.thenewsroom.Adapter.ListNewsAdapter;
import com.example.company.thenewsroom.Common.Common;
import com.example.company.thenewsroom.Interface.NewsService;
import com.example.company.thenewsroom.Model.Article;
import com.example.company.thenewsroom.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    android.app.AlertDialog dialog;
    NewsService mServivce;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source="",sortBy="",webHotURL="";

    ListNewsAdapter adapter;
    RecyclerView lstnews;
    RecyclerView.LayoutManager layoutManager;
    DiagonalLayout diagonalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //Service
        mServivce = Common.getNewsService();

        dialog = new SpotsDialog(this);

        //View
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source,true);
            }
        });
        //Some changes

        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(getBaseContext(),DetailArticle.class);
                detail.putExtra("webURL",webHotURL);
                startActivity(detail);
            }
        });
        kbv = (KenBurnsView) findViewById(R.id.top_image);
        top_author = (TextView)findViewById(R.id.top_author);
        top_title = (TextView)findViewById(R.id.top_title);
        lstnews = (RecyclerView)findViewById(R.id.lstnews);
        lstnews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstnews.setLayoutManager(layoutManager);

        //Intent
        if(getIntent() != null)
        {
            source = getIntent().getStringExtra("source");
            sortBy = getIntent().getStringExtra("sortBy");
            if(!source.isEmpty() && !sortBy.isEmpty())
            {
                loadNews(source,false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {
        if(!isRefreshed)
        {
            dialog.show();
            mServivce.getNewestArticles(Common.getAPIUrl(source,sortBy,Common.API_KEY))
            .enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    //Get First Article
                    try {
                        Picasso.with(getBaseContext())
                                .load(response.body().getArticles().get(0).getUrlToImage())
                                .into(kbv);

                        top_title.setText(response.body().getArticles().get(0).getTitle());
                        top_author.setText(response.body().getArticles().get(1).getAuthor());

                        webHotURL = response.body().getArticles().get(0).getUrl();

                        //Load remaining articles

                        List<Article> removeFirstItem = response.body().getArticles();

                        removeFirstItem.remove(0);
                        adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                        adapter.notifyDataSetChanged();
                        lstnews.setAdapter(adapter);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }
        else
        {
            dialog.show();
            mServivce.getNewestArticles(Common.getAPIUrl(source,sortBy,Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();
                            //Get First Article
                            Picasso.with(getBaseContext())
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kbv);

                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotURL = response.body().getArticles().get(0).getUrl();

                            //Load remaining articles

                            List<Article> removeFirstItem = response.body().getArticles();
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem,getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstnews.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
