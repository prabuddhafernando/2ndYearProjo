package com.example.comp;

import java.util.UUID;

import com.example.sender.VehicleDataTransmitter;

import android.util.Log;

public class Vehicle extends Thread {
	
	private Junction junction;
	
    private final String id;
    private double startlon, startlat, endlon, endlat;
    private int totaltime, unittime;
    private VehicleDataTransmitter transmitter;
    
    public Vehicle(Junction junction) {
    	
    	this.junction = junction;
        this.startlon = junction.getStartLon();
        this.startlat = junction.getStartLat();
        this.endlat = junction.getEndLat();
        this.endlon = junction.getEndLon();
        this.totaltime = junction.getTotalTime();
        this.unittime = junction.getUnitTime();

        //generate random UUIDs
        UUID idOne = UUID.randomUUID();
        id = idOne.toString().substring(0, 5);

        transmitter=App.getInstance().getVehicleDataTransmitter();
    }

    public void run() {
        Log.i(App.TAG,"started "+id );
        int steps = (int) (totaltime / unittime);
        double time1 = System.currentTimeMillis();
        for (int i = 1; i <= steps; i++) {

        	long curTime = System.currentTimeMillis();
            double curLon = startlon + (endlon - startlon) / steps * i;
            double curLat = startlat + (endlat - startlat) / steps * i;
            Log.i(App.TAG,id + " " + curLon + " " + curLat);
            transmitter.sendData(id, curLon, curLat, curTime);
            try {
                Thread.sleep(unittime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double time2 = System.currentTimeMillis();

        Log.i(App.TAG,id+" finished. Time diff " + (time2-time1)/1000);

    }
}