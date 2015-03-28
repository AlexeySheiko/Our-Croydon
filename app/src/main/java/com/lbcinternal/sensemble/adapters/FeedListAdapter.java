package com.lbcinternal.sensemble.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lbcinternal.sensemble.R;
import com.lbcinternal.sensemble.rest.model.FeedEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FeedListAdapter extends ArrayAdapter<FeedEntry> {

    List<FeedEntry> mEntries;

    public FeedListAdapter(Context context, List<FeedEntry> entries) {
        super(context, 0, entries);
        mEntries = entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.feed_entry,
                parent, false);

        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        titleTextView.setText(getItem(position).getTitle());

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        Date date = getItem(position).getCreationDate();
        DateFormat format = new SimpleDateFormat("F MMM");
        dateTextView.setText(format.format(date));

        return view;
    }
}
