package db.backend;
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

	public void addBook(String isbn, String titel, String genre, String verlag, String jahr, String bestand) {
		try {
			ps = con.prepareStatement("INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
					+ "VALUES (?, ?, ?, ?, ?, ?);");
			setString(isbn);
			setString(titel);
			setString(genre);
			setString(verlag);
			setString(jahr);
			setString(bestand);
			ps.executeUpdate();
			ps.close();
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBook(String titel) {
		try {
			ps = con.prepareStatement("DELETE FROM Buch WHERE titel=?;");

			setString(titel);
			System.out.println(ps);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getBooksFromAutor(String s) {
		try {
			ps = con.prepareStatement("SELECT Autor.Nachname, Autor.Vorname, Buch.Titel "
					+ "FROM Schreib "
					+ "JOIN Buch "
					+ "ON Buch.ISBN = Schreibt.Buch_ISBN "
					+ "JOIN Autor "
					+ "ON Autor.ID = Schreibt.Autor_ID "
					+ "WHERE Autor.ID IN(SELECT ID FROM Autor WHERE Nachname=?);");
			
			setString(s);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			System.out.println(konvertiereJava(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void addExemplar(String isbn, String signatur, String sprache, String auflage) {
		try {
			ps = con.prepareStatement("INSERT INTO Exemplar(ISBN, Signatur, Sprache_ID, Auflage)"
					+ "VALUES (?, ?, ?, ?);");
			setString(isbn);
			setString(signatur);
			setInt(sprache);
			setInt(auflage);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;
			
			ps = con.prepareStatement("UPDATE Buch "
					+ "SET Bestand =Bestand+1 "
					+ "WHERE ISBN=?");
			setString(isbn);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteExemplar(String isbn, String signatur) {
		try {
			ps = con.prepareStatement("DELETE FROM Exemplar "
					+ "WHERE ISBN=? "
					+ "AND Signatur=?;");
			setString(isbn);
			setString(signatur);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;
			
			ps = con.prepareStatement("UPDATE Buch "
					+ "SET Bestand =Bestand-1 "
					+ "WHERE ISBN=?");
			setString(isbn);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<LinkedHashMap<String, String>> getKundeEmail(int kundennr) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT* FROM Kunde WHERE Kundennummer=?";
		setSQL(sql);
		setInt(kundennr);
		
		liste = lesenJava();
		
		ps.close();
		counter_prepared = 1;
		
	
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		return liste;
	}
	
	
	public void updateKundeEmail(String kundennr, String email) {
		try {
			ps = con.prepareStatement("UPDATE Kunde "
					+ "SET E_Mail=? "
					+ "WHERE Kundennummer=?");
			setString(email);
			setInt(kundennr);
			ps.executeUpdate();
			ps.close();
			counter_prepared = 1;

		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<LinkedHashMap<String, String>> getBooksFromGenre(String genre) {
		
		ArrayList<LinkedHashMap<String, String>> liste=null;
		try {
			String sql = "SELECT * FROM Buch WHERE Genre_ID=(SELECT ID FROM Genre WHERE Bezeichnung=?);";
			
			setSQL(sql);
			setString(genre);
			liste = lesenJava();
			
			
			ps.close();
			counter_prepared = 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
				
//		//setSQL();
//		ArrayList<LinkedHashMap<String,String>> daten = lesenJava();
//		for(LinkedHashMap<String,String>datensatz:daten) {
//			System.out.println(datensatz);
//		}
	}
	
	public void getBook (String titel) {
		//wildcard
		try {
		String sql ="SELECT* FROM Buch WHERE Titel LIKE ?";
		
		setSQL(sql);
		setString(titel + "%");
		lesenJava();
		System.out.println(lesenJava());
		
		ps.close();
		counter_prepared = 1;
		
	
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		
			
		}
	
	public ArrayList<LinkedHashMap<String, String>> getBooks () {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT ISBN FROM Buch";
		setSQL(sql);
		
		liste = lesenJava();
		
		ps.close();
		counter_prepared = 1;
		
	
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		return liste;
	}

	public ArrayList<LinkedHashMap<String, String>> showExemplar(String isbn, String Signatur) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT* FROM Exemplar WHERE ISBN=? AND Signatur=?";
		
		setSQL(sql);
		setString(isbn);
		setString(Signatur);
		
		liste = lesenJava();
		
		ps.close();
		counter_prepared = 1;
		
	
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		return liste;
		
	}
	
}
