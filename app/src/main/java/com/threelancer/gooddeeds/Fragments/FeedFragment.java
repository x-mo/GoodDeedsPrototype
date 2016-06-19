package com.threelancer.gooddeeds.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.threelancer.gooddeeds.Activities.DetailsActivity;
import com.threelancer.gooddeeds.R;
import com.threelancer.gooddeeds.RequestsAdapter;
import com.threelancer.gooddeeds.model.Request;
import com.threelancer.gooddeeds.util.Constants;
import com.firebase.client.Firebase;

public class FeedFragment extends Fragment {

    private RequestsAdapter mRequestsAdapter;
    private ListView mFeedList;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_feed, container, false);


        Firebase requestsRef = new Firebase(Constants.FIREBASE_URL_REQUESTS);

        mRequestsAdapter = new RequestsAdapter(getActivity(), Request.class, R.layout.feed_item, requestsRef);

        mFeedList = (ListView) root.findViewById(R.id.feed_list);
        mFeedList.setAdapter(mRequestsAdapter);

        mFeedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request selectedRequest = mRequestsAdapter.getItem(position);
                if(selectedRequest != null){
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    String requestId = mRequestsAdapter.getRef(position).getKey();
                    String requestorEmail = mRequestsAdapter.getItem(position).getRequestorEmail();

                    intent.putExtra(Constants.KEY_LIST_ID, requestId);
                    intent.putExtra(Constants.KEY_EMAIL, requestorEmail);
                    Log.v("myTest","Req ID " + requestId);
                    startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRequestsAdapter.cleanup();
    }
}
