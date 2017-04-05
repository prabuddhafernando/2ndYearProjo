package com.analyzer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.Application.DBConnector;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	DBConnector db = new DBConnector();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] a=null;
		String retievingInfo=null;
		
		 try{
	            db.InserDataTest("opened testing");
	            a= db.getXMLdata();
	           
		 
		 }catch(Exception e){
	            	
	            	db.InserDataTest(e.toString());
	           }finally{
	            	
	           }
		 
	        
	       StringBuilder builder = new StringBuilder();
	        for(String s : a) {
	            builder.append(s+"\n");
	        }
	           retievingInfo = builder.toString();
	        System.out.println(retievingInfo);
		 
		 
		
		response.getWriter().print("Successfully send to the cep     "+retievingInfo);
	}

}
