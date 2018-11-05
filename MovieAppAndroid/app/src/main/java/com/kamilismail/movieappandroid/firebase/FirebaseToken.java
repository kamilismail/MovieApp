package com.kamilismail.movieappandroid.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.kamilismail.movieappandroid.SessionController;

public class FirebaseToken extends FirebaseInstanceIdService {

    private String registrationToken;
    private SessionController sessionController;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        this.registrationToken = refreshedToken;
        this.sessionController = new SessionController(this);
        sessionController.saveFirebaseToken(refreshedToken);
    }

    private void sendRegistrationToken() {

    }

    public String getRegistrationToken() {
        return this.registrationToken;
    }
}
