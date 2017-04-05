package com.example.accessweb.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mapact.location;

import android.util.Log;



public class LocationParser {

	public static  ArrayList<location> parseFeed(String content) {
		
		ArrayList<location> locList = new ArrayList<>();
		
		try {
				
				JSONArray ar = new JSONArray(content);
	// use this loop when you wanted to display array----------
				for(int i= 0;i<ar.length();i++){
					JSONObject obj = ar.getJSONObject(i);
			
			//	JSONObject obj = new JSONObject(content);
					location lc= new location();
				//lc.setLocation_name(obj.getString("location_name"));
				lc.setLatitude(obj.getDouble("latitude"));
				lc.setLongitude(obj.getDouble("longitude"));
				// For CEP 1 method
				lc.setSpeed(obj.getDouble("speed"));
				
				
				//For CEP 2 method
				//lc.setSpeed(obj.getDouble("free_flow_speed_d1"));
				locList.add(lc);
				//change in bashine`s method speed to her own and comment location_name
			
				Log.d("json parse", obj.toString());
				}
			return locList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
