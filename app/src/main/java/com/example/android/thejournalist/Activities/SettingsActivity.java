package com.example.android.thejournalist.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.thejournalist.Adapters.CountryAdapter;
import com.example.android.thejournalist.Models.Country;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.NavDrawer;
import com.example.android.thejournalist.Utilites.SharedPreference;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String country;
    private SharedPreference sharedPreference;
    private NavDrawer navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_acitivty);

        sharedPreference = new SharedPreference(this);

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

        navDrawer = new NavDrawer(this);


        TextView changeCountryTextView = findViewById(R.id.tv_settings_change_country);
        changeCountryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryDialog();
            }
        });
    }

    private void showCountryDialog() {
        final ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Australia", "au", R.drawable.australia));
        countries.add(new Country("Brazil", "br", R.drawable.brazil));
        countries.add(new Country("Canada", "ca", R.drawable.canada));
        countries.add(new Country("China", "cn", R.drawable.china));
        countries.add(new Country("Egypt", "eg", R.drawable.egypt));
        countries.add(new Country("France", "fr", R.drawable.france));
        countries.add(new Country("India", "in", R.drawable.india));
        countries.add(new Country("Italy", "it", R.drawable.italy));
        countries.add(new Country("Japan", "jp", R.drawable.japan));
        countries.add(new Country("USA", "us", R.drawable.united_states_of_america));

        final CountryAdapter countryAdapter = new CountryAdapter(this, R.layout.country_item_view, countries);

        country = "us";

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_countries);

        ListView countriesListView = dialog.findViewById(R.id.lv_countires);
        countriesListView.setAdapter(countryAdapter);

        countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                country = countries.get(position).getCode();
                Helper.displayLog("Home", "country code:" + country);
                sharedPreference.setCountry(country);
                dialog.dismiss();
            }
        });

        Button skipButton = dialog.findViewById(R.id.btn_skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
