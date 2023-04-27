package com.example.reservio_project.BackgroundProcesses;

import org.osmdroid.views.MapView;

public interface IDataService {
    void RestaurantsAPISearch(double latitude, double longitude, int radius, MapView mapView,
                                            android.graphics.drawable.Drawable drawable);
}
