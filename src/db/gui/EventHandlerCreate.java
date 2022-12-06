package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import db.backend.DB;

public class EventHandlerCreate implements ActionListener{
	
	private CreateBuch createBuch;
	
	private DB db;
	
	public  EventHandlerCreate(CreateBuch createBuch, DB db) {
		this.createBuch = createBuch;
		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == createBuch.getMenueButton()) {
			Menue m = new Menue(db);
			createBuch.getjCreate().dispose();
			m.getjMenue().setVisible(true);
		}
		
	}

}
