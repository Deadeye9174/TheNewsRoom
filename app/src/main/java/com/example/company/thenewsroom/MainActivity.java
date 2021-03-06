package com.example.company.thenewsroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.company.thenewsroom.Adapter.ListSourceAdapter;
import com.example.company.thenewsroom.Common.Common;
import com.example.company.thenewsroom.Interface.NewsService;
import com.example.company.thenewsroom.Model.WebSite;
import com.google.gson.Gson;
//import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    android.app.AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    TextView text;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

        text = ((NavigationView)findViewById(R.id.navigation)).getHeaderView(0).findViewById(R.id.nameoftheuser);

        name = getIntent().getStringExtra("firstName")+" "+ getIntent().getStringExtra("lastName");

        text.setText(name);



        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        try{
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.logout)
                {

                    Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                    Toast.makeText(MainActivity.this, "Logout Succesful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }

                else if(id == R.id.share)
                {
                    Toast.makeText(MainActivity.this, "Share this app!!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


        //Init Cache
        Paper.init(this);

        //Init Service
        mService = Common.getNewsService();

        //Init View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        listWebsite = (RecyclerView) findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);

    }

    private void loadWebsiteSource(boolean isRefreshed) {
        if(!isRefreshed) {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()) {
                WebSite webSite = new Gson().fromJson(cache, WebSite.class); // Convert cache from JSON to Object
                adapter = new ListSourceAdapter(getBaseContext(),  webSite.getSources());
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else
            {
                dialog.show();

                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(),response.body().getSources());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to Cache

                        Paper.book().write("cache",new Gson().toJson(response.body()));

                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }
        }
        else
        {
            dialog.show();

            dialog.dismiss();
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(),response.body().getSources());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to Cache

                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    //Dismiss Refresh

                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }
}
