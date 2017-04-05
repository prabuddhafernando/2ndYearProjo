package com.example.vehiclesim;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.comp.App;
import com.example.comp.Junction;
import com.example.sender.VehicleDataTransmitter;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class VehicleSimMainActivity extends Activity {

	private LinearLayout juncListLayout;
	private EditText txtServerUrl;
	private VehicleDataTransmitter vehicleDataTransmitter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicle_sim_main);
		
				
		//to enable testing a network request on main thread
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		juncListLayout = (LinearLayout)findViewById(R.id.junctionList);
		txtServerUrl = (EditText)findViewById(R.id.txtServerUrl);
		
		juncListLayout.requestFocus();
		
		vehicleDataTransmitter = new VehicleDataTransmitter(this, txtServerUrl.getText().toString());
		App.getInstance().setVehicleDataTransmitter(vehicleDataTransmitter);		
		
		// ------ Add junctions here -----
		
		addJunction("Bambalapitiya",79.85493064,6.89526235 ,79.85799909 ,6.88561223);
		addJunction("Kollupitiya",79.84965205,6.91119634,79.8514545 ,6.90563654);
		//addJunction("Kollupitiya",79.8514545,6.90563654 ,79.84965205 ,6.91119634);
		addJunction("Dehiwala",79.8514545,6.90563654 ,79.84965205 ,6.91119634);
		
		// -------------------------------
		
	}
	
	public void addJunction(String name,double startLon, double startLat,double endLon,double endLat){
		Junction junction=new Junction(this,name,startLon,startLat ,endLon ,endLat);
		juncListLayout.addView(junction.getView());
		junction.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vehicle_sim_main, menu);
		return true;
	}
	
	public void btnApplyAndTestOnClick(View view){		
		vehicleDataTransmitter.setServerURL(txtServerUrl.getText().toString());
		JSONObject response = vehicleDataTransmitter.testConnection();
		showResponseDialog(response);
	}
	
	private void showResponseDialog(JSONObject response){
		try {
			int responsecode = response.getInt("httpsc");
			String titleStatus, message;
			if(responsecode>=200 && responsecode<300){
				message ="";
				titleStatus = "Success";
			}else if(responsecode==-1){
				message = "Send data failed. Probably IP:PORT is not correct";
				titleStatus = "Failed";
			}else{
				message ="Send data failed. Connect to IP:PORT success. But rest of the URL may be incorrect."; 
				titleStatus = "Failed";
			}
			
			new AlertDialog.Builder(this)
		    .setTitle(responsecode+" " + titleStatus)
		    .setMessage(message + "\n" +
		    		"Response: " +response.getString("response") +"\n" +
		    		"Server URL: " + response.getString("url") )
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_info)
		     .show();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
