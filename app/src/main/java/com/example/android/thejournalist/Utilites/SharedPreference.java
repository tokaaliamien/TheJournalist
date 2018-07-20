package com.example.android.thejournalist.Utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.thejournalist.Models.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Toka on 2018-07-08.
 */

public class SharedPreference {
    private static final String COUNTRY = "COUNTRY";
    private static final String PREFERENCE_NAME = "THE_JOURNALIST";
    private static final String FAVORITES = "FAVORITE_NEWS";
    private Context context;
    private boolean removedFromFirebase = false;
    private ArrayList<News> favoritesArrayList;


    public SharedPreference(Context context) {
        super();
        this.context = context;
    }

    public void saveFavorites(ArrayList<News> favNewsArrayList) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(favNewsArrayList);
        editor.putString(FAVORITES, json);
        editor.commit();
    }

    public boolean addFavorite(News news) {
        ArrayList<News> favoritesArrayList = getFavorites();
        if (isFavorite(news))
            return false;
        if (favoritesArrayList == null)
            favoritesArrayList = new ArrayList<>();

        favoritesArrayList.add(news);
        saveFavorites(favoritesArrayList);
        return true;
    }

    public boolean removeFavorite(News news) {
        ArrayList<News> favoritesArrayList = getFavorites();
        boolean removeFromSharedPreferences = false;
        if (favoritesArrayList != null) {
            favoritesArrayList.remove(news);
            saveFavorites(favoritesArrayList);
            removeFromSharedPreferences = true;
        }
        //remove from firebase
        removedFromFirebase = false;
        Constants.favoritesDatabaseReference.child(Constants.currentUser.getUid()).child(news.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        removedFromFirebase = true;
                    }
                });
        return removeFromSharedPreferences && removedFromFirebase;
    }

    //TODO make it a map to reduce time complexity
    public boolean isFavorite(News news) {
        ArrayList<News> favoritesArrayList = getFavorites();
        if (favoritesArrayList != null) {
            for (News i : favoritesArrayList) {
                if (i.equals(news))
                    return true;
            }
        }
        return false;
    }

    public ArrayList<News> getFavorites() {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        favoritesArrayList = new ArrayList<>();

        if (sharedPreferences.contains(FAVORITES)) {
            String json = sharedPreferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            News[] newsArray = gson.fromJson(json, News[].class);
            favoritesArrayList = new ArrayList<News>(Arrays.asList(newsArray));
            Helper.displayLog("s", "shared pref:" + favoritesArrayList.size());
        }
        return favoritesArrayList;
    }

    public void getNewsFromFirebase(final CallBack callBack) {
        Constants.favoritesDatabaseReference.child(Constants.currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        favoritesArrayList.add(ds.getValue(News.class));
                    }
                    callBack.onCallback(favoritesArrayList);
                    Helper.displayLog(context.getClass().getSimpleName(), "SP class " + "list size: " + favoritesArrayList.size());
                } else {
                    favoritesArrayList = null;
                    callBack.onCallback(favoritesArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean removeAll() {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean removedFromSharedPreferences = editor.remove(FAVORITES).commit();

        //remove from firebase
        removedFromFirebase = false;
        Constants.favoritesDatabaseReference.child(Constants.currentUser.getUid())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        removedFromFirebase = true;
                    }
                });
        return removedFromSharedPreferences && removedFromFirebase;
    }

    public String getCountry() {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(COUNTRY)) {
            String country = sharedPreferences.getString(COUNTRY, null);
            return country;
        } else
            return null;
    }

    public void setCountry(String country) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString(COUNTRY, country);
        editor.commit();
    }


}
