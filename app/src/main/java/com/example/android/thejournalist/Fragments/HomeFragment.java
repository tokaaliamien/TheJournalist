package com.example.android.thejournalist.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.thejournalist.Activities.DetailsActivity;
import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Constants;
import com.example.android.thejournalist.Utilites.Helper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-04.
 */

public class HomeFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    RequestQueue requestQueue;
    private ListView listView;
    private TextView noNewsTextView;
    private ArrayList<News> newsArrayList = new ArrayList<>();
    private ProgressDialog pd;


    public HomeFragment() {
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

        //sendRequest("general");

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                //general
                sendRequest("general");
                break;
            }
            case 2: {
                //Tech
                sendRequest("technology");
                break;
            }
            case 3: {
                //science
                sendRequest("science");
                break;
            }
            case 4: {
                //sports
                sendRequest("sports");
                break;
            }
        }
        /*if (newsArrayList != null && newsArrayList.size() > 0)
            setNews(newsArrayList);*/

        return rootView;
    }

    public void sendRequest(String category) {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading " + category);
        pd.show();
        newsArrayList.clear();

        requestQueue = Volley.newRequestQueue(getContext());

        Uri uri = Uri.parse(Constants.TOP_HEADLINES_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.CATEGORY_PARAM, category)
                .appendQueryParameter(Constants.COUNTRY_PARAM, "us")
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();

        Helper.displayLog(LOG_TAG, "URL: " + uri);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Helper.displayLog(LOG_TAG, "worked");
                        JSONObject jObject = null;
                        try {
                            pd.hide();
                            jObject = new JSONObject(response);
                            JSONArray jsonarray = new JSONArray(jObject.getString("articles"));
                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);

                                JsonParser parser = new JsonParser();
                                JsonElement jsonElement = parser.parse(jsonobject.toString());
                                Gson gson = new Gson();
                                News news = gson.fromJson(jsonElement, News.class);
                                newsArrayList.add(news);
                                Helper.displayLog(LOG_TAG, newsArrayList.get(i).getSource().getName());
                            }

                            Helper.displayLog(LOG_TAG, "size= " + newsArrayList.size());
                            if (newsArrayList != null && newsArrayList.size() > 0) {
                                noNewsTextView.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                                setNews(newsArrayList);
                            } else {
                                noNewsTextView.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            pd.hide();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.displayLog(LOG_TAG, "VolleyError" + error);
                    }
                }
        );
        requestQueue.add(stringRequest);

    }

    private void setNews(final ArrayList<News> newsArrayList) {
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.item_view, newsArrayList);
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
        pd.dismiss();
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
