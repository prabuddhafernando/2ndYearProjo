package com.example.sender;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.comp.App;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DataTransmitter{

	private String serverurl;
	private Context context;

	public DataTransmitter(Context context,String url) {
		this.serverurl=url;
		this.context=context;
	}


	public void publishToServerOutOnly(String data) {
		publishToServerOutOnly("", data);
	}
	
	//non-blocking call to send data
	public void publishToServerOutOnly(String uri,String data) {
		publishToServerOutOnly(uri,data,false);
	}
	
	public void publishToServerOutOnly(String uri,String data,boolean stopSendingIfOneFails) {
		//execute tasks parallely
		new DataSenderTask(uri,data ,stopSendingIfOneFails).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
	}
	
	//blocking call to send data and get a response
	public JSONObject sendReceive(String uri,String data){

		String out ="";
		int responseCode=-1;
		URL url;
		try {
			
			url = new URL(serverurl + uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000 /* milliseconds */);
			conn.setConnectTimeout(5000 /* milliseconds */);

			conn.setRequestMethod("POST");
			
			//removed because request.getContent() in UES has an unusual behavior when content type is application/json
//			conn.setRequestProperty("Accept", "application/json");
//			conn.setRequestProperty("Content-Type", "application/json");

			conn.setDoOutput(true);
			conn.setDoInput(true);

			// Starts the query
			conn.connect();

			String payload = data;
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			
			responseCode = conn.getResponseCode();
	        
			InputStream response;
			
			if (responseCode >= 400) {
	            response = conn.getErrorStream();
	        } else {
	            response = conn.getInputStream();
	        }

	        if (response != null) {
	            StringBuilder sb = new StringBuilder();
	            byte[] bytes = new byte[1024];
	            int len;
	            while ((len = response.read(bytes)) != -1) {
	                sb.append(new String(bytes, 0, len));
	            }
	            if (!sb.toString().trim().isEmpty()) {
	                out = sb.toString();
	            }
	        }
	        
	        if(out.length()>300) out=out.substring(0, 299)+ "....";
	        
	        out="{'httpsc':"+responseCode+",'url':'"+serverurl + uri+"','response':'"+out.trim()+"'}";	        

		} catch (Exception e) {		
			out="{'httpsc':"+-1+",'url':'"+serverurl + uri+"','response':'Connection Failed'}";
		}
		
		try {
			JSONObject jsonObject = new JSONObject(out);
			return jsonObject;
		} catch (JSONException e1) {
			out="{'httpsc':"+responseCode+",'url':'"+serverurl + uri+"','response':'error building json response'}";
			try {
				JSONObject jsonObject = new JSONObject(out);
				return jsonObject;
			} catch (JSONException e2) {
				return null;
			}
		}
	}
	
	
	public void setServerURL(String url){
		serverurl=url;
	}

	private class DataSenderTask extends AsyncTask<String, Void, String> {

		private String data;
		private String uri;
		private boolean stopSendingIfOneFails;
		
		public DataSenderTask(String uri,String data, boolean stopSendingIfOneFails) {
			this.data = data;
			this.uri = uri;
			this.stopSendingIfOneFails = stopSendingIfOneFails;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				JSONObject response = sendReceive(uri,data);
				if(response != null){
					int responseCode = response.getInt("httpsc");
					if(responseCode==400 || responseCode==403){
						 Log.e(App.TAG,"Send data failed. Connect to IP:PORT success. But rest of the URL may be incorrect. Error : "+responseCode);
					}else if(responseCode >=200 && responseCode <300){
						 Log.i(App.TAG,"Success. Code : "+responseCode);
					}else if(responseCode==-1){
						 Log.e(App.TAG,"Send data failed. Probably IP:PORT is not correct");
					}else{
						 Log.e(App.TAG,"Send data failed. Connect to IP:PORT success. But rest of the URL may be incorrect. Code : "+responseCode);
					}
				}else{
					Log.e("TafficDemo","Send data failed. Probably IP:PORT is not correct");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
