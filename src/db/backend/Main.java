package db.backend;
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
		
		//String ipAddress = "192.168.0.137";
		String ipAddress  = "192.168.2.111";
		
		db = new DB(ipAddress);

//		db.getBooksFromAutor();
//		db.deleteBook("Norden");
//		db.addExemplar("9781596792500", "4 A 1530 4", "1", "2");
//		db.deleteExemplar("9781596792500", "4 A 1530 3");
//		db.updateKundeEmail("1", "Mustermann@mm.de");
		//db.getBook("H"); 
		
//		for(LinkedHashMap<String,String> daten: db.getBooksFromGenre("Fantasy")){
//			System.out.println(daten);
//		}
	
	
//		for(LinkedHashMap<String,String>daten:db.getKundeEmail(1)) {
//			System.out.println(daten);
//		}
	
	

//		for(LinkedHashMap<String, String> daten:db.getBooks()) {
//			System.out.println(daten);
//		}
		
//		for(LinkedHashMap<String,String>daten:db.showExemplar("9780132382458", "9 A 7617 2")) {
//			System.out.println(daten);
//		}
		
		db.close();
	}
	
	

}
