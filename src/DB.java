import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DB {
	private PreparedStatement ps = null;
	private Connection con = null;
	private int counter_prepared = 1;

	public DB(String ipAddress) {
		Zugangsdaten zugangsdaten = new Zugangsdaten(ipAddress);
		try{
			 System.out.println("Connecting to database :" +ipAddress + ":3306");
	            Connection conn =
	                    DriverManager.getConnection(zugangsdaten.getUrl(),zugangsdaten.getUser(),zugangsdaten.getPassword());

	            System.out.println("Connection Successful...!");


			System.out.println("Erfolgreich mit DB verbunden.");
			
			// BSP Abfrage Kundentabelle
			String query = "SELECT * FROM Kunde ORDER BY Kundennummer ASC";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int columns = rs.getMetaData().getColumnCount();
			for(int i = 1; i<columns; i++) 
				System.out.print(String.format("%-30s", rs.getMetaData().getColumnLabel(i)));
				
				System.out.println();
				System.out.println("----------------------------------------------------------");
			
				while(rs.next()) {
					for(int i =1; i<columns; i++) {
						System.out.print(String.format("%-30s", rs.getString(i)));
					}
					System.out.println();

				}
				
				rs.close();
				stmt.close();
			
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

	public void close() {
		finalize();
	}

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
	
	public void setString(String s) {
		try {
			ps.setString(counter_prepared++, s);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '"+ s + "': " + e.getMessage());
		}
	}
	
	public void setInt(String s) {
		try {
			setInt(Integer.parseInt(s));
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '"+ s + "': " + e.getMessage());
		}
	}
	
	public void setInt(int i) {
		try {
			ps.setInt(counter_prepared++, i);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '"+ i + "': " + e.getMessage());
		}
	}
}
