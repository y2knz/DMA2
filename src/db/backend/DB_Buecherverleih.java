package db.backend;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DB_Buecherverleih {
	
	public DB_Zugriff dbz;
	
	public DB_Buecherverleih(DB_Zugriff db) {
		this.dbz = db;
	}
	
	public void addBuch(String isbn, String titel, String genre, String verlag, String jahr, String bestand) {
		try {
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
					+ "VALUES (?, ?, ?, ?, ?, ?);"));
			dbz.setString(isbn);
			dbz.setString(titel);
			dbz.setString(genre);
			dbz.setString(verlag);
			dbz.setString(jahr);
			dbz.setString(bestand);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.getPreparedStatement().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBuch(String titel) {
		try {
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("DELETE FROM Buch WHERE titel=?;"));

			dbz.setString(titel);
			System.out.println(dbz.getPreparedStatement());
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getBuecherVonAutor(String s) {
		try {
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("SELECT Autor.Nachname, Autor.Vorname, Buch.Titel "
					+ "FROM Schreib "
					+ "JOIN Buch "
					+ "ON Buch.ISBN = Schreibt.Buch_ISBN " 
					+ "JOIN Autor "
					+ "ON Autor.ID = Schreibt.Autor_ID "
					+ "WHERE Autor.ID IN(SELECT ID FROM Autor WHERE Nachname=?);"));
			
			dbz.setString(s);
			System.out.println(dbz.getPreparedStatement());
			ResultSet rs = dbz.getPreparedStatement().executeQuery();
			System.out.println(dbz.konvertiereJava(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void addExemplar(String isbn, String signatur, String sprache, String auflage) {
		try {
			dbz.getCon().setAutoCommit(false);
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("INSERT INTO Exemplar(ISBN, Signatur, Sprache_ID, Auflage)"
					+ "VALUES (?, ?, ?, ?);"));
			dbz.setString(isbn);
			dbz.setString(signatur);
			dbz.setInt(sprache);
			dbz.setInt(auflage);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("UPDATE Buch "
					+ "SET Bestand =Bestand+1 "
					+ "WHERE ISBN=?"));
			dbz.setString(isbn);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			dbz.getCon().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteExemplar(String isbn, String signatur) {
		try {
			dbz.getCon().setAutoCommit(false);
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("DELETE FROM Exemplar "
					+ "WHERE ISBN=? "
					+ "AND Signatur=?;"));
			dbz.setString(isbn);
			dbz.setString(signatur);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("UPDATE Buch "
					+ "SET Bestand =Bestand-1 "
					+ "WHERE ISBN=?"));
			dbz.setString(isbn);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			dbz.getCon().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<LinkedHashMap<String, String>> getKundeEmail(int kundennr) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT* FROM Kunde WHERE Kundennummer=?";
		dbz.setSQL(sql);
		dbz.setInt(kundennr);
		
		liste = dbz.lesenJava();
		
		dbz.getPreparedStatement().close();
		dbz.setCounter_Prepared(1);
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return liste;
	}
	
	
	public void updateKundeEmail(String kundennr, String email) {
		try {
			dbz.setPreparedStatement(dbz.getCon().prepareStatement("UPDATE Kunde "
					+ "SET E_Mail=? "
					+ "WHERE Kundennummer=?"));
			dbz.setString(email);
			dbz.setInt(kundennr);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);

		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<LinkedHashMap<String, String>> getBuecherVonGenre(String genre) {
		
		ArrayList<LinkedHashMap<String, String>> liste=null;
		try {
			String sql = "SELECT * FROM Buch WHERE Genre_ID=(SELECT ID FROM Genre WHERE Bezeichnung=?);";
			
			dbz.setSQL(sql);
			dbz.setString(genre);
			liste = dbz.lesenJava();
			
			
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	public void getBuch (String titel) {
		try {
		String sql ="SELECT* FROM Buch WHERE Titel LIKE ?";
		
		dbz.setSQL(sql);
		dbz.setString(titel + "%");
		dbz.lesenJava();
		System.out.println(dbz.lesenJava());
		
		dbz.getPreparedStatement().close();
		dbz.setCounter_Prepared(1);
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
			
		}
	
	public ArrayList<LinkedHashMap<String, String>> getBuecher () {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT * FROM Buch";
		dbz.setSQL(sql);
		
		liste = dbz.lesenJava();
		
		dbz.getPreparedStatement().close();
		dbz.setCounter_Prepared(1);
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return liste;
	}

	public ArrayList<LinkedHashMap<String, String>> showExemplar(String isbn, String Signatur) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT* FROM Exemplar WHERE ISBN=? AND Signatur=?";
		
		dbz.setSQL(sql);
		dbz.setString(isbn);
		dbz.setString(Signatur);
		
		liste = dbz.lesenJava();
		
		dbz.getPreparedStatement().close();
		dbz.setCounter_Prepared(1);
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return liste;
		
	}
	
	public ArrayList<LinkedHashMap<String, String>> getGenre () {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
		String sql = "SELECT Bezeichnung FROM Genre";
		dbz.setSQL(sql);
		
		liste = dbz.lesenJava();
		
		dbz.getPreparedStatement().close();
		dbz.setCounter_Prepared(1);
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return liste;
	} 

	//Methode nur temporaer
	public void close() {
		dbz.close();
	}
}
