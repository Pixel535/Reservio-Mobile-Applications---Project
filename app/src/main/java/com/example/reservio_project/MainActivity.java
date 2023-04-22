package com.example.reservio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        Button btnNavToMyReservations = (Button) findViewById(R.id.MyReservationsButton);
        btnNavToMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNavToMyReservations");

                Intent intent = new Intent(MainActivity.this, MyReservationsScreen.class);
                startActivity(intent);
            }
        });

        Button btnNavToRestaurantsNearMe = (Button) findViewById(R.id.RestaurantsNearMeButton);
        btnNavToRestaurantsNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btnNavToRange");

                Intent intent = new Intent(MainActivity.this, RangeScreen.class);
                startActivity(intent);
            }
        });
    }
}