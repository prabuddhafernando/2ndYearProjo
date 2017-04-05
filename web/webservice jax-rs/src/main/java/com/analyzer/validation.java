package com.analyzer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Application.DBConnector;

/**
 * Servlet implementation class validation
 */
@WebServlet("/validation")
public class validation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	DBConnector db = new DBConnector();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public validation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("username");
		String password=request.getParameter("password");
		
		int result =5;
		try {
			result = db.validate(name, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		
		
		response.getWriter().print(result);
		
	}


}
