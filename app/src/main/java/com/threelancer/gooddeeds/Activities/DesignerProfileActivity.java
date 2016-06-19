package com.threelancer.gooddeeds.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.threelancer.gooddeeds.DesignsAdapter;
import com.threelancer.gooddeeds.R;
import com.threelancer.gooddeeds.ReviewAdapter;
import com.threelancer.gooddeeds.model.Review;

import java.util.ArrayList;
import java.util.Arrays;

public class DesignerProfileActivity extends AppCompatActivity {

//    String userKey;
//    Firebase mFirebase;
//    User user;
    FloatingActionButton editFab;
    GridView mGrid;
    DesignsAdapter mAdapter;
    Integer[] src;
    ListView mList;
    ReviewAdapter mAdapter1;
    Review[] reviews;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_profile);

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        userKey = sp.getString(Constants.KEY_EMAIL, "");
//
//        mFirebase = new Firebase(Constants.FIREBASE_URL_USERS).child(userKey);
//
//        mFirebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.child("name").getValue().toString();
//                String email = dataSnapshot.child("email").getValue().toString();
//                HashMap<String, Object> timestampJoined = new HashMap<>();
//                timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
//
//                user = new User(name, email, timestampJoined);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Desginer Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        src = new Integer[]{R.drawable.a, R.drawable.aa, R.drawable.aaa, R.drawable.aaaa, R.drawable.aaaaa, R.drawable.bb};

        mAdapter = new DesignsAdapter(this, new ArrayList<Integer>());
        mGrid = (GridView) findViewById(R.id.designs_grid);
        mGrid.setAdapter(mAdapter);


        Review review1 = new Review(R.drawable.uberlogo3 ,"Uber", "You are really a creative designer");
        Review review2 = new Review(R.drawable.raye7logo ,"Raye7", "Designs gamda to7fa!");

        reviews = new Review[]{review1, review2};

        mAdapter1 = new ReviewAdapter(this, new ArrayList<Review>());
        mList = (ListView) findViewById(R.id.reviews_list);
        mList.setAdapter(mAdapter1);

        editFab = (FloatingActionButton) findViewById(R.id.edit_fab_button);
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Add review is under development yet!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Task t = new Task();
        t.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    class Task extends AsyncTask<Void, Void, Integer>{
        @Override
        protected Integer doInBackground(Void... params) {
            mAdapter.addAll(Arrays.asList(src));
            mAdapter1.addAll(Arrays.asList(reviews));
            return 1;
        }
    }
}
