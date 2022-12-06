package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.backend.DB;

public class Delete {
	
	private JFrame jDelete;
	
	private JPanel deleteMain;
	
	private DB db;
	
	private JButton menueButton;
	
	private ArrayList<JLabel> buecherLabel = new ArrayList<>();
	
	
	public Delete(DB db) {
		
		EventHandlerDelete ed = new EventHandlerDelete(this, db);
		
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
		
		ScrollPane buecherScrollPanel = new ScrollPane();
		

		
		JPanel buecherPanel = new JPanel();
		
		GridLayout buecherLayout = new GridLayout(0,1);
		
		buecherPanel.setLayout(buecherLayout);
		
		buecherPanel.setPreferredSize(new Dimension(800,600));
		
		
		
		//Bücher Liste
		
		for(int i = 0; i <db.getBuecher().size();i++) {
			
			JLabel buch = new JLabel("");
			
			buecherLabel.add(buch);
			buecherPanel.add(buecherLabel.get(i));
			
		}
		buecherScrollPanel.add(buecherPanel);
		
		
		
		//Methode
		buecherAnzeigen();
		
		
		
		
		deleteMain.add(buecherScrollPanel,BorderLayout.CENTER);
		
		
		//NorthPanel
		
		JPanel northPanel  =new JPanel();
		northPanel.setPreferredSize(new Dimension(1280,50));
		northPanel.setOpaque(true);
		
		//Filler Panel
		
		JPanel northFillerPanel = new JPanel();
		northFillerPanel.setPreferredSize(new Dimension(1000,50));
		northFillerPanel.setOpaque(true);
		northPanel.add(northFillerPanel);
		
		//Menu Button
		
		menueButton = new JButton("Menü");
		menueButton.setPreferredSize(new Dimension(140,50));
		menueButton.addActionListener(ed);
		
		northPanel.add(menueButton);
		
		
		deleteMain.add(northPanel,BorderLayout.NORTH);
		jDelete.add(deleteMain);
		jDelete.setVisible(true);
	}



	public JPanel getDeleteMain() {
		return deleteMain;
	}
	
	public JFrame getjDelete() {
		return jDelete;
	}



	public JButton getMenueButton() {
		return menueButton;
	}



	public void buecherAnzeigen() {
		
		
		
		for(int i = 0; i < db.getBuecher().size();i++) {
			
			String infos = "";
			
			LinkedHashMap<String, String>buch = db.getBuecher().get(i);
			
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
