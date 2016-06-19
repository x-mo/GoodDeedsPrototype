package com.threelancer.gooddeeds;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.threelancer.gooddeeds.model.Applicant;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

public class ApplicationAdapter extends FirebaseListAdapter<Applicant> {
    public ApplicationAdapter(Activity activity, Class<Applicant> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View v, Applicant applicant) {
        TextView requestorName = (TextView) v.findViewById(R.id.requestor_name);
        TextView need_service = (TextView) v.findViewById(R.id.request_zatona);
        TextView timeCreated = (TextView) v.findViewById(R.id.time_text);

        requestorName.setText(applicant.getName());
        need_service.setText(applicant.getPrposal());
        timeCreated.setText("");
    }
}
