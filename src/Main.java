import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("DB IP-Address: ");
		String ipAddress = sc.next();
		DB db = new DB(ipAddress);

		
	}

}
