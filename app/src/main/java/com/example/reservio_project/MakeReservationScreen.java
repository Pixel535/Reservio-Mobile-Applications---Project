package com.example.reservio_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MakeReservationScreen  extends AppCompatActivity {

    private static final String TAG = "MakeReservationScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_reservation_layout);
    }
}
