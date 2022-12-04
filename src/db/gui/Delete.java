package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.backend.DB;

public class Delete {
	
	private JFrame jDelete;
	
	private JPanel deleteMain;
	
	private DB db;
	
	private ArrayList<JLabel> buecherLabel = new ArrayList<>();
	
	
	public Delete(DB db) {
		
		this.db = db;
		jDelete = new JFrame("Datenbank Delete");
		jDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jDelete.setSize(1280, 720);
		jDelete.setResizable(false);
		jDelete.setBackground(new Color(37, 40, 80));
		
		
		deleteMain = new JPanel();
		deleteMain.setBackground(new Color(221, 224, 229));
		deleteMain.setLayout(new BorderLayout());
		
		//
		
		JPanel buecherPanel = new JPanel();
		
		GridLayout buecherLayout = new GridLayout(0,1);
		
		buecherPanel.setLayout(buecherLayout);
		
		buecherPanel.setPreferredSize(new Dimension(800,600));
		
		
		//Bücher Liste
		
		for(int i = 0; i <db.getBooks().size();i++) {
			
			JLabel buch = new JLabel("");
			
			buecherLabel.add(buch);
			buecherPanel.add(buecherLabel.get(i));
			
		}
		
		//Methode
		buecherAnzeigen();
		
		
		
		
		deleteMain.add(buecherPanel,BorderLayout.CENTER);
		
		jDelete.add(deleteMain);
		jDelete.setVisible(true);
	}



	public JPanel getDeleteMain() {
		return deleteMain;
	}
	
	public void buecherAnzeigen() {
		
		
		
		for(int i = 0; i < db.getBooks().size();i++) {
			
			String infos = "";
			
			LinkedHashMap<String, String>buch = db.getBooks().get(i);
			
			System.out.println();
			
			for(String key : buch.keySet()) {
				System.out.println(key);
				System.out.println(buch.keySet().size());
				
				infos += buch.get(key)+ " ";
				
				buecherLabel.get(i).setText(infos);
				
				System.out.println(infos);
				
			}
			
		}
		
		
	}

}
