package com.parsveda.brainboost.numbers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parsveda.brainboost.numbers.R;
import com.parsveda.brainboost.numbers.model.StageItem;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<StageItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<StageItem> data = new ArrayList<StageItem>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<StageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        StageItem item = data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}