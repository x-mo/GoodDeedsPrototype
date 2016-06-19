package com.threelancer.gooddeeds;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.threelancer.gooddeeds.model.StartupReview;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartupReviewsAdapter extends ArrayAdapter<StartupReview> {
    public StartupReviewsAdapter(Context context, List<StartupReview> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StartupReview sr = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        de.hdodenhof.circleimageview.CircleImageView img = (CircleImageView) convertView.findViewById(R.id.img);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView review = (TextView) convertView.findViewById(R.id.review);

        img.setImageResource(sr.getImg());
        name.setText(sr.getName());
        review.setText(sr.getReview());

        return convertView;
    }
}
