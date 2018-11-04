package com.kamilismail.movieappandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kamilismail.movieappandroid.activities.LoginActivity;
import com.kamilismail.movieappandroid.helpers.CalendarHelper;

import java.text.ParseException;

public class SessionController {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_COOKIE = "Cookie";
    private static final String USERNAME = "Username";
    private static final String ROLE = "Role";
    private static final String FIREBASE_TOKEN = "FirebaseToken";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionController(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String cookie) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_COOKIE, cookie);
        // commit changes
        editor.commit();
    }

    public void saveUsername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public void saveUserRole(String role) {
        editor.putString(ROLE, role);
        editor.commit();
    }

    public void saveFirebaseToken(String token) {
        editor.putString(FIREBASE_TOKEN, token);
        editor.commit();
    }

    public void saveFragmentState(String key, String value, String keyData) {
        CalendarHelper calendarHelper = new CalendarHelper();
        editor.putString(key, value);
        editor.putString(keyData, calendarHelper.getCurrentDateWitTime());
        editor.commit();
    }

    public String getFragmentState(String key, String keyData) {
        CalendarHelper calendarHelper = new CalendarHelper();
        String data = pref.getString(keyData, null);
        if (data == null)
            return null;
        try {
            if (calendarHelper.checkIfNotToOld(data)) {
                return pref.getString(key, null);
            } else
                return null;
        } catch (ParseException e) {
            return null;
        }
    }

    public String getFirebaseToken() {
        return pref.getString(FIREBASE_TOKEN, null);
    }

    public String getUsername() {
        return pref.getString(USERNAME, null);
    }

    public String getRole() {
        return pref.getString(ROLE, null);
    }

    public String getCookie() {
        return pref.getString(KEY_COOKIE, null);
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
