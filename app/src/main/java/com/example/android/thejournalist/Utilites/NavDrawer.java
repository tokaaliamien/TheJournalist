package com.example.android.thejournalist.Utilites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;

import com.example.android.thejournalist.Activities.FavortiesActivity;
import com.example.android.thejournalist.Activities.HomeActivity;
import com.example.android.thejournalist.Activities.LoginActivity;
import com.example.android.thejournalist.Activities.SettingsActivity;
import com.example.android.thejournalist.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Toka on 2018-07-14.
 */
public class NavDrawer {
    Context context;
    NavigationView navigationView;

    public NavDrawer(Context context, NavigationView navigationView) {
        this.context = context;
        this.navigationView = navigationView;
        setUserData();
    }

    public void onNavItemClick(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                if (context instanceof HomeActivity)
                    return;
                context.startActivity(new Intent(context, HomeActivity.class));
                ((Activity) context).finish();
                break;
            case R.id.nav_fav:
                if (context instanceof FavortiesActivity)
                    return;
                context.startActivity(new Intent(context, FavortiesActivity.class));
                ((Activity) context).finish();
                break;
            case R.id.nav_sign_out:
                signOut();
                ((Activity) context).finish();
                break;
            case R.id.nav_settings:
                if (context instanceof SettingsActivity)
                    return;
                context.startActivity(new Intent(context, SettingsActivity.class));
                ((Activity) context).finish();
                break;
        }
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
    }

    private void setUserData() {
        View navView = navigationView.getHeaderView(0);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView nameTextView = navView.findViewById(R.id.tv_nav_header_name);
        nameTextView.setText(currentUser.getDisplayName());
        TextView emailTextView = navView.findViewById(R.id.tv_nav_header_email);
        emailTextView.setText(currentUser.getEmail());
    }


}
