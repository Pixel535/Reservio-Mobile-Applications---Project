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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MakeReservationScreen  extends AppCompatActivity {

    private static final String TAG = "MakeReservationScreen";
    String restaurantName;
    String restaurantCuisine;
    String restaurantAdress;
    Button TimeButton;
    Button DateButton;
    NumberPicker TableNum;
    NumberPicker SeatsNum;
    TextView textView;
    int hour = -1;
    int minute = -1;
    int givenTableNum = 1;
    int givenSeatsNum = 1;
    Calendar calendar = Calendar.getInstance();
    final int systemYear = calendar.get(Calendar.YEAR);
    final int systemMonth = calendar.get(Calendar.MONTH);
    final int systemDay = calendar.get(Calendar.DAY_OF_MONTH);
    Button BookTable;
    String date;
    TextView error2;
    TextView error3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_reservation_layout);

        restaurantName = getIntent().getStringExtra("title");
        restaurantCuisine = getIntent().getStringExtra("body");
        restaurantAdress = getIntent().getStringExtra("adress");

        textView = findViewById(R.id.RestaurantName);
        textView.setText(restaurantName);

        DateButton = findViewById(R.id.DatePickerButton);
        TimeButton = findViewById(R.id.HourPickerButton);
        TableNum = findViewById(R.id.Tablenumber);
        SeatsNum = findViewById(R.id.HowManySeats);

        TableNum.setMaxValue(25);
        TableNum.setMinValue(1);
        TableNum.setValue(1);

        SeatsNum.setMaxValue(10);
        SeatsNum.setMinValue(1);
        SeatsNum.setValue(1);

        TableNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                givenTableNum = newValue;
            }
        });

        SeatsNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                givenSeatsNum = newValue;
            }
        });

        BookTable = findViewById(R.id.BookTableButton);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
        BookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date != null && hour != -1 && minute != -1)
                {
                    try {
                        Date userDate = simpleDateFormat.parse(date);
                        Date TodayDate = simpleDateFormat.parse(systemDay + "/" + (systemMonth + 1) + "/" + systemYear);
                        if(userDate.after(TodayDate))
                        {
                            long dateNum = userDate.getTime();
                            Date Timedate = simpleTimeFormat.parse(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                            long timeNum = Timedate.getTime();
                            Reservation reservation = new Reservation(restaurantName, date, hour, minute, givenTableNum, givenSeatsNum, restaurantAdress, dateNum, timeNum);
                            DataBase dataBase = new DataBase(MakeReservationScreen.this);
                            dataBase.addReservation(reservation);
                            Intent intent = new Intent(MakeReservationScreen.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            error2.setVisibility(View.VISIBLE);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    error3.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                TimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popDatePicker(View view)
    {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day + "/" + (month+1) + "/" + year;
                DateButton.setText(date);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog dimePickerDialog = new DatePickerDialog(this, style, onDateSetListener, systemYear, systemMonth, systemDay);
        dimePickerDialog.setTitle("Select Date");
        dimePickerDialog.show();
    }
}
