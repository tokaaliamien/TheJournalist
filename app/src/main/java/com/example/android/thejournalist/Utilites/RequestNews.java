package com.example.android.thejournalist.Utilites;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.thejournalist.Adapters.NewsAdapter;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-02.
 */

public class RequestNews {
    private static ProgressDialog pd;
    private static Uri uri;
    private static String responseString;

    public static void sendRequest(Context context, final String LOG_TAG, String category, ListView listView) {
        pd = new ProgressDialog(context);
        pd.setMessage("Loading . . . ");
        pd.show();

        final ArrayList<News> newsArrayList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.CATEGORY_PARAM, category)
                .appendQueryParameter(Constants.COUNTRY_PARAM, "us")
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
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
                            JSONArray jsonarray = new JSONArray(jObject.getString("sources"));
                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String description = jsonobject.getString("description");
                                String name = jsonobject.getString("name");

                                JsonParser parser = new JsonParser();
                                JsonElement jsonElement = parser.parse(jsonobject.toString());
                                Gson gson = new Gson();
                                News news = gson.fromJson(jsonElement, News.class);
                                newsArrayList.add(news);
                                Helper.displayLog(LOG_TAG, newsArrayList.get(i).getId());
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
                        /*if (error != null) {
                        }*/
                        Helper.displayLog(LOG_TAG, "Error");
                    }
                }

        );


        requestQueue.add(stringRequest);
        setNews(context, newsArrayList, listView);
        //return responseString;

    }

    private static void setNews(Context context, ArrayList<News> newsArrayList, ListView listView) {
        NewsAdapter newsAdapter = new NewsAdapter(context, R.layout.item_view, newsArrayList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
