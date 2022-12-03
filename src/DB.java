import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DB {
	private PreparedStatement ps = null;
	private Connection con = null;
	private int counter_prepared = 1;
	Zugangsdaten zugangsdaten = null;

	public DB(String ipAddress) {
		zugangsdaten = new Zugangsdaten();
		zugangsdaten.setIP(ipAddress);
		try {
			System.out.println("Connecting to database :" + ipAddress + ":3306");
			con = DriverManager.getConnection(zugangsdaten.getUrl(), zugangsdaten.getUser(),
					zugangsdaten.getPassword());

			System.out.println("Connection Successful...!");

			System.out.println("Erfolgreich mit DB verbunden.");

			// BSP Abfrage Kundentabelle
//			String query = "SELECT * FROM Kunde ORDER BY Kundennummer ASC";
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			
//			int columns = rs.getMetaData().getColumnCount();
//			for(int i = 1; i<columns; i++) 
//				System.out.print(String.format("%-30s", rs.getMetaData().getColumnLabel(i)));
//				
//				System.out.println();
//				System.out.println("----------------------------------------------------------");
//			
//				while(rs.next()) {
//					for(int i =1; i<columns; i++) {
//						System.out.print(String.format("%-30s", rs.getString(i)));
//					}
//					System.out.println();
//
//				}
//				
//				rs.close();
//				stmt.close();

		} catch (Exception e) {
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

//	// Was muessen wir eigentlich alles angeben? Nur die Werte der Buch Tabelle?
//	// Oder auch noch Autor, und dadurch Verlag..etc...
	
//	public void addBook(String isbn, String titel, String genre, String verlag, String jahr, String bestand) {
//		try {
//			ps = con.prepareStatement("INSERT INTO Zugeordnet_zu(Buch_ISBN, Genre_ID) VALUES (?,?)");
//			setString(isbn);
//			setInt(genre);
//			ps.executeUpdate();
//			ps = null;
//			counter_prepared = 1;
//			
//			ps = con.prepareStatement("INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
//					+ "VALUES (?, ?, ?, ?, ?, ?);");
//			setString(isbn);
//			setString(titel);
//			setString(genre);
//			setString(verlag);
//			setString(jahr);
//			setString(bestand);
//			ps.executeUpdate();
//			ps = null;
//			counter_prepared = 1;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


	public void addBook(String isbn, String titel, String genre, String verlag, String jahr, String bestand) {
		try {
			ps = con.prepareStatement("INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			ps.setString(1, isbn);
			ps.setString(2, titel);
			ps.setString(3, genre);
			ps.setString(4, verlag);
			ps.setString(5, jahr);
			ps.setString(6, bestand);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public ArrayList< LinkedHashMap<String,String>> ArrayListgetAutorBooks (String Autor) {
//		String sql1, sql2;
//		
//		sql1 = "SELECT ID FROM Autor WHERE Vorname ="+Autor;
//		sql2 = "SELECT Buch FROM Schreibt WHERE Autor IN("+sql1+"); ";
//		setSQL(sql1);
//		ArrayList<LinkedHashMap<String,String>> daten = lesenJava();
//		return daten;
//		
//		
//		
//	}
	
	// geht nicht weil Enum....
//		public void addGenre(String bezeichnung) {
//			try {
//				ps = con.prepareStatement("INSERT INTO Genre(ID, Bezeichnung)"
//						+ "VALUES (?,?);");
//				setInt(0);
//				setString(bezeichnung);
//				ResultSet rs = ps.executeQuery();
//				System.out.println(konvertiereJava(rs));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	 
	
	public void deleteBook(String titel) {
		try {
			ps = con.prepareStatement("DELETE FROM Buch WHERE titel=?;");

			setString(titel);
			System.out.println(ps);
			ps.executeUpdate();
			ps = null;
			counter_prepared = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}
