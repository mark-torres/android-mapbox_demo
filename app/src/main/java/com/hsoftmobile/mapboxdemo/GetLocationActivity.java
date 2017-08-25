package com.hsoftmobile.mapboxdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class GetLocationActivity extends AppCompatActivity implements LocationListener {

	private MapView mapView;

	private MapboxMap map;

	private ImageButton btnMyLocation;

	private TextView textStatus;

	private boolean locationRequestInProgress;

	private Context thisActivity;

	private LocationManager locationManager;

	private String locationProvider;

	private final String PERMISSIONS[] = new String[]{
			Manifest.permission.ACCESS_FINE_LOCATION
	};

	private final int PERM_REQ_ACCESS_FINE_LOCATION = 301;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		thisActivity = this;

		// Mapbox access token
		Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

		setContentView(R.layout.activity_get_location);

		locationRequestInProgress = false;

		// bind map view
		mapView = (MapView) findViewById(R.id.map_get_location);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(MapboxMap mapboxMap) {
				// Customize map with markers, polylines, etc.
				map = mapboxMap;
				checkLocationPermissions();
			}
		});

		btnMyLocation = (ImageButton) findViewById(R.id.btn_my_location);
		textStatus = (TextView) findViewById(R.id.text_status);

		btnMyLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!locationRequestInProgress) {
					startLocationListener();
				} else {
					MyUtils.showToast(thisActivity, "Location request already in progress, please wait.");
				}
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
		if (locationRequestInProgress) {
			stopLocationListener();
		}
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

	// UTIL METHODS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	private void setupLocationMaganer() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationProvider = locationManager.getBestProvider(new Criteria(), true);
		if (locationProvider == null || locationProvider.isEmpty()) {
			MyUtils.showToast(thisActivity, "You don't have any location providers enabled.");
			finish();
		}
		textStatus.setText("Click the location button to get your current location.");
		// show location button
		btnMyLocation.setVisibility(View.VISIBLE);
		// check for last known location
		try {
			Location location = locationManager.getLastKnownLocation(locationProvider);
			if (location != null) {
				onLocationChanged(location);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private void addMarker(LatLng position, String title) {
		if (map == null) return;
		map.addMarker(new MarkerOptions().position(position).title(title));
	}

	// LOCATION LISTENER - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	@Override
	public void onLocationChanged(Location location) {
		Log.i(Globals.LOG_TAG, "Location changed");
		// move camera
		LatLng newPosition = new LatLng(location.getLatitude(), location.getLongitude());
		CameraPosition position = new CameraPosition.Builder()
				.target(newPosition)
				.zoom(12)
				.build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
		// add marker
		addMarker(newPosition, "Current location");
		// stop listening location
		if (locationRequestInProgress) {
			stopLocationListener();
		}
	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	@Override
	public void onProviderEnabled(String s) {

	}

	@Override
	public void onProviderDisabled(String s) {

	}

	private void startLocationListener() {
		Log.i(Globals.LOG_TAG, "Start location listener");
		locationRequestInProgress = true;
		try {
			locationManager.requestLocationUpdates(locationProvider, 5000, 10f, this);
		} catch (SecurityException e) {
			e.printStackTrace();
			MyUtils.showToast(thisActivity, e.getLocalizedMessage());
			finish();
		}
	}

	private void stopLocationListener() {
		Log.i(Globals.LOG_TAG, "Stop location listener");
		locationRequestInProgress = false;
		try {
			locationManager.removeUpdates(this);
		} catch (SecurityException e) {
			MyUtils.showToast(thisActivity, e.getLocalizedMessage());
			finish();
		}
	}

	// PERMISSIONS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	private void checkLocationPermissions() {
		Log.i(Globals.LOG_TAG, "Checking permissions");
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			Log.i(Globals.LOG_TAG, "Requesting permissions");
			ActivityCompat.requestPermissions(this, PERMISSIONS, PERM_REQ_ACCESS_FINE_LOCATION);
		} else {
			Log.i(Globals.LOG_TAG, "Permissions already granted");
			setupLocationMaganer();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERM_REQ_ACCESS_FINE_LOCATION:
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					setupLocationMaganer();
				} else {
					MyUtils.showToast(this, "The map requires access to your location.");
					finish();
				}
				return;
		}
	}
}
