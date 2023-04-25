package com.example.reservio_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MyReservationsScreen extends AppCompatActivity {

    private static final String TAG = "MyReservationsScreen";
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBase = new DataBase(this);

        if(dataBase.getAllReservations().isEmpty())
        {
            setContentView(R.layout.empty_data_base);
        }
        else
        {
            setContentView(R.layout.my_reservations_layout);
            ListView ReservationList = (ListView) findViewById(R.id.DataBaseItems);
            SimpleCursorAdapter simpleCursorAdapter = dataBase.getAllReservations();
            ReservationList.setAdapter(simpleCursorAdapter);
        }
    }
}
