package com.kamilismail.movieappandroid;

import android.content.Context;

public class HttpRequests {
    private String serverUrl;

    public HttpRequests (Context _context) {
        this.serverUrl = _context.getResources().getString(R.string.serverName);
    }

    public String authenticateUser() {
        StringBuilder url = new StringBuilder(this.serverUrl);
        url.append("/user/");
        return url.toString();
    }
}
