package com.analyzer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Application.DBConnector;

/**
 * Servlet implementation class PhoneApp
 */
@WebServlet("/PhoneApp")
public class PhoneApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhoneApp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		DBConnector db = new DBConnector();
		
		doctor d = new doctor();
		
		d.setDocID(request.getParameter("doctorID"));
		d.setName(request.getParameter("Name"));
		d.setPassword(request.getParameter("password"));
		d.setVisitingHours(request.getParameter("visitingHour"));
		d.setSpeciality(request.getParameter("speciality"));
		
		
		
		try{
		db.InserDataChira(d);
		
		}catch(Exception ff){
			
			out.print("doc :with eoor");
		}
		
		
		out.print("doc :"+d.getDocID());
		
		
		
	}

}
