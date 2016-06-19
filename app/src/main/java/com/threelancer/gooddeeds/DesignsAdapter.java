package com.threelancer.gooddeeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class DesignsAdapter extends ArrayAdapter<Integer>{
    public DesignsAdapter(Context context, List<Integer> urls) {
        super(context, 0, urls);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int i = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.desgin_image);
        img.setImageResource(i);

        return convertView;
    }
}
