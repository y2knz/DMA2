package db.backend;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

	static DB_Buecherverleih db = null;
	
	public static void main(String[] args) {
		//Scanner sc = new Scanner(System.in);
		//System.out.println("DB IP-Address: ");
		//String ipAddresse = sc.next();
		
		//String ipAddress = "192.168.0.137";

		String ipAddresse  = "192.168.0.137";

//		
		db = new DB_Buecherverleih(new DB_Zugriff(ipAddresse));
//

		db.addBuch("9783937507309", "Norden", "de Boer", "Hendrik", "Drama", "Carl Hanser", "1969", "1");
//		db.deleteBuch("Norden");

		
		
//		System.out.println(db.genreVorhanden("Fantasy"));
//		System.out.println(db.autorVorhanden("Orwell"));
//		db.getVerlagID("Zebra");
//		db.addVerlag("test");
//		db.getAutorID("Orwell");
//		db.addAutor("test", "test");
//		db.addBuch("9783937507309", "Norden", "10", "113", "1969", "10");
//		System.out.println(db.verlagVorhanden("Carl Hanser"));
//		System.out.println(db.autorVorhanden("Orwel"));
//		db.getBuecherVonAutor("Or");
//	db.deleteBuch("Norden");
//		db.addExemplar("9781596792500", "4 A 1530 4", "1", "2");
//		db.deleteExemplar("9781596792500", "4 A 1530 3");
//		db.updateKundeEmail("1", "Mustermann@mm.de");
//		db.getBuch("H"); 
//		db.getBuecherVonGenre("Fantasy");
//		System.out.println(db.getGenre());
		
//		for(LinkedHashMap<String,String> daten: db.getBuecherVonGenre("Fantasy")){
//			System.out.println(daten);
//		}
//	
//	
//		for(LinkedHashMap<String,String>daten:db.getKundeEmail(1)) {
//			System.out.println(daten);
//		}
	
//		db.getBuch("1");

//		for(LinkedHashMap<String, String> daten:db.getBuecher()) {
//			System.out.println(daten);
//		}
//		
//		for(LinkedHashMap<String,String>daten:db.showExemplar("9780132382458", "9 A 7617 2")) {
//			System.out.println(daten);
//		}
		
		db.close();
	}
	
	

}
