package com.example.android.thejournalist.Utilites;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Toka on 2018-04-13.
 */

public class Connector {
    private String responseString = "";
    private Uri uri;

    Connector() {
        uri = Uri.parse(Constants.SOURCES_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Constants.COUNTRY_PARAM, "us")
                .appendQueryParameter(Constants.API_KEY_PARAM, Constants.API_KEY)
                .build();
        Log.e("", uri.toString());

    }

    public String sendRequest(Context context) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseString = response.toString();
                Log.e("", "1");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseString = error.toString();
                Log.e("", "2");

            }
        }
        );
        Volley.newRequestQueue(context).add(jsonObjectRequest);

        return responseString;
    }

}
