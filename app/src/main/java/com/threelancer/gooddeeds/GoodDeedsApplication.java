package com.threelancer.gooddeeds;

import com.firebase.client.Firebase;

public class GoodDeedsApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
