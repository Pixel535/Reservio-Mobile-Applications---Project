package com.example.reservio_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyReservationsScreen extends AppCompatActivity {

    private static final String TAG = "MyReservationsScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reservations_layout);
    }
}
