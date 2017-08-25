package com.hsoftmobile.mapboxdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	private Context thisActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		thisActivity = this;

		Button btnSimpleMap = (Button) findViewById(R.id.btn_simple_map);
		Button btnMarkersMap = (Button) findViewById(R.id.btn_markers_map);
		Button btnSetLocation = (Button) findViewById(R.id.btn_set_location);
		//Button btnGetLocation = (Button) findViewById(R.id.btn_get_location);

		btnSimpleMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showActivity(SimpleMapActivity.class);
			}
		});
		btnMarkersMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showActivity(MapMarkerActivity.class);
			}
		});
		btnSetLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showActivity(SetLocationActivity.class);
			}
		});
	}

	// UTIL METHODS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	private void showActivity(Class <?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
}
