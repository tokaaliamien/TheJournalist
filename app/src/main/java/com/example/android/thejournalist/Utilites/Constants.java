package com.example.android.thejournalist.Utilites;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Toka on 2018-04-13.
 */

public class Constants {
    public static final String API_KEY = "98fd937903fd4853846b9f026adffc6b";
    public static final String SOURCES_BASE_URL = "https://newsapi.org/v2/sources";
    public static final String TOP_HEADLINES_BASE_URL = "https://newsapi.org/v2/top-headlines";
    public static final String EVERYTHING_BASE_URL = "https://newsapi.org/v2/everything";


    public static final String COUNTRY_PARAM = "country";
    public static final String API_KEY_PARAM = "apikey";
    public static final String CATEGORY_PARAM = "category";
    public static final String SOURCE_PARAM = "sources";
    public static final String QUERY_PARAM = "q";

    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference favoritesDatabaseReference = database.getReference("favorites");
    public static final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();




}
