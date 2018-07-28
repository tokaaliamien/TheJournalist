package com.example.android.thejournalist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.thejournalist.Activities.DetailsActivity;
import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.CallBack;
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.SharedPreference;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-27.
 */
public class FavortiesFragment extends Fragment {

    SharedPreference sharedPreference;
    TextView noNewsTextView;
    NewsAdapter newsAdapter;
    FloatingActionButton deleteFavFloatingButton;
    ProgressBar progressBar;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        listView = rootView.findViewById(R.id.lv_news);
        noNewsTextView = rootView.findViewById(R.id.tv_no_news);
        deleteFavFloatingButton = getActivity().findViewById(R.id.fab_delete_all_fav);
        progressBar = rootView.findViewById(R.id.pb_list);

        progressBar.setVisibility(View.VISIBLE);
        noNewsTextView.setText("You don't have favorite news yet");

        getFavoriteNews();

        deleteFavFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.removeAll();
                Helper.displayToast(getActivity(), "Favorites deleted");
                getFavoriteNews();
            }
        });

        return rootView;
    }

    private void getFavoriteNews() {
        sharedPreference = new SharedPreference(getContext());
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
        newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_view, favoritesArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().findViewById(R.id.fragment_details) != null) {
                    getActivity().findViewById(R.id.fragment_details).setVisibility(View.VISIBLE);
                    DetailsFragment detailFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", favoritesArrayList.get(position));
                    detailFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_details, detailFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("news", favoritesArrayList.get(position));
                    startActivity(intent);
                }
            }
        });
    }

}
