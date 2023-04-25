package com.example.reservio_project;

import java.util.Locale;

public class Reservation {

    private int id;
    private String restaurantName;
    private String date;
    private String time;
    private int TableNum;
    private int SeatsNum;
    private String address;

    public Reservation(String restaurantName, String date, int hour, int minutes, int TableNum, int SeatsNum, String address)
    {
        this.restaurantName = restaurantName;
        this.date = date;
        this.time = String.format(Locale.getDefault(), "%02d:%02d", hour, minutes);
        this.TableNum = TableNum;
        this.SeatsNum = SeatsNum;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTableNum() {
        return TableNum;
    }

    public void setTableNum(int tableNum) {
        TableNum = tableNum;
    }

    public int getSeatsNum() {
        return SeatsNum;
    }

    public void setSeatsNum(int seatsNum) {
        SeatsNum = seatsNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
