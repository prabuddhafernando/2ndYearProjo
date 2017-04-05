package com.Application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import com.analyzer.MyCEPSocket;
import com.analyzer.location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Application
 */
@WebServlet("/Application")
public class Application extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Application() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		location l=  new location();  
		ArrayList<location> LocationList=null;
		
		
		// geting relevent parameters..
		l.setPhoneID((request.getParameter("phoneID")));
		l.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		l.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		l.setSpeed(Double.parseDouble(request.getParameter("speed")));

    	
    	DBConnector db = new DBConnector();
    	
    	// try to send the parameter to the CEP
    	try{
    	MyCEPSocket.CEP.getBasicRemote().sendText("<data:TrafficData  xmlns:data=\"http://prabu.com\">"+
				"<PhoneData>"+
				"<phoneID>"+l.getPhoneID()+"</phoneID>"+
				"<latitude>"+l.getLatitude()+"</latitude>"+
				"<longitude>"+l.getLongitude()+"</longitude>"+
                "<speed>"+l.getSpeed()+"</speed>"+
                "</PhoneData>"+
                "</data:TrafficData>");
    	}catch(Exception e){
    		
    		
    	}
    	
    	
    	
    	
    	
    	/*//push data to database
    	try{
    	System.out.println("Starting data insert");
    	db.InserData1(l);
    	System.out.println("data insert done!");
    	}catch(Exception ew){
    		System.out.println("Eoor");
    	}
    	*/
    	
    	
    	
    	//retrieve data from the database.
    	try {
    		System.out.println("retrieving data from the database");
			LocationList = db.getAllLocation();
			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(LocationList, new TypeToken<List<location>>() {}.getType());
			  JsonArray jsonArray = element.getAsJsonArray();
			  System.out.println("data recieved done!");
			 out.print(jsonArray);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		out.print("end!");
    	
	}

}
