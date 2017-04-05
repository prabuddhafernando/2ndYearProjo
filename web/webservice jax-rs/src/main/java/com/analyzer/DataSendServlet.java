package com.analyzer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DataSendServlet
 */
@WebServlet("/datasendservlet")
public class DataSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		
		try{
	//	String name=request.getParameter("name");
			

			String phoneID=request.getParameter("phoneID");
		double latitude=Double.parseDouble(request.getParameter("latitude"));
		double longitude=Double.parseDouble(request.getParameter("longitude"));
		double speed=Double.parseDouble(request.getParameter("speed"));
		
		
		MyCEPSocket.CEP.getBasicRemote().sendText("<data:TrafficData  xmlns:data=\"http://prabu.com\">"+
				"<PhoneData>"+
				"<phoneID>"+phoneID+"</phoneID>"+
				"<latitude>"+latitude+"</latitude>"+
				"<longitude>"+longitude+"</longitude>"+
                "<speed>"+speed+"</speed>"+
                "</PhoneData>"+
                "</data:TrafficData>");
		/*MyCEPSocket.CEP.getBasicRemote().sendText("<data:StudentDataPacket xmlns:data=\"http://supun.com\">"+
				  "<StudentData>"+						
				  "<Name>"+name+"</Name>"+
				  "</StudentData>"+
				  "</data:StudentDataPacket>");*/
		
		response.getWriter().print("Success     ");
		
		}catch(Exception e){
			response.getWriter().print("Success     error"+e.toString());
			
		}
		/*
		MyCEPSocket.CEP.getBasicRemote().sendText("<data:StudentDataPacket xmlns:data=\"http://supun.com\">"+
												  "<StudentData>"+						
												  "<Name>"+name+"</Name>"+
												  "</StudentData>"+
				  								  "</data:StudentDataPacket>");
		*/
		
			
		
		
		
		
	}

}
