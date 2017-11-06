package com.example.company.thenewsroom.Common;

import com.example.company.thenewsroom.Interface.IconBetterIdeaService;
import com.example.company.thenewsroom.Interface.NewsService;
import com.example.company.thenewsroom.Remote.IconBetterIdeaClient;
import com.example.company.thenewsroom.Remote.RetrofitClient;

/**
 * Created by jatin on 31/10/17.
 */

public class Common {

    public static final String API_KEY="764404a74fb34a2c82d6e207d6c340b2";

    private static final String BASE_URL=" https://newsapi.org/";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getAPIUrl(String source,String sortBy,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source)
                .append("&sortBy=")
                .append(sortBy)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }

}
