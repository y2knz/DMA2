package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import db.backend.DB_Buecherverleih;
import db.backend.DB_Zugriff;

public class EventHandlerConnect implements ActionListener {

	Connect connect;

	DB_Buecherverleih db;

	public EventHandlerConnect(Connect connect, DB_Buecherverleih db) {
		this.connect = connect;

		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == connect.getVerbindenButton()) {

			String ip = connect.getIpAdresseField().getText();
			
			
			try {

				db = new DB_Buecherverleih(new DB_Zugriff(ip));

				Menue m = new Menue(db);
				
				connect.getjConnect().dispose();

				m.getjMenue().setVisible(true);

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null,"ungültige Eingabe");
			}
			}

			
		

	}

}
