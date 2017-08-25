package com.hsoftmobile.mapboxdemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by markushi on 8/25/17.
 */

public final class MyUtils {
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
