package db.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandlerMenue implements ActionListener {

	private Menue m;

	public EventHandlerMenue(Menue m) {

		this.m = m;

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
			Delete d = new Delete();
			
			d.getDeleteMain().setVisible(true);

		}if (m.getOptionen()[2]== source) {

		}if (m.getOptionen()[3]== source) {

		}if (m.getOptionen()[4]== source) {

		}if (m.getOptionen()[5]== source) {

		}
		

	}

}
