package com.lbcinternal.sensemble.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lbcinternal.sensemble.R;
import com.lbcinternal.sensemble.rest.model.NewsEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsEntry> {

    List<NewsEntry> mEntries;

    public NewsAdapter(Context context, List<NewsEntry> entries) {
        super(context, 0, entries);
        mEntries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.feed_list_item,
                parent, false);

        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        titleTextView.setText(getItem(position).getTitle());

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        Date date = getItem(position).getCreationDate();
        DateFormat format = new SimpleDateFormat("d MMM");
        String formattedDate = format.format(date);
        dateTextView.setText(formattedDate);

        return view;
    }
}
