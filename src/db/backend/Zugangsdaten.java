package db.backend;

public class Zugangsdaten {
	private String url = null;
	private String benutzer = "root";
	private String passwort = "root";
	
	public Zugangsdaten(){
	}

	public void setIP(String ipAddresse) {
		String s1 = "jdbc:mysql://";
		String s2 = ":3306/buecherverleih";
		this.url = s1 + ipAddresse + s2;
	}
	
	public String getUrl() {
		return url;
	}

	public String getBenutzer() {
		return benutzer;
	}

	public String getPasswort() {
		return passwort;
	}

}
