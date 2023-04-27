package com.example.reservio_project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservio_project.BackgroundProcesses.DataBase;
import com.example.reservio_project.BackgroundProcesses.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateDeleteDBScreen extends AppCompatActivity {

    String Date;
    String Time;
    String RestaurantName;
    String Address;
    int TableNum;
    int SeatsNum;
    long reservationID;
    long dateNum;
    long timeNum;
    Calendar calendar = Calendar.getInstance();
    final int systemYear = calendar.get(Calendar.YEAR);
    final int systemMonth = calendar.get(Calendar.MONTH);
    final int systemDay = calendar.get(Calendar.DAY_OF_MONTH);
    int hour;
    int minutes;

    TextView tvResName;
    TextView tvResAddr;
    Button changeDateButton;
    Button changeHourButton;
    NumberPicker changeTableNumber;
    NumberPicker changeSeatsNumber;
    Button saveButton;
    TextView uderror;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_db_items);

        Date = getIntent().getStringExtra("date");
        Time = getIntent().getStringExtra("time");
        RestaurantName = getIntent().getStringExtra("restaurantName");
        Address = getIntent().getStringExtra("address");
        TableNum = getIntent().getIntExtra("tableNum", 0);
        SeatsNum = getIntent().getIntExtra("seatsNum", 0);
        reservationID = getIntent().getLongExtra("id", 0);
        dateNum = getIntent().getLongExtra("dateNum", 0);
        timeNum = getIntent().getLongExtra("timeNum", 0);

        tvResName = findViewById(R.id.udRestaurantName);
        tvResAddr = findViewById(R.id.udRestaurantAddress);
        changeDateButton = findViewById(R.id.udDatePickerButton);
        changeHourButton = findViewById(R.id.udHourPickerButton);
        changeTableNumber = findViewById(R.id.udTablenumber);
        changeSeatsNumber = findViewById(R.id.udHowManySeats);

        changeDateButton.setText(Date);
        changeHourButton.setText(Time);

        changeTableNumber.setMaxValue(25);
        changeTableNumber.setMinValue(1);
        changeTableNumber.setValue(TableNum);

        changeSeatsNumber.setMaxValue(10);
        changeSeatsNumber.setMinValue(1);
        changeSeatsNumber.setValue(SeatsNum);

        tvResName.setText(RestaurantName);
        tvResAddr.setText(Address);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = sdf.parse(Time);
            Date time = sdf.parse(Time);
            calendar.setTime(time);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minutes = calendar.get(Calendar.MINUTE);

            changeTableNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                    TableNum = newValue;
                }
            });

            changeSeatsNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                    SeatsNum = newValue;
                }
            });

            saveButton = findViewById(R.id.SaveButton);
            uderror = findViewById(R.id.udError);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        java.util.Date userDate = simpleDateFormat.parse(Date);
                        Date TodayDate = simpleDateFormat.parse(systemDay + "/" + (systemMonth + 1) + "/" + systemYear);
                        if(userDate.after(TodayDate))
                        {
                            dateNum = userDate.getTime();
                            Date Timedate = simpleTimeFormat.parse(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));
                            timeNum = Timedate.getTime();
                            Reservation reservation = new Reservation(RestaurantName, Date, hour, minutes, TableNum, SeatsNum, Address, dateNum, timeNum);
                            DataBase dataBase = new DataBase(UpdateDeleteDBScreen.this);
                            dataBase.updateReservation(reservationID, reservation);
                            Intent intent = new Intent(UpdateDeleteDBScreen.this, MyReservationsScreen.class);
                            startActivity(intent);
                        }
                        else
                        {
                            uderror.setVisibility(View.VISIBLE);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            deleteButton = findViewById(R.id.DeleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBase dataBase = new DataBase(UpdateDeleteDBScreen.this);
                    dataBase.deleteItem(reservationID);
                    Intent intent = new Intent(UpdateDeleteDBScreen.this, MyReservationsScreen.class);
                    startActivity(intent);
                }
            });

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void udpopTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minutes = selectedMinute;
                Time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                changeHourButton.setText(Time);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minutes, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void udpopDatePicker(View view)
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Date = day + "/" + (month+1) + "/" + year;
                changeDateButton.setText(Date);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog dimePickerDialog = new DatePickerDialog(this, style, onDateSetListener, systemYear, systemMonth, systemDay);
        dimePickerDialog.setTitle("Select Date");
        dimePickerDialog.show();
    }
}
