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
 * Servlet implementation class getDoctor
 */
@WebServlet("/getDoctor")
public class getDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getDoctor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setContentType("application/json");
		ArrayList<doctor> DocList=null;
		PrintWriter out = response.getWriter();
		DBConnector db = new DBConnector();
		
		//retrieve data from the database.
    	try {
    		System.out.println("retrieving data from the database");
    		DocList = db.getAllDoctors();
    		
			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(DocList, new TypeToken<List<doctor>>() {}.getType());
			  JsonArray jsonArray = element.getAsJsonArray();
			  System.out.println("data recieved done!");
			 out.print(jsonArray);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
	}

	

}
