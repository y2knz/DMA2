package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB;

public class EventHandlerDelete implements ActionListener{
	
	private Delete d;
	
	private DB db;
	
	public  EventHandlerDelete(Delete d, DB db) {
		this.d = d;
		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == d.getMenueButton()) {
			Menue m = new Menue(db);
			d.getjDelete().dispose();
			m.getjMenue().setVisible(true);
		}
		
	}

}
