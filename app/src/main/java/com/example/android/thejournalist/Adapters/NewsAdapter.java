package com.example.android.thejournalist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;

import java.util.ArrayList;

/**
 * Created by Toka on 2018-07-02.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private final Context context;
    private final ArrayList<News> newsArrayList;

    public NewsAdapter(@NonNull Context context, int resource, ArrayList<News> newsArrayList) {
        super(context, -1, newsArrayList);
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        TextView nameTextView = itemView.findViewById(R.id.tv_item_name);
        TextView titleTextView = itemView.findViewById(R.id.tv_item_title);

        nameTextView.setText(newsArrayList.get(position).getSource().getName());
        titleTextView.setText(newsArrayList.get(position).getTitle());
        return itemView;
    }
}
