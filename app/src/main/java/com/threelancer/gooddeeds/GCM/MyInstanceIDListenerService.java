package com.threelancer.gooddeeds.GCM;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {

        startService(new Intent(this, RegistrationIntentService.class));
    }
}
