package com.example.android.thejournalist.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.thejournalist.Activities.DetailsActivity;
import com.example.android.thejournalist.Adapters.CountryAdapter;
import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.Country;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.CallBack;
import com.example.android.thejournalist.Utilites.Constants;
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.SharedPreference;
import com.example.android.thejournalist.Utilites.VolleyRequest;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-04.
 */

public class HomeFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private ListView listView;
    private TextView noNewsTextView;
    private ArrayList<News> newsArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private String country;
    private SharedPreference sharedPreference;
    private VolleyRequest volleyRequest;


    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        listView = rootView.findViewById(R.id.lv_news);
        noNewsTextView = rootView.findViewById(R.id.tv_no_news);
        progressBar = rootView.findViewById(R.id.pb_list);
        progressBar.setVisibility(View.VISIBLE);

        sharedPreference = new SharedPreference(getContext());

        if (sharedPreference.getCountry() != null) {
            country = sharedPreference.getCountry();
            sendRequest(country);
        } else {
            showCountryDialog();
        }

        return rootView;
    }

    private void sendRequest(String countryCode) {
        String category = "general";
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                //general
                category = "general";
                break;
            }
            case 2: {
                //Tech
                category = "technology";
                break;
            }
            case 3: {
                //science
                category = "science";
                break;
            }
            case 4: {
                //sports
                category = "sports";
                break;
            }
        }

        Uri uri = Uri.parse(Constants.TOP_HEADLINES_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.CATEGORY_PARAM, category)
                .appendQueryParameter(Constants.COUNTRY_PARAM, countryCode)
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();

        volleyRequest = new VolleyRequest(getContext());
        volleyRequest.requestData(uri, new CallBack() {
            @Override
            public void onCallback(ArrayList<News> resultArrayList) {
                newsArrayList = resultArrayList;
                if (newsArrayList != null && newsArrayList.size() > 0) {
                    noNewsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    setNews(newsArrayList);
                } else {
                    noNewsTextView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setNews(final ArrayList<News> newsArrayList) {
        progressBar.setVisibility(View.GONE);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_view, newsArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*DetailsFragment detailsFragment=new DetailsFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.view_pager, detailsFragment);
                transaction.commit();*/
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("news", newsArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressBar.setVisibility(View.GONE);
        volleyRequest.cancelAll();
    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            sendRequest(country);
            Helper.displayLog(LOG_TAG, "refresh");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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

        final CountryAdapter countryAdapter = new CountryAdapter(getContext(), R.layout.country_item_view, countries);

        country = "us";

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_countries);

        ListView countriesListView = dialog.findViewById(R.id.lv_countires);
        countriesListView.setAdapter(countryAdapter);

        countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                country = countries.get(position).getCode();
                Helper.displayLog("Home", "country code:" + country);
                sendRequest(country);
                sharedPreference.setCountry(country);
                dialog.dismiss();
            }
        });

        Button skipButton = dialog.findViewById(R.id.btn_skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(country);
                dialog.dismiss();
            }
        });


        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sendRequest(country);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
