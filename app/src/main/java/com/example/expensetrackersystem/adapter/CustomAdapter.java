package com.example.expensetrackersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.tipItems;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<tipItems> mtipItems;

    public CustomAdapter(Context context, List<tipItems> tipItems) {
        mContext = context;
        mtipItems = tipItems;
    }

    @Override
    public int getCount() {
        return mtipItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mtipItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            gridItemView = inflater.inflate(R.layout.activity_description, parent, false);
        }

        ImageView imageView = gridItemView.findViewById(R.id.desImageViewId);
        TextView textView = gridItemView.findViewById(R.id.desTextViewId);

        tipItems currentItem = mtipItems.get(position);

        imageView.setImageResource(currentItem.getImageResource());
        textView.setText(currentItem.getUrl());

        return gridItemView;
    }
}
