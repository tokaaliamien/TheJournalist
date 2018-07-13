package com.example.android.thejournalist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.thejournalist.Activities.WebPageActivity;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Firebase;
import com.example.android.thejournalist.Utilites.Helper;
import com.example.android.thejournalist.Utilites.SharedPreference;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.android.thejournalist.Utilites.Helper.isNullOrEmpty;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {
    TextView descriptionTextView, titleTextView, authorTextView, nameTextView,
            dateTextView, fromTextView, byTextView, publishedAtTextView;
    FloatingActionButton linkFloatingButton, favFloatingButton;
    ImageView imageView;
    SharedPreference sharedPreference;
    Firebase firebase;
    boolean isFavorite;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        News news = (News) getActivity().getIntent().getExtras().get("news");

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        sharedPreference = new SharedPreference();
        firebase = new Firebase();
        isFavorite = sharedPreference.isFavorite(getContext(), news);

        descriptionTextView = view.findViewById(R.id.tv_news_details_description);
        titleTextView = view.findViewById(R.id.tv_news_details_title);
        dateTextView = view.findViewById(R.id.tv_news_details_date);
        nameTextView = view.findViewById(R.id.tv_news_details_name);
        authorTextView = view.findViewById(R.id.tv_news_details_author);
        fromTextView = view.findViewById(R.id.tv_from);
        byTextView = view.findViewById(R.id.tv_by);
        publishedAtTextView = view.findViewById(R.id.tv_published_at);


        imageView = view.findViewById(R.id.iv_news_details_image);

        linkFloatingButton = view.findViewById(R.id.fab_news_details_link);
        favFloatingButton = view.findViewById(R.id.fab_news_details_fav);

        setNewsData(news);

        return view;
    }

    private void setNewsData(final News news) {
        if (isNullOrEmpty(news.getSource().getName())) {
            nameTextView.setVisibility(View.GONE);
            fromTextView.setVisibility(View.GONE);
        } else {
            nameTextView.setVisibility(View.VISIBLE);
            fromTextView.setVisibility(View.VISIBLE);
            nameTextView.setText(news.getSource().getName());
        }

        if (isNullOrEmpty(news.getDescription())) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(news.getDescription());
        }

        if (isNullOrEmpty(news.getTitle())) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(news.getTitle());
        }
        if (isNullOrEmpty(news.getAuthor())) {
            authorTextView.setVisibility(View.GONE);
            byTextView.setVisibility(View.GONE);
        } else {
            authorTextView.setVisibility(View.VISIBLE);
            byTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(news.getAuthor());
        }
        if (isNullOrEmpty(news.getPublishedAt())) {
            dateTextView.setVisibility(View.GONE);
            publishedAtTextView.setVisibility(View.GONE);
        } else {
            dateTextView.setVisibility(View.VISIBLE);
            publishedAtTextView.setVisibility(View.VISIBLE);
            dateTextView.setText(news.getPublishedAt()); //TODO format date
        }

        if (isNullOrEmpty(news.getUrlToImage())) {
            imageView.setVisibility(View.GONE);
        } else
            Glide.with(this).load(news.getUrlToImage()).into(imageView);

        setFavFloatingButtonIcon();

        if (isNullOrEmpty(news.getUrl())) {
            linkFloatingButton.setVisibility(View.GONE);
        } else {
            linkFloatingButton.setVisibility(View.VISIBLE);
            linkFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*Fragment webViewFragment = new WebPageFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, webViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/

                    Intent intent = new Intent(getActivity(), WebPageActivity.class);
                    intent.putExtra("news", news);
                    startActivity(intent);
                }
            });
        }
        favFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    sharedPreference.removeFavorite(getContext(), news);
                    firebase.removeFavorite(FirebaseAuth.getInstance().getCurrentUser(), news);
                    isFavorite = false;
                    setFavFloatingButtonIcon();
                    Helper.displayToast(getActivity(), "Removed from favorites");

                } else {
                    String newsId = firebase.addFavorite(FirebaseAuth.getInstance().getCurrentUser(), news);
                    news.setId(newsId);
                    sharedPreference.addFavorite(getContext(), news);
                    isFavorite = true;
                    setFavFloatingButtonIcon();
                    Helper.displayToast(getActivity(), "Added to favorites");
                }
            }
        });


    }

    private void setFavFloatingButtonIcon() {
        if (isFavorite)
            favFloatingButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_action_fav));
        else
            favFloatingButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_action_fav_border));
    }


}
