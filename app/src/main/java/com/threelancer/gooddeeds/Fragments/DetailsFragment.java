package com.threelancer.gooddeeds.Fragments;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.threelancer.gooddeeds.ApplicationAdapter;
import com.threelancer.gooddeeds.R;
import com.threelancer.gooddeeds.model.Applicant;
import com.threelancer.gooddeeds.model.User;
import com.threelancer.gooddeeds.util.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class DetailsFragment extends Fragment {

    Button applyBtn;
    private String mListId, mListRequestorEmail;
    private Firebase requestRef;
    TextView profileName, detailsDesc, detailsRequir, detailsTime, detailsOffer;
    User user;

    ApplicationAdapter mApplicationAdapter;
    ListView mList;

    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_details, container, false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userKey = sp.getString(Constants.KEY_EMAIL, "");

        Firebase mFirebase = new Firebase(Constants.FIREBASE_URL_USERS).child(userKey);


        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String reg_id = dataSnapshot.child("reg_id").getValue().toString();
                HashMap<String, Object> timestampJoined = new HashMap<>();
                timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                user = new User(name, email, timestampJoined, reg_id);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Intent intent = getActivity().getIntent();
        mListId = intent.getStringExtra(Constants.KEY_LIST_ID);
        mListRequestorEmail = intent.getStringExtra(Constants.KEY_EMAIL);

        Firebase requestsRef = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(mListId);

        mApplicationAdapter = new ApplicationAdapter(getActivity(), Applicant.class, R.layout.feed_item, requestsRef);

        mList = (ListView) root.findViewById(R.id.apply_list);
        mList.setAdapter(mApplicationAdapter);


        requestRef = new Firebase(Constants.FIREBASE_URL_REQUESTS).child(mListId);
        // make a new ref for applicants people

        initializeScreen(root);


        fillInDataInViews();

        return root;
    }


    void initializeScreen(View root){
        profileName = (TextView) root.findViewById(R.id.profile_name);
        detailsDesc = (TextView) root.findViewById(R.id.details_desc);
        detailsRequir = (TextView) root.findViewById(R.id.details_requir);
        detailsTime = (TextView) root.findViewById(R.id.time_text);
        detailsOffer = (TextView) root.findViewById(R.id.details_offer);

        applyBtn = (Button) root.findViewById(R.id.apply_button);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show dialog
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                alert.setTitle("Proposal Statement");
                alert.setView(edittext);

                alert.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String note = edittext.getText().toString();
//                        Toast.makeText(getContext(), note, Toast.LENGTH_LONG).show();
                        Firebase applicationsRef = new Firebase(Constants.FIREBASE_URL_APPLICATIONS).child(mListId);

                        Applicant applicant = new Applicant(user.getName(),
                                user.getEmail(),
                                note);

                        applicationsRef.push().setValue(applicant);

                        sendNotification();

                    }
                });
                alert.show();
            }
        });
    }

    void fillInDataInViews(){
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                profileName.setText(dataSnapshot.child(Constants.FIREBASE_PROPERTY_REQUESTOR_NAME).getValue().toString());
                detailsDesc.setText(dataSnapshot.child(Constants.FIREBASE_PROPERTY_DESCRIPTION).getValue().toString());
                detailsRequir.setText(dataSnapshot.child(Constants.FIREBASE_PROPERTY_REQUIREMENTS).getValue().toString());
                detailsOffer.setText(dataSnapshot.child(Constants.FIREBASE_PROPERTY_OFFER).getValue().toString());

                long time = (long) dataSnapshot.child(Constants.FIREBASE_PROPERTY_TIMESTAMP_CREATED)
                        .child(Constants.FIREBASE_PROPERTY_TIMESTAMP).
                                getValue();
                // gives an error
//                detailsTime.setText(Utilities.SIMPLE_DATE_FORMAT.format(time));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    void sendNotification(){
        Firebase postManRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mListRequestorEmail);

        postManRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String reg_id = dataSnapshot.child(Constants.FIREBASE_PROPERTY_USER_REG_ID).getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                TaskSendNotification task = new TaskSendNotification();
                task.execute(reg_id, name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    class TaskSendNotification extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            try {

                final String link = "http://gogetout.net/Threelancer/gcm/SendPushNotification.php";

                URL url = new URL(link);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data =
                        URLEncoder.encode("registration_id", "UTF-8") + "=" +URLEncoder.encode(params[0], "UTF-8")
                                + "&" +  URLEncoder.encode("applicant", "UTF-8") +"="+URLEncoder.encode(params[1], "UTF-8");


                writer.write(post_data);
                writer.flush();
                writer.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = reader.readLine()) != null) result +=line;
                if(result.length() == 0) return null;
                inputStream.close();

                Log.v("myTest", "is sending notification success? " + result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(null != httpURLConnection)
                    httpURLConnection.disconnect();
                if(null != reader){
                    try{
                        reader.close();
                    }catch (final IOException e){
                        Log.e("MyTest", "Error closing reader stream", e);
                    }
                }
            }
            return null;
        }
    }
}
