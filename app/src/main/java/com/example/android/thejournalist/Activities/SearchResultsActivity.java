package com.example.android.thejournalist.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.CallBack;
import com.example.android.thejournalist.Utilites.Constants;
import com.example.android.thejournalist.Utilites.NavDrawer;
import com.example.android.thejournalist.Utilites.VolleyRequest;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();
    private VolleyRequest volleyRequest;
    private ArrayList<News> newsArrayList;
    private ListView listView;
    private TextView noNewsTextView;
    private ProgressBar progressBar;
    private NavDrawer navDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navDrawer = new NavDrawer(this, navigationView);


        listView = findViewById(R.id.lv_news);
        noNewsTextView = findViewById(R.id.tv_no_news);
        progressBar = findViewById(R.id.pb_list);
        progressBar.setVisibility(View.GONE);

        handleIntent(getIntent());
    }

    private void search(final String query) {
        progressBar.setVisibility(View.VISIBLE);
        volleyRequest = new VolleyRequest(SearchResultsActivity.this);
        Uri uri = Uri.parse(Constants.EVERYTHING_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.QUERY_PARAM, query)
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();

        volleyRequest.requestData(uri, new CallBack() {
            @Override
            public void onCallback(ArrayList<News> resultArrayList) {
                newsArrayList = resultArrayList;
                if (newsArrayList != null && newsArrayList.size() > 0) {
                    noNewsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    setNews(newsArrayList);
                } else {
                    noNewsTextView.setText(getString(R.string.no_search_reasults_found) + " \"" + query + "\"");
                    noNewsTextView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }


    private void setNews(final ArrayList<News> newsArrayList) {
        progressBar.setVisibility(View.GONE);
        NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.news_item_view, newsArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultsActivity.this, DetailsActivity.class);
                intent.putExtra("news", newsArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        volleyRequest.cancelAll();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        navDrawer.onNavItemClick(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
