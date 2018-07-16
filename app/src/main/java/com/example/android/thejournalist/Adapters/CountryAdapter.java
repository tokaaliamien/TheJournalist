package com.example.android.thejournalist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thejournalist.Models.Country;
import com.example.android.thejournalist.R;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-02.
 */

public class CountryAdapter extends ArrayAdapter<Country> {
    private final Context context;
    private final ArrayList<Country> countriesArrayList;

    public CountryAdapter(@NonNull Context context, int resource, ArrayList<Country> countriesArrayList) {
        super(context, -1, countriesArrayList);
        this.context = context;
        this.countriesArrayList = countriesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.country_item_view, parent, false);
        TextView nameTextView = itemView.findViewById(R.id.tv_country_item_view);
        ImageView flagImageView = itemView.findViewById(R.id.iv_country_item_view);

        nameTextView.setText(countriesArrayList.get(position).getName());
        flagImageView.setImageResource(countriesArrayList.get(position).getFlag());
        return itemView;
    }
}
