
public class Zugangsdaten {
	private String url = null;
	private String user = "root";
	private String password = "root";
	
	public Zugangsdaten(String ipAddress){
		String s1 = "jdbc:mysql://";
		String s2 = ":3306/buecherverleih";
		this.url = s1 + ipAddress + s2;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
