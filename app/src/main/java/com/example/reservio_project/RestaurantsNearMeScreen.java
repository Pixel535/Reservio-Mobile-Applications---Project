package com.example.reservio_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import android.Manifest.permission;


public class RestaurantsNearMeScreen  extends AppCompatActivity {

    private static final String TAG = "RestaurantsNearMeScreen";
    private MapView mapView;
    private IMapController mapController;
    private ArrayList<OverlayItem> restaurants = new ArrayList<>();
    Integer range;
    Double latitude;
    Double longitude;
    LocationManager locationManager;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.restaurants_near_me_layout);

        range = getIntent().getIntExtra("range", 0);

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(17);
        x = 0;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    GeoPoint currentLocation = new GeoPoint(latitude, longitude);
                    if(x == 0)
                    {
                        mapController.setCenter(currentLocation);
                    }
                    Marker marker = new Marker(mapView);
                    marker.setPosition(currentLocation);
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setIcon(getResources().getDrawable(org.osmdroid.library.R.drawable.person));
                    marker.setTitle("That's you !");
                    mapView.getOverlays().add(marker);
                    android.graphics.drawable.Drawable restauranticon = getResources().getDrawable(org.osmdroid.library.R.drawable.marker_default);
                    RestaurantsAPI.RestaurantsAPISearch(latitude, longitude, range, mapView, restauranticon);
                    mapView.invalidate();
                    x = 1;
                }
            });
        }
    }
}
