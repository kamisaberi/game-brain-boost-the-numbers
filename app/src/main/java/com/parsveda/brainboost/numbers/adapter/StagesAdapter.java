package com.parsveda.brainboost.numbers.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parsveda.brainboost.numbers.base.Globals;
import com.parsveda.brainboost.numbers.model.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kami on 12/20/2016.
 */
public class StagesAdapter extends ArrayAdapter<Stage> {

    private Context context;
    private int layoutResourceId;
    private List<Stage> stages = new ArrayList<>();

    public StagesAdapter(Context context, int layoutResourceId, ArrayList<Stage> stages) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.stages = stages;
        Log.d(Globals.LOG_TAG, "SIZE :" + stages.size());

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            //holder.title = (TextView) row.findViewById(R.id.txtStageTitle);
            //holder.rating = (RatingBar) row.findViewById(R.id.ratingStage);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        Stage item = stages.get(position);
        holder.title.setText(item.getTitle());
        holder.rating.setRating(item.getRating());
        return row;


    }

    static class ViewHolder {
        TextView title;
        RatingBar rating;
    }


}
