package com.example.comp;

import com.example.vehiclesim.R;
import com.example.vehiclesim.R.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Junction extends Thread{

	private Context context;
	private LinearLayout layout;
	private SeekBar seekSpeed;
	private ToggleButton toggleOnOff;
	private TextView txtJunctionName;
	private TextView txtSpeed;
	
	private int speed;
	private boolean enabled;
	private boolean alive = true;
	
	private double startLon,startLat,endLon,endLat;
	private double distance;
	private int totalTime,unitTime;
	
	private String juncName;
	private Object mPauseLock;
	
	public Junction(Context context,String name,double startLon, double startLat,double endLon,double endLat) {
		
		this.context = context;		
		this.juncName = name;
		mPauseLock = new Object();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout wrapper= new LinearLayout(context);
		layout = (LinearLayout)inflater.inflate(R.layout.junction, wrapper);
		
		this.startLon = startLon;
		this.startLat = startLat;
		this.endLon = endLon;
		this.endLat = endLat;
		
        distance = Math.abs(distFrom(startLat,startLon,endLat,endLon));      
        unitTime = 3000;
		
		seekSpeed = (SeekBar)layout.findViewById(R.id.seekSpeed);		
		seekSpeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {	
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Junction.this.speed=progress;
				txtSpeed.setText(progress + "");
				totalTime = (int) ( (distance/(speed*5/18))*1000);				
			}
		});
		
		toggleOnOff = (ToggleButton)layout.findViewById(R.id.toggleONOFF);
		toggleOnOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				boolean checked = toggleOnOff.isChecked();
				if(checked){
					resumeJunction();
				}else{
					pauseJunction();
				}
			}
		});
		
		txtJunctionName = (TextView)layout.findViewById(R.id.txtJuncName);
		txtJunctionName.setText(juncName);
		
		txtSpeed=(TextView)layout.findViewById(R.id.txtSpeed);
		
	}
	
	public String getJuncionName() {
		return juncName;
	}
	
	public void pauseJunction(){
		synchronized (mPauseLock) {
            enabled = false;
        }
	}
	
	public void resumeJunction() {
		synchronized (mPauseLock) {
			enabled = true;
			mPauseLock.notifyAll();
		}
	}
	
	public void die() {
		alive = false;
	}
	
	public View getView(){
		return layout;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void run() {
		while(alive){
			Log.i(App.TAG, "Run " + juncName +" "+ speed);
			
			Vehicle vehicle = new Vehicle(this);
			vehicle.start();
			
			synchronized (mPauseLock) {
                while (!enabled) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public double getStartLon() {
		return startLon;
	}
	
	public double getStartLat() {
		return startLat;
	}
	
	public double getEndLon() {
		return endLon;
	}
	
	public double getEndLat() {
		return endLat;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	public int getUnitTime() {
		return unitTime;
	}
	
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}
