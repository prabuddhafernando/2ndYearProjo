package com.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.BreakIterator;
import java.util.ArrayList;

import com.analyzer.doctor;
import com.analyzer.location;
import com.analyzer.patient;
import com.mysql.jdbc.PreparedStatement;

public class DBConnector {

	public void InserDataChira(patient ha) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO patient(id,name,address,password) VALUES(?,?,?,?)");

			pst.setString(1, ha.getId());
			pst.setString(2, ha.getName());
			pst.setString(3, ha.getAddress());
			pst.setString(4, ha.getPassword());

			System.out.print("Befor");
			pst.executeUpdate();

		} catch (Exception s) {

			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	// =========================
	public void InserDataChira(doctor l) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO doctor(doctorID,name,password,speciality,visitinghours) VALUES(?,?,?,?,?)");

			pst.setString(1, l.getDocID());
			pst.setString(2, l.getName());
			pst.setString(3, l.getPassword());
			pst.setString(4, l.getSpeciality());
			pst.setString(5, l.getVisitingHours());

			System.out.print("Befor");
			pst.executeUpdate();

		} catch (Exception s) {

			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	// not formal method... here
	public void InserData(location l) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");
			// String name = "sadun";
			// int phone = 354444;

			// String insert = "INSERT INTO datatable VALUES('" + name + "',"
			// + phone + ")";

			String insert = "INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES('"
					+ l.getPhoneID()
					+ "',"
					+ l.getLatitude()
					+ ","
					+ l.getLongitude() + "," + l.getSpeed() + ")";

			stat.executeUpdate(insert);

		} catch (Exception s) {
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	public void InserDataFromCEP(String phnID, double lat, double longi,
			double spd) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES(?,?,?,?)");
			pst.setString(1, phnID);
			pst.setDouble(2, lat);
			pst.setDouble(3, longi);
			pst.setDouble(4, spd);

			pst.executeUpdate();
			/*
			 * PreparedStatement pst2 = (PreparedStatement) con
			 * .prepareStatement("DELETE FROM `trafficdata`");
			 * 
			 * pst2.executeUpdate();
			 */

			// ....("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			InserDataTest("Message :insert dataFrom cep Exception" + s);
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	public void InserDataFromCEP(location l) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			// PreparedStatement pst = (PreparedStatement) con
			// .prepareStatement("DELETE * trafficdata(phoneID,latitude,longitude,speed) VALUES(?,?,?,?)");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES(?,?,?,?)");
			pst.setString(1, l.getPhoneID());
			pst.setDouble(2, l.getLatitude());
			pst.setDouble(3, l.getLongitude());
			pst.setDouble(4, l.getSpeed());
			// String insert =
			// "INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES('"
			// + l.getPhoneID() +
			// "',"+l.getLatitude()+","+l.getLongitude()+","+l.getSpeed()+")";

			pst.executeUpdate();

			System.out.print("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			InserDataTest("Message :insert dataFrom cep Exception" + s);
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	public void InserUserDetails(String name, String paswd, String email) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO users(username,password,email) VALUES(?,?,?)");
			pst.setString(1, name);
			pst.setString(2, paswd);
			pst.setString(3, email);

			pst.executeUpdate();

			System.out.print("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	public void InserData1(location l) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES(?,?,?,?)");
			pst.setString(1, l.getPhoneID());
			pst.setDouble(2, l.getLatitude());
			pst.setDouble(3, l.getLongitude());
			pst.setDouble(4, l.getSpeed());
			// String insert =
			// "INSERT INTO trafficdata(phoneID,latitude,longitude,speed) VALUES('"
			// + l.getPhoneID() +
			// "',"+l.getLatitude()+","+l.getLongitude()+","+l.getSpeed()+")";

			pst.executeUpdate();

			System.out.print("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	public void InserDataTest(String name) {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("INSERT INTO gta(name) VALUES(?)");
			pst.setString(1, name);

			pst.executeUpdate();

			System.out.print("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}
////////////////////////
	public void EmptyDatabase() {

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			System.out.println("point 1");
			// Statement stat = (Statement) con.createStatement();
			System.out.println("point 2");

			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("delete from trafficdata");
		

			pst.executeUpdate();

			System.out.print("EXECUTE QUERY DONE!");

		} catch (Exception s) {
			System.out.println("eoor2 :" + s);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.print("connection cannot close");
			}
		}

	}

	

	
///////////	
	public static String[] getXMLdata() {

		String[] XML = null;

		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select name from gta");

			int i = 0;
			while (rs.next()) {

				XML[i] = rs.getString("name");
				i++;

				// countryList.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return XML;

	}
//validation of username and password
	public int validate(String name, String password) {

		

		Connection con = null;
		try {
			int i = 9;
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");
			
			Statement statement = con.createStatement();
			//SELECT username,password FROM `users` WHERE username='pop' AND password='dhd'
			
			//ResultSet rs = statement.executeQuery("select username,password from users where username='"+name+"'");
			ResultSet rs = statement.executeQuery("SELECT username,password FROM `users` WHERE username='"+name+"' AND password='"+password+"'");
			
			// ResultSet rs =
			// statement.executeQuery("select * from trafficdata");
			
			if ( rs.next() == true) {
				
				
				
				
				/*while (rs.next()) {

					if (name.equals(rs.getString("username"))
							&& (password.equals(rs.getString("passoword")))) {

						i = i * 0;

					} else {

						i = i * 1;
					}

				}*/
					// o  if null
					return 0;
				
			}else{
				// 1 if not nul
				return 1;
			}
			
			// return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 2;

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//if cannot ditect
		//return 2;

	}

	// retrieve data Doctors Lists/.....
	public static ArrayList<patient> getAllPatients() {

		ArrayList<patient> PatientList = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			PatientList = new ArrayList<patient>();

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select * from patient");
			// ResultSet rs =
			// statement.executeQuery("select * from trafficdata");

			while (rs.next()) {
				patient p = new patient();
				// l.setPhoneID(rs.getString("phoneID"));
				// l.setLatitude(rs.getDouble("latitude"));
				// l.setLongitude(rs.getDouble("longitude"));
				// l.setSpeed(rs.getDouble("speed"));
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setAddress(rs.getString("address"));
				p.setAddress(rs.getString("password"));

				PatientList.add(p);
				// LocationList.add(l);

				// countryList.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return PatientList;
	}

	// retrieve data Doctors Lists/.....
	public static ArrayList<doctor> getAllDoctors() {

		ArrayList<doctor> DocList = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			DocList = new ArrayList<doctor>();

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select * from doctor");
			// ResultSet rs =
			// statement.executeQuery("select * from trafficdata");

			while (rs.next()) {
				doctor d = new doctor();
				d.setDocID(rs.getString("doctorID"));
				d.setName(rs.getString("name"));
				d.setPassword(rs.getString("password"));
				d.setSpeciality(rs.getString("speciality"));
				d.setVisitingHours(rs.getString("visitinghours"));

				DocList.add(d);
				// LocationList.add(l);

				// countryList.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return DocList;
	}

	// retrieve data location Lists/.....
	public static ArrayList<location> getAllLocation() {

		ArrayList<location> LocationList = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://127.4.59.130:3306/marine", "admin9dWq6Uu",
					"tUCqjC2dQVIE");

			LocationList = new ArrayList<location>();

			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("select DISTINCT latitude,phoneID,longitude,speed from trafficdata");
			// ResultSet rs =
			// statement.executeQuery("select * from trafficdata");

			while (rs.next()) {
				location l = new location();
				l.setPhoneID(rs.getString("phoneID"));
				l.setLatitude(rs.getDouble("latitude"));
				l.setLongitude(rs.getDouble("longitude"));
				l.setSpeed(rs.getDouble("speed"));

				LocationList.add(l);

				// countryList.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return LocationList;
	}
	
	
	

}
