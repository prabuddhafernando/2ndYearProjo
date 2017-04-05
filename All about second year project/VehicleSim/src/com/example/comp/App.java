package com.example.comp;

import android.content.Context;

import com.example.sender.VehicleDataTransmitter;

public class App {

	public static final String TAG = "VehicleSim";
	
	private static App instance;
	private VehicleDataTransmitter vehicleDataTransmitter;
	
	public static App getInstance(){
		if(instance==null){
			instance=new App();
		}
		return instance;
	}
	
	public VehicleDataTransmitter getVehicleDataTransmitter(){
		return vehicleDataTransmitter;
	}
	
	public void setVehicleDataTransmitter(
			VehicleDataTransmitter vehicleDataTransmitter) {
		this.vehicleDataTransmitter = vehicleDataTransmitter;
	}

}
