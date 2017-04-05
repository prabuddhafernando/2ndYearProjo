package com.Application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.analyzer.location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class website
 */
@WebServlet("/website")
public class website extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public website() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ArrayList<location> LocationList=null;
		
    	DBConnector db = new DBConnector();
    	
    	
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
