package com.example.reservio_project;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.IOException;

public class RestaurantsAPI {

    public static CustomMarkerInfoWindow currentInfoWindow;
    public static void RestaurantsAPISearch(double latitude, double longitude, int radius, MapView mapView,
                                            android.graphics.drawable.Drawable drawable)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.overpass-api.de/api/interpreter?data=[out:json];node(around:" + radius + "," + latitude + "," + longitude + ")[\"amenity\"=\"restaurant\"];out;")
                        .build();
                try (Response response = httpClient.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray elements = jsonResponse.getJSONArray("elements");
                        for (int i = 0; i < elements.length(); i++) {
                            JSONObject element = elements.getJSONObject(i);
                            String name = element.getJSONObject("tags").optString("name");
                            double lat = element.optDouble("lat");
                            double lon = element.optDouble("lon");
                            String cuisine = element.getJSONObject("tags").optString("cuisine");
                            String adress = element.getJSONObject("tags").optString("addr:city") + " " +
                                            element.getJSONObject("tags").optString("addr:postcode") + " " +
                                            element.getJSONObject("tags").optString("addr:street") + " " +
                                            element.getJSONObject("tags").optString("addr:housenumber");
                            GeoPoint restaurantLocation = new GeoPoint(lat, lon);
                            Marker restaurantMarker = new Marker(mapView);
                            restaurantMarker.setPosition(restaurantLocation);
                            restaurantMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                            restaurantMarker.setIcon(drawable);
                            CustomMarkerInfoWindow infoWindow = new CustomMarkerInfoWindow(R.layout.custom_pop_up, mapView, name, cuisine, adress);
                            restaurantMarker.setInfoWindow(infoWindow);
                            mapView.getOverlays().add(restaurantMarker);
                        }
                    } else {
                        System.out.println("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
