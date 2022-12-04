package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB;

public class EventHandlerMenue implements ActionListener {

	private Menue m;
	
	private DB db;

	public EventHandlerMenue(Menue m,DB db) {

		this.m = m;
		this.db = db;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		
		//Add
		if (m.getOptionen()[0]== source) {

		}
		
		//Delete
		if (m.getOptionen()[1]== source) {
			
			m.getjMenue().dispose();
			Delete d = new Delete(db);
			
			d.getDeleteMain().setVisible(true);

		}if (m.getOptionen()[2]== source) {

		}if (m.getOptionen()[3]== source) {

		}if (m.getOptionen()[4]== source) {

		}if (m.getOptionen()[5]== source) {

		}
		

	}

}
