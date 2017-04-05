package com.analyzer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Application.DBConnector;

/**
 * Servlet implementation class saveUserDetails
 */
@WebServlet("/saveUserDetails")
public class saveUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveUserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = request.getParameter("username");
		String paswd = request.getParameter("password");
		String email= request.getParameter("email");
		
		
		
	DBConnector n = new DBConnector();
	
	
		try {
			n.InserUserDetails(name, paswd, email);
			response.getWriter().print("success! ");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().print("internel server error ");
		}
	
		
	
	}




}
