package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
			DeleteV2 d2 = new DeleteV2(db);
			d2.getDeleteFrame().setVisible(true);

		}if (m.getOptionen()[2]== source) {
			
			m.getjMenue().dispose();
			UpdateBestand u = new UpdateBestand(db);
			u.getUpdateFrame().setVisible(true);

		}if (m.getOptionen()[3]== source) {
			m.getjMenue().dispose();
			ReadAutor rba = new ReadAutor(db);
			rba.getReadAutorFrame().setVisible(true);

		}if (m.getOptionen()[4]== source) {
			m.getjMenue().dispose();
			ReadGenre rg = new ReadGenre(db);
			rg.getReadGFrame().setVisible(true);
			

		}if (m.getOptionen()[5]== source) {
			
			JOptionPane.showMessageDialog(null, "Team A2:\nNiels Kochert\nNicole Shiner\nRodez Tazo\nHendrik Tesch\nNiklas Zoz");

		}
		

	}

}
