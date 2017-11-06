package com.example.company.thenewsroom.Interface;

import com.example.company.thenewsroom.Model.News;
import com.example.company.thenewsroom.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by jatin on 31/10/17.
 */

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
