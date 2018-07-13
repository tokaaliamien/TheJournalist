package com.example.android.thejournalist.Utilites;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.thejournalist.Models.News;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Toka on 2018-07-08.
 */

public class SharedPreference {

    private static final String PREFERENCE_NAME = "THE_JOURNALIST";
    private static final String FAVORITES = "FAVORITE_NEWS";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, ArrayList<News> favNewsArrayList) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(favNewsArrayList);
        editor.putString(FAVORITES, json);
        editor.commit();
    }

    public boolean addFavorite(Context context, News news) {
        ArrayList<News> favoritesArrayList = getFavorites(context);
        if (isFavorite(context, news))
            return false;
        if (favoritesArrayList == null)
            favoritesArrayList = new ArrayList<>();

        favoritesArrayList.add(news);
        saveFavorites(context, favoritesArrayList);
        return true;
    }

    public boolean removeFavorite(Context context, News news) {
        ArrayList<News> favoritesArrayList = getFavorites(context);

        if (favoritesArrayList != null) {
            favoritesArrayList.remove(news);
            saveFavorites(context, favoritesArrayList);
            return true;
        }
        return false;
    }

    public boolean isFavorite(Context context, News news) {
        ArrayList<News> favoritesArrayList = getFavorites(context);
        if (favoritesArrayList != null) {
            for (News i : favoritesArrayList) {
                if (i.equals(news))
                    return true;
            }
        }
        return false;
    }

    public ArrayList<News> getFavorites(Context context) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(FAVORITES)) {
            String json = sharedPreferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            News[] newsArray = gson.fromJson(json, News[].class);
            ArrayList<News> favoritesArrayList = new ArrayList<News>(Arrays.asList(newsArray));
            Helper.displayLog("s", "shared pref:" + favoritesArrayList.size());
            return favoritesArrayList;
        } else
            return null;
    }

    public boolean removeAll(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.remove(FAVORITES).commit();
    }

}
