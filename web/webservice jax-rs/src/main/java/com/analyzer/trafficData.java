package com.analyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.XMLObject;

import org.json.JSONObject;

/**
 * Servlet implementation class trafficData
 */

@WebServlet(description = "none", urlPatterns = { "/trafficData" })
public class trafficData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// response.setContentType("text/html");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		location l = new location();
		boolean t = true;
		
	//	Date date = new Date();
	//	int Current_hour = date.getHours();
	//	int Current_minute= date.getHours();
	//	int Current_seconds= date.getSeconds();
		
		
	   // System.out.println(   "Hours :"+date.getHours() +"\nMinites :" + date.getMinutes() +"\nSeconds :" + date.getSeconds());  
	    					
		l.setPhoneID(request.getParameter("phoneID"));
		l.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		l.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		l.setSpeed(Double.parseDouble(request.getParameter("speed")));
		
		//creating json object to send android application ... 
		try {
			obj.put("phoneID", l.getPhoneID());
			obj.put("latitude", +l.getLatitude());
			obj.put("longitude", l.getLongitude());
			obj.put("speed", l.getSpeed());
		} catch (Exception e) {
			out.print("error occoured");
			e.printStackTrace();
		}
		
		
		// ADD time ^^^^^ here
		try {
			MyCEPSocket.CEP.getBasicRemote().sendText(
					"<data:TrafficData  xmlns:data=\"http://prabu.com\">"
							+ "<PhoneData>" + "<phoneID>" + l.getPhoneID()
							+ "</phoneID>" + "<latitude>" + l.getLatitude()
							+ "</latitude>" + "<longitude>" + l.getLongitude()
							+ "</longitude>" + "<speed>" + l.getSpeed()
							+ "</speed>" + "</PhoneData>"
							+ "</data:TrafficData>");
		} catch (Exception e) {
			t = false;

		}
		
		if (t == false) {
			out.print(obj+"\nfailed to send data to cep!!");
			out.flush();

		} else {
			out.print(obj);
			out.flush();
		}
		

	}

}
