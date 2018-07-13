package com.example.android.thejournalist.Utilites;

import com.example.android.thejournalist.Models.News;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Toka on 2018-07-12.
 */
public class Firebase {
    public String addFavorite(FirebaseUser user, News news) {
        DatabaseReference newFavoriteNewsRef = Constants.favoritesDatabaseReference.child(user.getUid()).push();
        news.setId(newFavoriteNewsRef.getKey());
        newFavoriteNewsRef.setValue(news);
        return newFavoriteNewsRef.getKey();
    }

    public void removeFavorite(FirebaseUser user, News news) {
        Constants.favoritesDatabaseReference.child(user.getUid()).child(news.getId()).removeValue();
    }

}
