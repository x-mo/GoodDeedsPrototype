package com.threelancer.gooddeeds.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.threelancer.gooddeeds.R;

import com.threelancer.gooddeeds.model.User;
import com.threelancer.gooddeeds.util.Constants;
import com.firebase.client.Firebase;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

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

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    User user;

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";
    SharedPreferences sharedPreferences;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        InstanceID instanceID = InstanceID.getInstance(this);
        String senderID = getResources().getString(R.string.gcm_defaultSenderId);

        try{
            String token = instanceID.getToken(senderID, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "GCM Registration Token: " + token);

            sharedPreferences.edit().putString(GCM_TOKEN, token).apply();

            sendRegistertionToServer(token);

        }catch (IOException e){
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }

        Intent registrationComplete = new Intent("registeration_complete");
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistertionToServer(String token){
        // send network request
        sendTokenToServer(token);

        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }

    private void sendTokenToServer(String token){

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;

        // get current user

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userKey = sp.getString(Constants.KEY_EMAIL, "");

        Log.v("myTest"," RegistrationIntent userKey " + userKey);

        Firebase userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(userKey);
        userRef.child(Constants.FIREBASE_PROPERTY_USER_REG_ID).setValue(token);

        try {

            final String link = "http://gogetout.net/Threelancer/gcm/addRegisterationID.php";

            URL url = new URL(link);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("registration_id", "UTF-8") + "=" +URLEncoder.encode(token, "UTF-8");

            writer.write(post_data);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";

            while ((line = reader.readLine()) != null) result +=line;
            if(result.length() == 0) return;
            inputStream.close();

            Log.v("myTest", "is registeration success? " + result);

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
    }
}
