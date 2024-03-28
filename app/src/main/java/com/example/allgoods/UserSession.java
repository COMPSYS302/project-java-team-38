package com.example.allgoods;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class UserSession {
    private static volatile UserSession instance;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER = "user";
    private Gson gson = new Gson(); // Gson instance

    private UserSession(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
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

    public void createLoginSession(User user) {
        String userJson = gson.toJson(user); // Serialize the User object to JSON
        editor.putString(KEY_USER, userJson);
        editor.apply(); // Save the user JSON asynchronously
    }

    public User getUser() {
        String userJson = prefs.getString(KEY_USER, null);
        return gson.fromJson(userJson, User.class); // Deserialize the JSON back to a User object
    }

    public void logoutUser() {
        editor.clear();
        editor.apply(); // Clear the session asynchronously
    }

    public boolean isUserLoggedIn() {
        return getUser() != null;
    }
}
