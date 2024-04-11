package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        //This is for Setting content view to activity search once clicked on search bar
        //I was thinking to have this as soon as the search bar is clicked instead of ever
        //thing being in the Main Activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
