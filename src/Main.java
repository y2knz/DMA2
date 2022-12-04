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

//		db.addBook("9781408855684", "Harry Potter and the half-blood prince", "1", "100", "2002", "1");
//		db.getBooksFromAutor("Rowling");
		//db.addBook("9781408855684", "Harry Potter and the half-blood prince", "1", "100", "2002", "1");

		
		
		
//		Funktionierende Queries:
//		db.deleteBook("Norden");
//		db.addExemplar("9781596792500", "4 A 1530 4", "1", "2");
//		db.deleteExemplar("9781596792500", "4 A 1530 3");
//		db.updateKundeEmail("1", "Mustermann@mm.de");
		
		db.close();
	}
	
	

}
