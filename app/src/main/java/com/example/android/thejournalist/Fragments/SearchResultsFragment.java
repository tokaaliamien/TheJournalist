package com.example.android.thejournalist.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.thejournalist.Activities.DetailsActivity;
import com.example.android.thejournalist.Activities.SearchResultsActivity;
import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.CallBack;
import com.example.android.thejournalist.Utilites.Constants;
import com.example.android.thejournalist.Utilites.NavDrawer;
import com.example.android.thejournalist.Utilites.VolleyRequest;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-27.
 */
public class SearchResultsFragment extends Fragment {

    private static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();
    private VolleyRequest volleyRequest;
    private ArrayList<News> newsArrayList;
    private ListView listView;
    private TextView noNewsTextView;
    private ProgressBar progressBar;
    private NavDrawer navDrawer;
    private String query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        listView = rootView.findViewById(R.id.lv_news);
        noNewsTextView = rootView.findViewById(R.id.tv_no_news);
        progressBar = rootView.findViewById(R.id.pb_list);
        progressBar.setVisibility(View.GONE);

        search(this.getArguments().getString("query"));

        return rootView;
    }

    private void search(final String query) {
        progressBar.setVisibility(View.VISIBLE);
        volleyRequest = new VolleyRequest(getContext());
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
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.news_item_view, newsArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().findViewById(R.id.fragment_details) != null) {
                    getActivity().findViewById(R.id.fragment_details).setVisibility(View.VISIBLE);
                    DetailsFragment detailFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", newsArrayList.get(position));
                    detailFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_details, detailFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("news", newsArrayList.get(position));
                    startActivity(intent);
                }
            }
        });
    }

}
