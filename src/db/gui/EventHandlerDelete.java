package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import db.backend.DB_Buecherverleih;

public class EventHandlerDelete implements ActionListener{
	
	private DeleteBuch d;
	
	private DB_Buecherverleih db;
	
	public  EventHandlerDelete(DeleteBuch d, DB_Buecherverleih db) {
		this.d = d;
		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		
		if(source == d.getMenueButton()) {
			Menue m = new Menue(db);
			d.getjDelete().dispose();
			m.getjMenue().setVisible(true);
		}
		if(source == d.getLoeschenButton()) {
			
			try {
				db.deleteBuch(d.getLoeschenInputField().getText());
				JOptionPane.showMessageDialog(null,d.getLoeschenInputField().getText() +" wurde gelöscht!");
				d.buecherAnzeigen();
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Titel nicht vorhanden");
			}
		}
		
	}

}
