package com.example.reservio_project;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class CustomMarkerInfoWindow extends InfoWindow {
    private Button button;
    private TextView titleTextView;
    private TextView bodyTextView;
    private String title;
    private String body;
    private String adress;

    public CustomMarkerInfoWindow(int layoutResId, MapView mapView, String title, String body, String adress) {
        super(layoutResId, mapView);
        this.title = title;
        this.body = body;
        this.adress = adress;
    }

    @Override
    public void onOpen(Object item) {

        if (RestaurantsAPI.currentInfoWindow != null && RestaurantsAPI.currentInfoWindow.isOpen()) {
            RestaurantsAPI.currentInfoWindow.close();
        }
        RestaurantsAPI.currentInfoWindow = this;
        View view = getView();
        if (view != null) {
            button = view.findViewById(R.id.map_more_info_imageView);
            titleTextView = view.findViewById(R.id.map_popup_header);
            bodyTextView = view.findViewById(R.id.map_popup_body);
            titleTextView.setText(title);
            bodyTextView.setText(body);
            button.setText("RESERVE");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getMapView().getContext(), MakeReservationScreen.class);
                    intent.putExtra("title", title);
                    intent.putExtra("body", body);
                    intent.putExtra("adress", adress);
                    getMapView().getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onClose() {

    }

    public void setTitle(String title) {
        this.title = title;
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    public void setBody(String body) {
        this.body = body;
        if (bodyTextView != null) {
            bodyTextView.setText(body);
        }
    }
}