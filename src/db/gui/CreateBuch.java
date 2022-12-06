package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.backend.DB;

public class CreateBuch {

	private JFrame jCreate;

	private JPanel createMain;

	private DB db;

	private JButton menueButton;
	
	private JTextField eingabeISBN = new JTextField(13);
	private JTextField eingabeTitel = new JTextField(50);
	private JTextField eingabeGenreID = new JTextField(5);
	private JTextField eingabeVerlagID = new JTextField(5);
	private JTextField eingabeErscheinungsjahr = new JTextField(4);
	private JTextField eingabeBestand = new JTextField(5);
	private JLabel[] beschriftungen = new JLabel[6];


	public CreateBuch(DB db) {

		EventHandlerCreate ed = new EventHandlerCreate(this, db);

		this.db = db;
		jCreate = new JFrame("Datenbank Delete");
		jCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jCreate.setSize(1280, 720);
		jCreate.setResizable(false);
		jCreate.setBackground(new Color(37, 40, 80));

		createMain = new JPanel();
		createMain.setBackground(new Color(221, 224, 229));
		createMain.setLayout(new BorderLayout());

		//

		GridLayout eingabeLayout = new GridLayout(6, 2);
		
		JPanel eingabePanel = new JPanel();

		eingabePanel.setLayout(eingabeLayout);

		eingabePanel.setPreferredSize(new Dimension(800, 200));


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

		createMain.add(northPanel, BorderLayout.NORTH);
		createMain.add(eingabePanel, BorderLayout.WEST);
		jCreate.add(createMain);
		jCreate.setVisible(true);
		
		// Eingabefelder
		
		eingabePanel.add(beschriftungen[0] = new JLabel("ISBN:"));
		eingabePanel.add(eingabeISBN);
		eingabePanel.add(beschriftungen[0] = new JLabel("Titel:"));
		eingabePanel.add(eingabeTitel);
		eingabePanel.add(beschriftungen[0] = new JLabel("GenreID:"));
		eingabePanel.add(eingabeGenreID);
		eingabePanel.add(beschriftungen[0] = new JLabel("VerlagID:"));
		eingabePanel.add(eingabeVerlagID);
		eingabePanel.add(beschriftungen[0] = new JLabel("Erscheinungsjahr:"));
		eingabePanel.add(eingabeErscheinungsjahr);
		eingabePanel.add(beschriftungen[0] = new JLabel("Bestand:"));
		eingabePanel.add(eingabeBestand);
	}

	public JPanel getCreateMain() {
		return createMain;
	}

	public JFrame getjCreate() {
		return jCreate;
	}

	public JButton getMenueButton() {
		return menueButton;
	}

}