package com.analyzer;

public class location {

	String phoneID;
	double latitude;
	double longitude;
	double speed;
	
	public String getPhoneID() {
		return phoneID;
	}
	public void setPhoneID(String phoneID) {
		this.phoneID = phoneID;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
