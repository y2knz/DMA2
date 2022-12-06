package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB_Buecherverleih;

public class EventHandlerReadBAuto implements ActionListener{
	
	private ReadBuecherAutor rbautor;
	private DB_Buecherverleih db;
	
	
	
	public  EventHandlerReadBAuto(ReadBuecherAutor rbautor,DB_Buecherverleih db) {
		this.rbautor = rbautor;
		this.db = db;
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		
		if(source == rbautor.getMenueButton()) {
			Menue m = new Menue(db);
			rbautor.getbAutorFrame().dispose();
			m.getjMenue().setVisible(true);
		}
		
	}

}
