package com.hsoftmobile.mapboxdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapMarkerActivity extends AppCompatActivity {

	private Mapbox mapbox;
	private MapView mapView;
	private MapboxMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Mapbox access token
		mapbox = Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

		setContentView(R.layout.activity_map_marker);

		mapView = (MapView) findViewById(R.id.map_marker);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(MapboxMap mapboxMap) {
				// Customize map with markers, polylines, etc.
				map = mapboxMap;
				setupMap();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mapView.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mapView.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	// MAP METHODS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	private void setupMap() {
		if (map == null) return;
		// set long click listener
		map.setOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(@NonNull LatLng point) {
				map.clear();
				addMarker(point, "My marker");
			}
		});
	}

	private void addMarker(LatLng position, String title) {
		if (map == null) return;
		map.addMarker(new MarkerOptions().position(position).title(title));
	}
}
