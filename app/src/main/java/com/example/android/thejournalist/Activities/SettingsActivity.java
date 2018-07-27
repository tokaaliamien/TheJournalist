package com.example.android.thejournalist.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.thejournalist.Adapters.CountryAdapter;
import com.example.android.thejournalist.Models.Country;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Constants;
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.NavDrawer;
import com.example.android.thejournalist.Utilites.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();
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

        navDrawer = new NavDrawer(this, navigationView);


        LinearLayout changeCountryLayout = findViewById(R.id.layout_settings_change_country);
        changeCountryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryDialog();
            }
        });

        LinearLayout changeNameLayout = findViewById(R.id.layout_settings_change_name);
        changeNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNameDialog();

            }
        });
        LinearLayout changeEmailLayout = findViewById(R.id.layout_settings_change_email);
    }

    private void showNameDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edittext);
        dialog.setCancelable(true);
        TextView labelTextView = (dialog.findViewById(R.id.tv_label));
        labelTextView.setText("Your name");

        final EditText nameEditText = (dialog.findViewById(R.id.et));
        nameEditText.setText(Constants.currentUser.getDisplayName());

        Button saveButton, cancelButton;
        saveButton = dialog.findViewById(R.id.btn_save);
        cancelButton = dialog.findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nameEditText.getText().toString().trim())
                        .build();
                Constants.currentUser.updateProfile(profileChangeRequest)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Helper.displayToast(SettingsActivity.this, "Name updated");
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }

    private void showCountryDialog() {
        final ArrayList<Country> countries = Country.getCountriesList();

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
