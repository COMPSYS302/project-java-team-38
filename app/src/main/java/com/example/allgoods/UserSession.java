package com.example.allgoods;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class UserSession {
    private static volatile UserSession instance;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER = "user";
    private User currentUser;
    private Gson gson = new Gson();

    private UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadUser();  // Load user when instance is created
    }

    public static UserSession getInstance(Context context) {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    instance = new UserSession(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private void loadUser() {
        String userJson = prefs.getString(KEY_USER, null);
        currentUser = gson.fromJson(userJson, User.class);  // Load user once and reuse
    }

    public void createLoginSession(User user) {
        currentUser = user;
    }

    public User getUser() {
        return currentUser;
    }

    public void logoutUser() {
        currentUser = null;
        prefs.edit().clear().apply();
    }
}

