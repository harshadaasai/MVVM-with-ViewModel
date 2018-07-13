package com.apero.mvvmviewmodel.view.callback;

import android.util.Log;
import android.view.View;

import com.apero.mvvmviewmodel.model.Followers;

public class OnClickCallback {

	private static final String TAG = "OnClickCallback";
	public void onClick(View view, Followers followers) {
		Log.e(TAG, "onClick: "+followers.getFullname() );
	}
}
