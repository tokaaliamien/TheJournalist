package com.example.android.thejournalist.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.Helper;

public class WebPageFragment extends android.support.v4.app.Fragment {
    private final String LOG_TAG = WebPageFragment.class.getSimpleName();

    public WebPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_page, container, false);

        News news = (News) getActivity().getIntent().getExtras().get("news");

        final ProgressBar progressBar = view.findViewById(R.id.pb_web_view);
        Helper.displayLog(LOG_TAG, "Link: " + news.getUrl());

        WebView webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(news.getUrl());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                view.findViewById(R.id.pb_web_view);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }

}
