import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MainExperiment {

	public static void main(String[] args) {
		
		DBExperiment db = new DBExperiment();
		
		
		System.out.print("Wollen Sie die Autorenliste sehgen");
				db.setSQL("SELECT * FROM Autor");
			
			ArrayList<LinkedHashMap<String,String>> liste = db.lesenJava();	
			System.out.println();
			System.out.println(liste.get(1).get("Nachname"));
		System.out.println();
	}

}
