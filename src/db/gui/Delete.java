package db.gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.backend.DB;

public class Delete {
	
	private JFrame jMenue;
	
	private JPanel deleteMain;
	
	private DB db;
	
	private ArrayList<JLabel> buecherLabel = new ArrayList<>();
	
	
	public Delete(DB db) {
		
		this.db = db;
		jMenue = new JFrame("Datenbank Delete");
		jMenue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jMenue.setSize(1280, 720);
		jMenue.setResizable(false);
		jMenue.setBackground(new Color(37, 40, 80));
		
		
		deleteMain = new JPanel();
		deleteMain.setBackground(new Color(221, 224, 229));
		
		
		//Bücher Liste
		
		for(int i = 0; i <db.getBooks().size();i++) {
			
			JLabel buch = new JLabel("");
			
			buecherLabel.add(buch);
			
		}
		
		
		
		jMenue.add(deleteMain);
		jMenue.setVisible(true);
	}



	public JPanel getDeleteMain() {
		return deleteMain;
	}

}
