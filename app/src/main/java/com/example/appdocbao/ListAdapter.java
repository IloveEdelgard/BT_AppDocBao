package com.example.appdocbao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<RSSItem> {
    private Context context;
    private ArrayList<RSSItem> rssItems;

    public ListAdapter(Context context, ArrayList<RSSItem> rssItems) {
        super(context, R.layout.news_item, rssItems);
        this.context = context;
        this.rssItems = rssItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        RSSItem rssItem = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.news_pic);
        Picasso.get().load(rssItem.getImageUrl()).into(imageView);

        TextView titleTextView = convertView.findViewById(R.id.txt_title);
        titleTextView.setText(rssItem.getTitle());

        TextView descriptionTextView = convertView.findViewById(R.id.txt_description);
        descriptionTextView.setText(rssItem.getDescription());

        TextView pubDateTextView = convertView.findViewById(R.id.txt_pubDate);
        pubDateTextView.setText(rssItem.getPubDate());

        return convertView;
    }
}
