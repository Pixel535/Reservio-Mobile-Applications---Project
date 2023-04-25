package com.example.reservio_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.SimpleCursorAdapter;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reservations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "reservation";
    private static final String COLUMN_ID = BaseColumns._ID;
    private static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TABLE_NUM = "table_num";
    private static final String COLUMN_SEATS_NUM = "seats_num";
    Context context;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_RESTAURANT_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_TABLE_NUM + " INTEGER, "
                + COLUMN_SEATS_NUM + " INTEGER)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long addReservation(Reservation reservation)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, reservation.getDate());
        values.put(COLUMN_TIME, reservation.getTime());
        values.put(COLUMN_RESTAURANT_NAME, reservation.getRestaurantName());
        values.put(COLUMN_ADDRESS, reservation.getAddress());
        values.put(COLUMN_TABLE_NUM, reservation.getTableNum());
        values.put(COLUMN_SEATS_NUM, reservation.getSeatsNum());
        long id = database.insert(TABLE_NAME, null, values);
        database.close();
        return id;
    }

    public SimpleCursorAdapter getAllReservations()
    {
        SQLiteDatabase database = this.getReadableDatabase();
        String columns[] = {COLUMN_ID, COLUMN_DATE + " || ' ' || " + COLUMN_TIME + " AS " + "reservation_datetime", COLUMN_RESTAURANT_NAME, COLUMN_ADDRESS, COLUMN_TABLE_NUM, COLUMN_SEATS_NUM};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null,null, null, null);
        String[] fromFieldNames = new String[]{
                COLUMN_ID, "reservation_datetime", COLUMN_RESTAURANT_NAME, COLUMN_ADDRESS, COLUMN_TABLE_NUM, COLUMN_SEATS_NUM
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.ReservationDate, R.id.RestaurantName, R.id.RestaurantAdress, R.id.TNum, R.id.SNum};
        SimpleCursorAdapter reservationAdapter = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return reservationAdapter;
    }
}
