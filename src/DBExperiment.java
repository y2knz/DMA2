import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class DBExperiment {
	private PreparedStatement ps = null;
	private Connection con = null;
	private int counter_prepared = 1;
	
	private String ipAddress = "192.168.0.206:3306/buecherverleih";

	public DBExperiment() {
		//IP Adresse an die der VM anpassen
		String url = "jdbc:mysql://192.168.2.111:3306/buecherverleih";
		String user = "root";
		String password = "root";
		try{
			 System.out.println("Connecting to database :" +ipAddress);
	           // Connection myConn =
	                 //   DriverManager.getConnection(url,user,password);
	            
	            con = DriverManager.getConnection(url, user,password);

	            System.out.println("Connection Successful...!");


			System.out.println("Erfolgreich mit DB verbunden.");
			if( con == null) {
				System.out.println("Die Connection ist null");
			}else {
				System.out.println("Die Connection ist nicht null");
			}
			
		
			
					
			// BSP Abfrage Kundentabelle
	
				
		
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Der DB_Zugang ist nicht vorhanden!");
		}
		
		
	}

	
	
//	public DB(String db, String user, String pass) {
//			try {
//				Class.forName("com.mysql.jdbc.Driver").newInstance();
//				String kommando =
//						"jdbc:mysql://localhost/" + Zugangsdaten.db +
//						"?user=" + Zugangsdaten.user +
//						"&password=" Zugangsdaten.pass;
//				con = DriverManager.getConnection(kommando);
//			} catch(Exception e) {
//				e.printStackTrace();
//				throw new RuntimeException("Der DB_Zugang ist nicht vorhanden!");
//			}
//		}


	

	@Override
	public void finalize() {
		try {
			ps.close();
		} catch (SQLException e) {
		}
		ps = null;
		try {
			con.close();
		} catch (SQLException e) {
		}
		con = null;
	}

	public void setSQL(String sql) {
		if(con == null) {
			System.out.println();
			System.out.println("Die connection ist null");
			
		}
		try {
			ps = con.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL-Fehler: " + e.getMessage());
			
		}
	}

	public ArrayList<LinkedHashMap<String, String>> lesenJava() {
		try {
			return konvertiereJava(ps.executeQuery());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB lesenJava: " + e.getMessage());
		}
	}

	public ArrayList<LinkedHashMap<String, String>> konvertiereJava(ResultSet rs) throws SQLException {

		ArrayList<LinkedHashMap<String, String>> daten = new ArrayList<>();
		int anz_spalten = rs.getMetaData().getColumnCount();
		if (anz_spalten == 0)
			return daten;
		while (rs.next()) {
			LinkedHashMap<String, String> datensatz = new LinkedHashMap<>();
			for (int i = 1; i < anz_spalten; i++) {
				String name = rs.getMetaData().getColumnName(i);
				String wert = rs.getString(name);
				if (wert != null)
					datensatz.put(name, wert);
				else
					datensatz.put(name, "");
			}
			daten.add(datensatz);
		}
		return daten;
	}
	
	
}
