package com.threelancer.gooddeeds.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.threelancer.gooddeeds.R;
import com.threelancer.gooddeeds.StartupReviewsAdapter;
import com.threelancer.gooddeeds.model.StartupReview;

import java.util.ArrayList;
import java.util.Arrays;

public class StartupProfileActivity extends AppCompatActivity {

    StartupReviewsAdapter mAdapter;
    StartupReview[] startupReview;
    ListView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_profile);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Startup Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StartupReview startupReview1 = new StartupReview(R.drawable.cahr1 ,"Good Dude", "I enjoyed your services, please do it again ;)");
        StartupReview startupReview2 = new StartupReview(R.drawable.dontfuckwithme ,"Bad Guy", "Do I look like I'm kidding?");
        StartupReview startupReview3 = new StartupReview(R.drawable.cowboy ,"Roly Rays", "Lovely old fashioned, I liked! :)");
        StartupReview startupReview4 = new StartupReview(R.drawable.batman ,"Batman", "I saw my sign on sky 1000 times! where is my services!");

        startupReview = new StartupReview[]{startupReview1, startupReview2, startupReview3, startupReview4};

        mAdapter = new StartupReviewsAdapter(this, new ArrayList<StartupReview>());
        mList = (ListView) findViewById(R.id.startup_reviews_list);
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tasky ty = new Tasky();
        ty.execute();
    }

    public void onStartupReviewClicked(View view) {
        Toast.makeText(this, "Add review is under development yet!", Toast.LENGTH_LONG).show();
    }

    class Tasky extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            mAdapter.addAll(Arrays.asList(startupReview));
            return 1;
        }
    }
}
