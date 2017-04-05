package com.example.mapact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.util.Base64;

public class HttpManager {
	

	public static String getData(String uri) {

		// method 1
		/*
		 * AndroidHttpClient client =
		 * AndroidHttpClient.newInstance("AndroidAgent"); HttpGet request = new
		 * HttpGet(url); HttpResponse response;
		 * 
		 * try { response = client.execute(request); return
		 * EntityUtils.toString(response.getEntity()); } catch (IOException e) {
		 * e.printStackTrace(); return null; }finally{ client.close(); }
		 */
		// method 2 best  request data using HttpConnection
		BufferedReader reader = null;

		try {
			URL url = new URL(uri);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}

	}

	// public static String getData(String uri, String userName, String
	// password) {
	//
	// // method 2 best
	// BufferedReader reader = null;
	// HttpURLConnection con = null ;
	// byte[] loginBytes = (userName + ":" + password).getBytes();
	// StringBuilder loginBuilder = new StringBuilder().append("Basic ")
	// .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));
	//
	// try {
	// URL url = new URL(uri);
	// con = (HttpURLConnection) url.openConnection();
	// //modified..
	// con.addRequestProperty("Authorization", loginBuilder.toString());
	//
	// StringBuilder sb = new StringBuilder();
	// reader = new BufferedReader(new InputStreamReader(
	// con.getInputStream()));
	//
	// String line;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	//
	// return sb.toString();
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// try {
	// int status = con.getResponseCode();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// return null;
	// } finally {
	// if (reader != null) {
	// try {
	// reader.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	// }
	//
	// }

}
