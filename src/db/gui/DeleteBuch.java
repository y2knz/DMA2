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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import db.backend.DB;

public class DeleteBuch {

	private JFrame jDelete;

	private JPanel deleteMain;

	private DB db;
	
	private ScrollPane buecherScrollPane;
	
	private JTextField loeschenInputField;

	private JButton menueButton;
	
	private JButton loeschenButton;

	private ArrayList<JLabel> buecherLabel = new ArrayList<>();

	public DeleteBuch(DB db) {

		EventHandlerDelete ed = new EventHandlerDelete(this, db);

		// TODO
		int tabellen_groesse = db.getBuecher().size();

		this.db = db;
		jDelete = new JFrame("Datenbank Delete");
		jDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jDelete.setSize(1280, 720);
		jDelete.setResizable(false);
		jDelete.setBackground(new Color(37, 40, 80));

		deleteMain = new JPanel();
		deleteMain.setBackground(new Color(221, 224, 229));
		deleteMain.setLayout(new BorderLayout());

		// West Panel

		JPanel westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(200, 300));
		westPanel.setOpaque(true);
		westPanel.setBorder(new LineBorder(Color.YELLOW));
		GridLayout loeschenLayout = new GridLayout(3,1);
		loeschenLayout.setVgap(50);
		westPanel.setLayout(loeschenLayout);
		
		
		//Loeschen Eingabe
		
		JLabel loeschenLabel = new JLabel("Name des Buches eingeben.");
		
		loeschenLabel.setPreferredSize(new Dimension(200,25));
		
		westPanel.add(loeschenLabel);
		
		loeschenInputField = new JTextField("");
		
		loeschenInputField.setPreferredSize(new Dimension(200,25));
		
		westPanel.add(loeschenInputField);
		
		loeschenButton = new JButton("Löschen");
		
		loeschenButton.setPreferredSize(new Dimension(200,25));
		
		loeschenButton.addActionListener(ed);
		
		westPanel.add(loeschenButton);
		

		deleteMain.add(westPanel, BorderLayout.WEST);
		
		//Fill Panel West
		
		JPanel fillWestPanel = new JPanel();
		
		fillWestPanel.setOpaque(true);
		fillWestPanel.setPreferredSize(new Dimension(200,300));
		
		deleteMain.add(fillWestPanel);

		// Center Panel

		

		JPanel buecherPanel = new JPanel();

		GridLayout buecherLayout = new GridLayout(0, 1);

		buecherPanel.setLayout(buecherLayout);

		buecherPanel.setPreferredSize(new Dimension(800, 600));

		// Bücher Liste
		
		JLabel ueberschrift = new JLabel("ISBN Name Autor ");
		buecherLabel.add(ueberschrift);
		

		for (int i = 0; i < db.getBuecher().size(); i++) {

			JLabel buch = new JLabel("");
			
			buch.setBorder(new LineBorder(Color.RED));

			buecherLabel.add(buch);
			buecherPanel.add(buecherLabel.get(i));

		}
	     buecherScrollPane = new ScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		buecherScrollPane.setPreferredSize(new Dimension(900, 600));
//		buecherScrollPane.setVisible(true);
		buecherScrollPane.add(buecherPanel);
		

		// Methode
		buecherAnzeigen();

		deleteMain.add(buecherScrollPane, BorderLayout.CENTER);

		// NorthPanel

		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(1280, 50));
		northPanel.setOpaque(true);

		// Filler Panel

		JPanel northFillerPanel = new JPanel();
		northFillerPanel.setPreferredSize(new Dimension(1000, 50));
		northFillerPanel.setOpaque(true);
		northPanel.add(northFillerPanel);

		// Menu Button

		menueButton = new JButton("Menü");
		menueButton.setPreferredSize(new Dimension(140, 50));
		menueButton.addActionListener(ed);

		northPanel.add(menueButton);

		deleteMain.add(northPanel, BorderLayout.NORTH);
		jDelete.add(deleteMain);
		jDelete.setVisible(true);
	}

	public JPanel getDeleteMain() {
		return deleteMain;
	}

	public JTextField getLoeschenInputField() {
		return loeschenInputField;
	}

	public JButton getLoeschenButton() {
		return loeschenButton;
	}

	public JFrame getjDelete() {
		return jDelete;
	}

	public JButton getMenueButton() {
		return menueButton;
	}

	public void buecherAnzeigen() {
		System.out.println(db.getBuecher().size());

		for (int i = 0; i < db.getBuecher().size(); i++) {

			String infos = "";

			LinkedHashMap<String, String> buch = db.getBuecher().get(i);

			for (String key : buch.keySet()) {

				infos += buch.get(key) + " ";

				buecherLabel.get(i +1).setText(infos);
				
				System.out.println(infos);

			}

		}

	}

}
