package com.example.reservio_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            ReservationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                    long reservationID = cursor.getLong(0);
                    String DateHour = cursor.getString(1);
                    String RestaurantName = cursor.getString(2);
                    String Address = cursor.getString(3);
                    int TableNum = cursor.getInt(4);
                    int SeatsNum = cursor.getInt(5);
                    long dateNum = cursor.getLong(6);
                    long timeNum = cursor.getLong(7);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                    try {
                        Date date = sdf.parse(DateHour);
                        SimpleDateFormat dateSdf = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
                        String Date = dateSdf.format(date);
                        String Time = timeSdf.format(date);

                        Intent intent = new Intent(MyReservationsScreen.this, UpdateDeleteDBScreen.class);
                        intent.putExtra("id", reservationID);
                        intent.putExtra("date", Date);
                        intent.putExtra("time", Time);
                        intent.putExtra("restaurantName", RestaurantName);
                        intent.putExtra("address", Address);
                        intent.putExtra("tableNum", TableNum);
                        intent.putExtra("seatsNum", SeatsNum);
                        intent.putExtra("dateNum", dateNum);
                        intent.putExtra("timeNum", timeNum);
                        startActivity(intent);

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
