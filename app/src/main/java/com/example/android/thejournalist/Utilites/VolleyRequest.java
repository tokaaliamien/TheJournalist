package com.example.android.thejournalist.Utilites;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.thejournalist.Models.News;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-18.
 */
public class VolleyRequest {
    private static String LOG_TAG;
    Context context;
    RequestQueue requestQueue;
    private ArrayList<News> newsArrayList;


    public VolleyRequest(Context context) {
        this.context = context;
        LOG_TAG = ((Activity) context).getClass().getSimpleName();
        newsArrayList = new ArrayList<>();
    }

    public void requestData(final Uri uri, final CallBack callBack) {
        newsArrayList.clear();

        requestQueue = Volley.newRequestQueue(context);

        Helper.displayLog(((Activity) context).getClass().getSimpleName(), "URL: " + uri);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Helper.displayLog(LOG_TAG, "worked");
                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                            JSONArray jsonarray = new JSONArray(jObject.getString("articles"));
                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                JsonParser parser = new JsonParser();
                                JsonElement jsonElement = parser.parse(jsonobject.toString());
                                Gson gson = new Gson();
                                News news = gson.fromJson(jsonElement, News.class);
                                newsArrayList.add(news);
                            }

                            Helper.displayLog(LOG_TAG, "size= " + newsArrayList.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Helper.displayLog(LOG_TAG, "Jsonexception" + e);
                        }
                        callBack.onCallback(newsArrayList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Helper.displayLog(LOG_TAG, "VolleyError" + error);
                        if (error == null) {
                            return;
                        } else {
                            String errorMessage = "";
                            if (error instanceof NoConnectionError)
                                if (error instanceof NetworkError) {
                                } else if (error instanceof ClientError) {
                                    errorMessage = "Client error";
                                } else if (error instanceof ServerError) {
                                    errorMessage = "Server error";
                                } else if (error instanceof AuthFailureError) {
                                    errorMessage = "Auth failure";
                                } else if (error instanceof ParseError) {
                                    errorMessage = "Parse error";
                                } else if (error instanceof NoConnectionError) {
                                    errorMessage = "No internet connection";
                                } else if (error instanceof TimeoutError)
                                    errorMessage = "Request timeout error";

                            Snackbar snackbar = Snackbar.make(((Activity) context).getWindow().getDecorView().getRootView()
                                    , errorMessage
                                    , Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            requestData(uri, callBack);
                                        }
                                    });
                            snackbar.show();
                        }
                    }
                }

        );
        requestQueue.add(stringRequest);

    }

    public void cancelAll() {
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }


}
