package com.threelancer.gooddeeds;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.threelancer.gooddeeds.model.Request;
import com.threelancer.gooddeeds.util.Utilities;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

public class RequestsAdapter extends FirebaseListAdapter<Request> {
    public RequestsAdapter(Activity activity, Class<Request> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View v, Request request) {

        TextView requestorName = (TextView) v.findViewById(R.id.requestor_name);
        TextView need_service = (TextView) v.findViewById(R.id.request_zatona);
        TextView timeCreated = (TextView) v.findViewById(R.id.time_text);


        requestorName.setText(request.getRequestorName());
        need_service.setText(request.getNeed() + " - for -> " + request.getOffer());
        timeCreated.setText(Utilities.SIMPLE_DATE_FORMAT.format(request.getTimestampCreatedLong()));

        // use Picasso to populate pp
    }
}
