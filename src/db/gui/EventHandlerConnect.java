package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB;

public class EventHandlerConnect implements ActionListener {

	Connect connect;

	DB db;

	public EventHandlerConnect(Connect connect, DB db) {
		this.connect = connect;

		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == connect.getVerbindenButton()) {

			String ip = connect.getIpAdresseField().getText();

			db = new DB(ip);
			
			connect.getjConnect().dispose();
			Menue m = new Menue();
			
			m.getjMenue().setVisible(true);
		}

	}

}
