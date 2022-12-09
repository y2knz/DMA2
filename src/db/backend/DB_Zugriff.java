package db.backend;
import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DB_Zugriff {
	private PreparedStatement ps = null;
	private Connection con = null;
	private int counter_prepared = 1;
	Zugangsdaten zugangsdaten = null;

	public DB_Zugriff(String ipAddress) {
		zugangsdaten = new Zugangsdaten();
		zugangsdaten.setIP(ipAddress);
		try {
			System.out.println("Connecting to database :" + ipAddress + ":3306");
			con = DriverManager.getConnection(zugangsdaten.getUrl(), zugangsdaten.getBenutzer(),
					zugangsdaten.getPasswort());

			System.out.println("Connection Successful...!");

			System.out.println("Erfolgreich mit DB verbunden.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Der DB_Zugang ist nicht vorhanden!");
		}
	}
	
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
//		counter_prepared = 1;
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
			for (int i = 1; i <= anz_spalten; i++) {
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '" + s + "': " + e.getMessage());
		}
	}

	public void setInt(String s) {
		try {
			setInt(Integer.parseInt(s));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '" + s + "': " + e.getMessage());
		}
	}

	public void setInt(int i) {
		try {
			ps.setInt(counter_prepared++, i);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("DB setString '" + i + "': " + e.getMessage());
		}
	}
	public PreparedStatement getPreparedStatement() {
		return ps;
	}
	
	public void setPreparedStatement(PreparedStatement ps) {
		this.ps = ps;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public int setCounter_Prepared(int i) {
		return this.counter_prepared = 1;
	}
	
}
