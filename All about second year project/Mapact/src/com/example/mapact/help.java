package com.example.mapact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

public class help extends ActionBarActivity{
	
	ImageView iv ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		iv = (ImageView) findViewById(R.id.helpImage);
		setContentView(R.layout.help);
		
	
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
	}
	
	

}
