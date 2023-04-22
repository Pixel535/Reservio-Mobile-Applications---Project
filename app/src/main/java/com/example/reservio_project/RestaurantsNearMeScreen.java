package com.example.reservio_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantsNearMeScreen  extends AppCompatActivity {

    private static final String TAG = "RestaurantsNearMeScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_near_me_layout);
    }
}
