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

		btnSimpleMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showActivity(SimpleMapActivity.class);
			}
		});
	}

	// UTIL METHODS - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	private void showActivity(Class <?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
}
