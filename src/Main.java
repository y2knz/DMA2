import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

	static DB db = null;
	
	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("DB IP-Address: ");
//		String ipAddress = sc.next();
		
		String ipAddress = "192.168.0.137";
		
		db = new DB(ipAddress);
//		db.ArrayListgetAutorBooks("Joanne");
		//getBooks();
		//getBooksFromAutor();
		//getBooksFromAutor("Doyle");
//		db.addGenre("Dystopian");

//		db.addBook("9781408855684", "Harry Potter and the half-blood prince", "1", "100", "2002", "1");
		db.deleteBook("Norden");
//		db.getBooksFromAutor("Rowling");		
		db.getBooksFromAutor("Rowling");


		//db.addBook("9781408855684", "Harry Potter and the half-blood prince", "1", "100", "2002", "1");
		db.close();
	}
	
	public static void getBooks() {
		db.setSQL("SELECT * FROM Buch;");
		ArrayList<LinkedHashMap<String,String>> daten = db.lesenJava();
		for(LinkedHashMap<String,String>datensatz:daten) {
			System.out.println(datensatz);
		}
	}
	
	//funktioniert noch nicht...
	public static void getBooksFromAutor(String autor) {
		String sql1, sql2, sql3;
		sql1 = "SELECT ID FROM Autor"
				+ "WHERE Nachname=" + autor;
		sql2 = "SELECT Buch_ISBN FROM Schreibt "
				+ "WHERE Autor_ID IN ("+ sql1 +");";
		sql3 = "SELECT * FROM Buch"
				+ "WHERE ISBN IN (" +sql2 + ");";
				
		db.setSQL(sql3);
		ArrayList<LinkedHashMap<String,String>> daten = db.lesenJava();
		for(LinkedHashMap<String,String>datensatz:daten) {
			System.out.println(datensatz);
		}
	}

}
