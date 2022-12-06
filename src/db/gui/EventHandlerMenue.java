package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB_Buecherverleih;
import db.backend.DB_Zugriff;

public class EventHandlerMenue implements ActionListener {

	private Menue m;
	
	private DB_Buecherverleih db;

	public EventHandlerMenue(Menue m,DB_Buecherverleih db) {

		this.m = m;
		this.db = db;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		
		//Add
		if (m.getOptionen()[0]== source) {
			m.getjMenue().dispose();
			CreateBuch createBuch = new CreateBuch(db);
			createBuch.getjCreate().setVisible(true);
		}
		
		//Delete
		if (m.getOptionen()[1]== source) {
			
			m.getjMenue().dispose();
			DeleteBuch d = new DeleteBuch(db);
			
			d.getDeleteMain().setVisible(true);

		}if (m.getOptionen()[2]== source) {

		}if (m.getOptionen()[3]== source) {
			m.getjMenue().dispose();
			ReadBuecherAutor rba = new ReadBuecherAutor(db);
			rba.getbAutorFrame().setVisible(true);

		}if (m.getOptionen()[4]== source) {

		}if (m.getOptionen()[5]== source) {

		}
		

	}

}
