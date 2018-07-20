package com.example.android.thejournalist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.NavDrawer;
import com.example.android.thejournalist.Utilites.SharedPreference;

import java.util.ArrayList;

public class FavortiesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = FavortiesActivity.class.getSimpleName();
    SharedPreference sharedPreference;
    ListView listView;
    TextView noNewsTextView;
    NewsAdapter newsAdapter;
    FloatingActionButton deleteFavFloatingButton;
    ProgressBar progressBar;
    NavDrawer navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        deleteFavFloatingButton = findViewById(R.id.fab_delete_all_fav);
        progressBar = findViewById(R.id.pb_list);

        progressBar.setVisibility(View.VISIBLE);
        noNewsTextView.setText("You don't have favorite news yet");

        getFavoriteNews();

        deleteFavFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.removeAll();
                Helper.displayToast(FavortiesActivity.this, "Favorites deleted");
                getFavoriteNews();
            }
        });

    }

    private void getFavoriteNews() {
        sharedPreference = new SharedPreference(this);
        ArrayList<News> favoritesArrayList = sharedPreference.getFavorites();
        if (favoritesArrayList != null && favoritesArrayList.size() > 0) {
            // favorites is in shared preferences
            setNews(favoritesArrayList);

        } else {
            sharedPreference.getNewsFromFirebase(new CallBack() {
                @Override
                public void onCallback(ArrayList<News> newsArrayList) {
                    if (newsArrayList == null || newsArrayList.size() <= 0) {
                        noNewsTextView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        setNews(newsArrayList);
                        //save fav from firebase to shared preferences
                        sharedPreference.saveFavorites(newsArrayList);
                    }
                }
            });
        }
    }


    private void setNews(final ArrayList<News> favoritesArrayList) {
        progressBar.setVisibility(View.GONE);
        noNewsTextView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        newsAdapter = new NewsAdapter(this, R.layout.news_item_view, favoritesArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*DetailsFragment detailsFragment=new DetailsFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.view_pager, detailsFragment);
                transaction.commit();*/
                Intent intent = new Intent(FavortiesActivity.this, DetailsActivity.class);
                intent.putExtra("news", favoritesArrayList.get(position));
                startActivity(intent);
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        getFavoriteNews();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getFavoriteNews();
    }
}
