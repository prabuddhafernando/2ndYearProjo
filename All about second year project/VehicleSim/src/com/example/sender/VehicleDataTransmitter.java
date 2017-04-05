package com.example.sender;

import org.json.JSONObject;

import android.content.Context;


public class VehicleDataTransmitter extends DataTransmitter {
	
	public VehicleDataTransmitter(Context context,String url) {
		super(context,url);
	}
	
	public JSONObject testConnection() {
		
        String jsonString = "{\"imei\" : \""+"testRequest"+"\",\n" +
                "\"lon\":"+0.0+",\n" +
                "\"lat\":"+0.0+",\n" +
                "\"timemili\":"+123+"}";

        JSONObject response = sendReceive("", jsonString);
        return response;

    }
	
	public void sendData(String id, double lon,double lat ,long timemili) {
		
        String jsonString = "{\"imei\" : \""+id+"\",\n" +
                "\"lon\":"+lon+",\n" +
                "\"lat\":"+lat+",\n" +
                "\"timemili\":"+timemili+"}";

        publishToServerOutOnly(jsonString);

    }
	
	

}
