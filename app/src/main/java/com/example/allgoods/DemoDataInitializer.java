package com.example.allgoods;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class DemoDataInitializer {
    private static DemoDataInitializer instance;
    private boolean demoDataInstalled = false;
    private Context context;

    // Private constructor to prevent instantiation
    private DemoDataInitializer(Context context) {
        this.context = context.getApplicationContext();
    }

    // Public method to get the singleton instance
    public static synchronized DemoDataInitializer getInstance(Context context) {
        if (instance == null) {
            instance = new DemoDataInitializer(context);
        }
        return instance;
    }

    // Method to check and initialize demo data if necessary
    public Boolean initializeDemoDataIfNecessary() {


        if (!demoDataInstalled) {

            demoDataInstalled = true;
            return false;
        }
        return true;
    }
}
