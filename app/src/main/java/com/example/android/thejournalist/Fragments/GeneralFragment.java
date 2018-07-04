package com.example.android.thejournalist.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.thejournalist.R;
import com.example.android.thejournalist.Utilites.RequestNews;

public class GeneralFragment extends Fragment {
    private static final String LOG_TAG = GeneralFragment.class.getSimpleName();
    String responseString = "";
    private Uri uri;


    public GeneralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Helper.displayLog(LOG_TAG,news.get(1).getId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        ListView listView = rootView.findViewById(R.id.lv_general);
        RequestNews.sendRequest(getContext(), LOG_TAG, "general", listView);
        return rootView;
    }

}
