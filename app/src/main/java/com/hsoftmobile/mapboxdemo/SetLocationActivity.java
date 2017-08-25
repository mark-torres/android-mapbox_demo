package com.hsoftmobile.mapboxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class SetLocationActivity extends AppCompatActivity {

	private LatLng[] positions;

	private String[] titles;

	private MapView mapView;
	private MapboxMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Mapbox access token
		Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

		setContentView(R.layout.activity_set_location);

		mapView = (MapView) findViewById(R.id.map_set_location);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(MapboxMap mapboxMap) {
				// Customize map with markers, polylines, etc.
				map = mapboxMap;
			}
		});

		positions = new LatLng[]{
				new LatLng(40.6976701,-74.2598711), // New York
				new LatLng(35.6735408,139.570302),  // Tokyo
				new LatLng(48.8589507,2.2770199),   // Paris
		};
		titles = new String[]{
				"New York",
				"Tokyo",
				"Paris"
		};

		Button btnCity1 = (Button) findViewById(R.id.btn_city1);
		Button btnCity2 = (Button) findViewById(R.id.btn_city2);
		Button btnCity3 = (Button) findViewById(R.id.btn_city3);

		btnCity1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showCity(0);
			}
		});
		btnCity2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showCity(1);
			}
		});
		btnCity3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showCity(2);
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

	private void showCity(int cityIndex) {
		// move camera
		CameraPosition position = new CameraPosition.Builder()
				.target(positions[cityIndex])
				.zoom(13)
				.build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
		// place marker
		map.clear();
		addMarker(positions[cityIndex], titles[cityIndex]);
	}

	private void addMarker(LatLng position, String title) {
		if (map == null) return;
		map.addMarker(new MarkerOptions().position(position).title(title));
	}
}
