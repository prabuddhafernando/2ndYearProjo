package com.analyzer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sender
 */
@WebServlet("/sender")
public class sender extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		String name=request.getParameter("name");
		
		//response.setContentType("application/json");
		/*MyCEPSocket.CEP.getBasicRemote().sendText("<data:StudentDataPacket xmlns:data=\"http://supun.com\">"+
				  "<StudentData>"+		
				  "<Name>"+name+"</Name>"+
				  "</StudentData>"+
				  "</data:StudentDataPacket>");*/
		
		
		
		
		response.getWriter().print("Successfully send to the cep     "+name);
	}

}
