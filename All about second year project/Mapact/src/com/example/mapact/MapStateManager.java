package com.example.mapact;

import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

import com.google.android.gms.internal.mo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.SharedPreferences;

public class MapStateManager {
	private static final String LONGITUDE = "logitude";
	private static final String LATITUDE = "latitude";
	private static final String ZOOM = "zoom";
	private static final String BEARING = "bearing";
	private static final String TILT = "tilt";
	private static final String MAPTYPE = "MAPTYPE";
	private static final String PREF_NAME = "mapCameraState";
	

	
	private SharedPreferences mapStatePrefs;

	public MapStateManager(Context context) {
		mapStatePrefs = context.getSharedPreferences(PREF_NAME,
				context.MODE_PRIVATE);
		
	}
	
	
	// saving current settings of map object
	public void saveMapState(GoogleMap map){
		
		SharedPreferences.Editor editor = mapStatePrefs.edit();
		CameraPosition position = map.getCameraPosition();
		
		editor.putFloat(LATITUDE, (float) position.target.latitude);
		editor.putFloat(LONGITUDE, (float) position.target.longitude);
		editor.putFloat(ZOOM, (float) position.zoom);
		editor.putFloat(BEARING, (float) position.bearing);
		editor.putFloat(TILT, (float) position.tilt);
		editor.putFloat(MAPTYPE, map.getMapType());
		
		editor.commit();
	}
	
	// getting saved setting of map
	public CameraPosition getSavedCameraPosition() {
		double latitude = mapStatePrefs.getFloat(LATITUDE, 0);
		if (latitude == 0) {
			return null;
		}
		float longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
		LatLng target = new LatLng(latitude, longitude);

		float zoom = mapStatePrefs.getFloat(ZOOM, 0);
		float bearing = mapStatePrefs.getFloat(BEARING, 0);
		float tilt = mapStatePrefs.getFloat(TILT, 0);

		CameraPosition postition = new CameraPosition(target, zoom, tilt,
				bearing);
		return postition;
	}
	
	
	
	

	// getting map type
	public float getMapType() {

		float mapType = mapStatePrefs.getFloat(MAPTYPE,
				GoogleMap.MAP_TYPE_NORMAL);

		return mapType;
	}

}
