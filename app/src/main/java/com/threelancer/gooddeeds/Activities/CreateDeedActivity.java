package com.threelancer.gooddeeds.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.threelancer.gooddeeds.R;
import com.threelancer.gooddeeds.model.Request;
import com.threelancer.gooddeeds.model.User;
import com.threelancer.gooddeeds.util.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class CreateDeedActivity extends AppCompatActivity {

    ImageButton backNavigation;
    Request mRequest;
    EditText need, offer, desc, requir;
    User user;
    String userKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdeed);

        backNavigation = (ImageButton) findViewById(R.id.back_nav_btn2);
        backNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userKey = sp.getString(Constants.KEY_EMAIL, "");

        Firebase mFirebase = new Firebase(Constants.FIREBASE_URL_USERS).child(userKey);


        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String reg_id = dataSnapshot.child("reg_id").getValue().toString();
                HashMap<String, Object> timestampJoined = new HashMap<>();
                timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                user = new User(name, userKey, timestampJoined, reg_id);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        initializeUi();
    }

    void initializeUi(){
        need = (EditText) findViewById(R.id.need_text);
        offer = (EditText) findViewById(R.id.offer_text);
        desc = (EditText) findViewById(R.id.description_text);
        requir = (EditText) findViewById(R.id.requirements_text);
    }

    public void onConfirmRequestPressed(View view) {
        HashMap<String, Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


        mRequest = new Request(need.getText().toString(),
                offer.getText().toString(),
                desc.getText().toString(),
                requir.getText().toString(),
                user.getName(),
                userKey,
                timestampCreated);

        Firebase requestRef = new Firebase(Constants.FIREBASE_URL_REQUESTS);
        requestRef.push().setValue(mRequest);

        Toast.makeText(getApplicationContext(), "Posting...", Toast.LENGTH_SHORT).show();

        this.finish();
    }
}
