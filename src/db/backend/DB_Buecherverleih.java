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

//	Verlag, Genre(dropdown) nicht als ID eingeben, Autor Vorname feld soll optional sein, 
//	falls nichts eingegeben wird, einfahc leeren String übergeben
	public void addBuch(String isbn, String titel, String autorNachname, String autorVorname, String genre, String verlag, String jahr, String bestand) {
		String verlagID = "";
		String autorID = "";
		String genreID = getGenreID(genre);
		if (isbnVorhanden(isbn)) {
			System.out.println("Buch bereits vorhanden");
		} else {
			if(!verlagVorhanden(verlag)) {
				verlagID = getVerlagID(verlag);
			} else {
				addVerlag(verlag);
				verlagID = getVerlagID(verlag);
			}
			
			if(!autorVorhanden(autorNachname)) {
				autorID = getAutorID(autorNachname);
			} else {
				addAutor(autorNachname, autorVorname);
				autorID = getAutorID(autorNachname);
			}
		
			try {
				dbz.setPreparedStatement(dbz.getCon().prepareStatement(
						"INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
								+ "VALUES (?, ?, " + genreID + ", " + verlagID + ", ?, ?);"));
				dbz.setString(isbn);
				dbz.setString(titel);
				dbz.setString(jahr);
				dbz.setString(bestand);
				dbz.getPreparedStatement().executeUpdate();
				dbz.getPreparedStatement().close();
				dbz.setCounter_Prepared(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addBuch(String isbn, String titel, String autorNachname, String autorVorname, String autorNachname2, String autorVorname2,  String genre, String verlag, String jahr, String bestand) {
		String verlagID = "";
		String autorID = "";
		String autorID2 = "";
		String genreID = getGenreID(genre);
		if (isbnVorhanden(isbn)) {
			System.out.println("Buch bereits vorhanden");
		} else {
			if(!verlagVorhanden(verlag)) {
				verlagID = getVerlagID(verlag);
			} else {
				addVerlag(verlag);
				verlagID = getVerlagID(verlag);
			}
			
			if(!autorVorhanden(autorNachname)) {
				autorID = getAutorID(autorNachname);
			} else {
				addAutor(autorNachname, autorVorname);
				autorID = getAutorID(autorNachname);
			}
			
			if(!autorVorhanden(autorNachname2)) {
				autorID2 = getAutorID(autorNachname2);
			} else {
				addAutor(autorNachname, autorVorname2);
				autorID2 = getAutorID(autorNachname2);
			}
		
			try {
				dbz.setPreparedStatement(dbz.getCon().prepareStatement(
						"INSERT INTO Buch(ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand)"
								+ "VALUES (?, ?, " + genreID + ", " + verlagID + ", ?, ?);"));
				dbz.setString(isbn);
				dbz.setString(titel);
				dbz.setString(jahr);
				dbz.setString(bestand);
				dbz.getPreparedStatement().executeUpdate();
				dbz.getPreparedStatement().close();
				dbz.setCounter_Prepared(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	public ArrayList<LinkedHashMap<String, String>> getBuecherVonAutor(String s) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
			dbz.setPreparedStatement(dbz.getCon()
					.prepareStatement("SELECT Autor.Nachname, Autor.Vorname, Buch.Titel " 
							+ "FROM Schreibt "
							+ "JOIN Buch " 
							+ "ON Buch.ISBN = Schreibt.Buch_ISBN " 
							+ "JOIN Autor "
							+ "ON Autor.ID = Schreibt.Autor_ID "
							+ "WHERE Autor.ID IN(SELECT ID FROM Autor WHERE Nachname LIKE ?);"));
			dbz.setString(s + "%");
			dbz.getPreparedStatement().executeQuery();
			liste = dbz.lesenJava();
			System.out.println(liste);
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public void addExemplar(String isbn, String signatur, String sprache, String auflage) {
		try {
			dbz.getCon().setAutoCommit(false);
			dbz.setPreparedStatement(dbz.getCon().prepareStatement(
					"INSERT INTO Exemplar(ISBN, Signatur, Sprache_ID, Auflage)" 
							+ "VALUES (?, ?, ?, ?);"));
			dbz.setString(isbn);
			dbz.setString(signatur);
			dbz.setInt(sprache);
			dbz.setInt(auflage);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);

			dbz.setPreparedStatement(
					dbz.getCon().prepareStatement("UPDATE Buch " + "SET Bestand =Bestand+1 " + "WHERE ISBN=?"));
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
			dbz.setPreparedStatement(
					dbz.getCon().prepareStatement("DELETE FROM Exemplar " 
							+ "WHERE ISBN=? " 
							+ "AND Signatur=?;"));
			dbz.setString(isbn);
			dbz.setString(signatur);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);

			dbz.setPreparedStatement(
					dbz.getCon().prepareStatement("UPDATE Buch " 
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
			dbz.setPreparedStatement(
					dbz.getCon().prepareStatement("UPDATE Kunde " 
							+ "SET E_Mail=? " 
							+ "WHERE Kundennummer=?"));
			dbz.setString(email);
			dbz.setInt(kundennr);
			dbz.getPreparedStatement().executeUpdate();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LinkedHashMap<String, String>> getBuecherVonGenre(String genre) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
			String sql = "SELECT Buch.ISBN, Buch.Titel, CONCAT(Autor.Nachname, ', ', Autor.Vorname) AS Autor, Genre.Bezeichnung, Verlag.Name, Buch.Erscheinungsjahr, Buch.Bestand "
					+ "FROM Schreibt " 
					+ "JOIN Buch " 
					+ "ON Buch.ISBN = Schreibt.Buch_ISBN " 
					+ "JOIN Autor "
					+ "ON Autor.ID = Schreibt.Autor_ID " 
					+ "JOIN Genre " 
					+ "ON Genre.ID = Buch.Genre_ID "
					+ "JOIN Verlag " 
					+ "ON Buch.Verlag_ID = Verlag.ID "
					+ "WHERE Buch.Genre_ID=(SELECT ID FROM Genre WHERE Bezeichnung=?);";
			dbz.setSQL(sql);
			dbz.setString(genre);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(liste);
		return liste;
	}

	public void getBuch(String titel) {
		try {
			String sql = "SELECT* FROM Buch WHERE Titel LIKE ?";
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

	public ArrayList<LinkedHashMap<String, String>> getBuecher() {
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
			String sql = "SELECT* FROM Exemplar "
					+ "WHERE ISBN=? "
					+ "AND Signatur=?";
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

	public ArrayList<LinkedHashMap<String, String>> getGenre() {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		try {
			String sql = "SELECT Bezeichnung "
					+ "FROM Genre";
			dbz.setSQL(sql);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public boolean isbnVorhanden(String isbn) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		boolean istVorhanden;
		try {
			String sql = "SELECT ISBN "
					+ "FROM Buch "
					+ "WHERE ISBN=?";
			dbz.setSQL(sql);
			dbz.setString(isbn);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (liste.size() > 0) {
			istVorhanden = true;
		} else
			istVorhanden = false;
		return istVorhanden;
	}

	public boolean verlagVorhanden(String verlag) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		boolean istVorhanden;
		try {
			String sql = "SELECT Name " 
					+ "FROM Verlag " 	
					+ "WHERE Name=?;";
			dbz.setSQL(sql);
			dbz.setString(verlag);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			System.out.println(liste);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (liste.size() > 0) {
			istVorhanden = true;
		} else
			istVorhanden = false;

		return istVorhanden;
	}

//	public boolean autorVorhanden(String autorNachname) {
//		ArrayList<LinkedHashMap<String, String>> liste = null;
//		boolean istVorhanden;
//		try {
//			String sql = "SELECT Autor.Nachname " 
//					+ "FROM Autor " 
//					+ "LEFT JOIN Schreibt "
//					+ "ON Autor.ID = Schreibt.Autor_ID " 
//					+ "LEFT JOIN Buch " 
//					+ "ON Schreibt.Buch_ISBN = Buch_ISBN "
//					+ "WHERE Autor.ID=(SELECT ID FROM Autor WHERE Nachname=?);";
//			dbz.setSQL(sql);
//			dbz.setString(autorNachname);
//			liste = dbz.lesenJava();
//			dbz.getPreparedStatement().close();
//			dbz.setCounter_Prepared(1);
//			System.out.println(liste);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		if (liste.size() > 0) {
//			istVorhanden = true;
//		} else
//			istVorhanden = false;
//
//		return istVorhanden;
//	}
	
	public boolean autorVorhanden(String autorNachname) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		boolean istVorhanden;
		try {
			String sql = "SELECT Nachname " 
					+ "FROM Autor " 
					+ "WHERE Nachname=?;";
			dbz.setSQL(sql);
			dbz.setString(autorNachname);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			System.out.println(liste);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (liste.size() > 0) {
			istVorhanden = true;
		} else
			istVorhanden = false;

		return istVorhanden;
	}
	
	public boolean genreVorhanden(String genre) {
		ArrayList<LinkedHashMap<String, String>> liste = null;
		boolean istVorhanden;
		try {
			String sql = "SELECT ID " 
					+ "FROM Genre " 
					+ "WHERE Bezeichnung=?;";
			dbz.setSQL(sql);
			dbz.setString(genre);
			liste = dbz.lesenJava();
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
			System.out.println(liste);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (liste.size() > 0) {
			istVorhanden = true;
		} else
			istVorhanden = false;

		return istVorhanden;
	}

	public String getVerlagID(String verlag) {
		String verlagID = "";
		try {
			String sql = "SELECT ID " 
					+ "FROM Verlag " 
					+ "WHERE Name=?;";
			dbz.setSQL(sql);
			dbz.setString(verlag);
//			System.out.println(dbz.lesenJava());
			verlagID = dbz.lesenJava().get(0).get("ID");
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return verlagID;
	}
	
	public void addVerlag(String name) {
		if(!verlagVorhanden(name)) {
			try {
				dbz.setPreparedStatement(dbz.getCon().prepareStatement(
						"INSERT INTO Verlag(Name) "
								+ "VALUES(?);"));
				dbz.setString(name);
				dbz.getPreparedStatement().executeUpdate();
				dbz.getPreparedStatement().close();
				dbz.setCounter_Prepared(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getAutorID(String autor) {
		String autorID = "";
		try {
			String sql = "SELECT ID " 
					+ "FROM Autor " 
					+ "WHERE Nachname=?;";
			dbz.setSQL(sql);
			dbz.setString(autor);
			System.out.println(dbz.lesenJava());
			autorID = dbz.lesenJava().get(0).get("ID");
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autorID;
	}
	
	public void addAutor(String nachname, String vorname) {
		if(!verlagVorhanden(nachname)) {
			try {
				dbz.setPreparedStatement(dbz.getCon().prepareStatement(
						"INSERT INTO Autor(Nachname, Vorname) "
								+ "VALUES(?, ?);"));
				dbz.setString(nachname);
				dbz.setString(vorname);
				dbz.getPreparedStatement().executeUpdate();
				dbz.getPreparedStatement().close();
				dbz.setCounter_Prepared(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getGenreID(String genre) {
		String autorID = "";
		try {
			String sql = "SELECT ID " 
					+ "FROM Genre " 
					+ "WHERE Bezeichnung=?;";
			dbz.setSQL(sql);
			dbz.setString(genre);
			System.out.println(dbz.lesenJava());
			autorID = dbz.lesenJava().get(0).get("ID");
			dbz.getPreparedStatement().close();
			dbz.setCounter_Prepared(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autorID;
	}
	
//	Funktioniert nicht: haben als Bezeichnung Enum... sollen wir noch weitere hinzufügen lassen?
//	TODO falls nicht benötigt, andere Genre Methoden löschen
	public void addGenre(String genre) {
		if(!genreVorhanden(genre)) {
			try {
				dbz.setPreparedStatement(dbz.getCon().prepareStatement(
						"INSERT INTO Genre(Bezeichnung) "
								+ "VALUES(?);"));
				dbz.setString(genre);
				dbz.getPreparedStatement().executeUpdate();
				dbz.getPreparedStatement().close();
				dbz.setCounter_Prepared(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Methode nur temporaer
	public void close() {
		dbz.close();
	}
}








