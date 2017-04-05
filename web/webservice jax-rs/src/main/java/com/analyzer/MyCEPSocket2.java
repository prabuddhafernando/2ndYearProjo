package com.analyzer;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.websocket.OnError;
import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
////////////////////////////


import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;



import com.Application.DBConnector;


/**
 * @ServerEndpoint gives the relative name for the end point This will be
 *                 accessed via ws://localhost:8080/EchoChamber/echo Where
 *                 "localhost" is the address of the host, "EchoChamber" is the
 *                 name of the package and "echo" is the address to access this
 *                 class from the server
 */

@ServerEndpoint("/get")
public class MyCEPSocket2 {

	ArrayList<location> LocationList = null;
	location loc;

	double longi = 0;
	double latit = 0;
	double speed = 0;
	String phnID = null;
	/**
	 * @OnOpen allows us to intercept the creation of a new session. The session
	 *         class allows us to send data to the user. In the method onOpen,
	 *         we'll let the user know that the handshake was successful.
	 */

	DBConnector db = new DBConnector();
	private boolean connected = false;
	public static Session CEP = null;

	@OnOpen
	public void onOpen(Session session) {

		connected = true;

		CEP = session;
		try {
			db.InserDataTest("connection opened");
		} catch (Exception e) {

			db.InserDataTest(e.toString());
		} finally {

		}
		// Data data=new Data();

	}

	/**
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a
	 * String.
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		// System.out.println("Message from CEP session =" + CEP.getId() + ": "
		// + message);
//
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	
		try {

			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				// Document doc = builder.parse("fas.xml");
				// Document doc = builder.parse(uri);
				Document doc = builder.parse(new InputSource(new StringReader(message)));
				NodeList personList = doc.getElementsByTagName("payloadData");
				
				for (int i = 0; i < personList.getLength(); i++) {
					Node p = personList.item(i);
					if (p.getNodeType() == Node.ELEMENT_NODE) {
						Element person = (Element) p;
						String id = person.getAttribute("id");
						NodeList nameList = person.getChildNodes();
						for (int j = 0; j < nameList.getLength(); j++) {
							Node n = nameList.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element name = (Element) n;

								
								
								switch (name.getTagName()) {
//								case "phoneID":
//									phnID = name.getTextContent();
//									//loc.setPhoneID(phnID);
//									// Se("speed"+speed);
//									break;
								case "latitude":
									latit = Double.parseDouble(name
											.getTextContent());
									//loc.setLatitude(latit);
									// Se("lat "+latit);
									break;
								case "longitude":
									longi = Double.parseDouble(name
											.getTextContent());
									//loc.setLongitude(longi);

									// Se("longi"+longi);
									break;
								case "speed":   //case "avgSpeed":
									speed = Double.parseDouble(name
											.getTextContent());
									//loc.setSpeed(speed);
									// Se("speed"+speed);
									break;
								default:
									break;
								}

							}
						}
						//work for any array of xml
						phnID= "CEP00000";
						
						
						// Empty database before insert a tuple
						// !!!!!!!!!!!!!!!!! uncoment when needed..
						//     db.EmptyDatabase();
						
						
						db.InserDataFromCEP(phnID,latit,longi,speed);
					}
				}
				//db.InserDataFromCEP(phnID,latit,longi,speed);
				//db.InserDataFromCEP(loc);
			
			//	db.InserDataTest("detail" + phnID+ "  ,"+latit+" ,"+longi+" sped:"+speed);

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				db.InserDataTest("Message :on ParserConfigurationException");
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				db.InserDataTest("Message :on SAXException");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				db.InserDataTest("Message :on IOException");
			}

			//
			//db.InserDataTest("Message :" + message.toString()+")))))))))))))))))))))))))))))))");

			// db.InserData1(loc);

		} catch (Exception e) {
			db.InserDataTest(e.toString()+"outer");
		} finally {

		}

	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " has ended");
		connected = false;
		CEP = null;
	}

	public void OnError(Session s) {
		try {
			db.InserDataTest("erorr");
		} catch (Exception e) {

			db.InserDataTest(e.toString());
		} finally {

		}

	}


}