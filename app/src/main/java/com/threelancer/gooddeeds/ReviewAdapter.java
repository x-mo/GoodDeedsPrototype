package com.threelancer.gooddeeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.threelancer.gooddeeds.model.Review;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends ArrayAdapter<Review>{

    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review r = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        de.hdodenhof.circleimageview.CircleImageView img = (CircleImageView) convertView.findViewById(R.id.img);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView review = (TextView) convertView.findViewById(R.id.review);

        img.setImageResource(r.getImg());
        name.setText(r.getName());
        review.setText(r.getReview());

        return convertView;
    }
}
