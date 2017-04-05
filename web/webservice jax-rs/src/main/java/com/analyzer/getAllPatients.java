package com.analyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Application.DBConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class getAllPatients
 */
@WebServlet("/getAllPatients")
public class getAllPatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAllPatients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		response.setContentType("application/json");
		ArrayList<patient> PatientList=null;
		PrintWriter out = response.getWriter();
		 DBConnector db = new DBConnector();
		
		//retrieve data from the database.
    	try {
    		System.out.println("retrieving data from the database");
    		 PatientList = db.getAllPatients();
    		
			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(PatientList, new TypeToken<List<patient>>() {}.getType());
			  JsonArray jsonArray = element.getAsJsonArray();
			  System.out.println("data recieved done!");
			 out.print(jsonArray);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
