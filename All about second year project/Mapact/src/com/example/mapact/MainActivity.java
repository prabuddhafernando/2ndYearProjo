package com.example.mapact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity  extends ActionBarActivity{
	GPSTracker gs;
	Button btnContinue, aboutUs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_frame);
		btnContinue = (Button) findViewById(R.id.btncontinue);
		aboutUs = (Button) findViewById(R.id.btnaboutus);
		
		btnContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gs = new GPSTracker(MainActivity.this);
				if (gs.canGetLocation) {
					
					Intent i = new Intent(MainActivity.this,LoginInterface.class);
					startActivity(i);
					
					
				}else{
					
					gs.showSettingsAlert();
				}
				
				
				
			}
		});
		
		aboutUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setContentView(R.layout.about_app);
				
			}
		});
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	super.onBackPressed();
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("EXIT")
        .setIcon(R.drawable.warning_2)
        .setMessage("Are you sure you want to Exit?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        })
        .setNegativeButton("No", null)
        .show();
	}
	
	
	
	
}
