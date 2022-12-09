package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.backend.DB_Buecherverleih;
import db.backend.DB_Zugriff;

public class CreateBuch {

	private JFrame jCreate;

	private JPanel createMain;

	private DB_Buecherverleih db;

	private JButton menueButton;
	private JButton createButton;
	
	private JTextField eingabeISBN = new JTextField(13);
	private JTextField eingabeTitel = new JTextField(50);
	private JTextField eingabeAutorNachname = new JTextField(30);
	private JTextField eingabeAutorVorname = new JTextField(50);
	private JTextField eingabeGenreID = new JTextField(5);
	private JTextField eingabeVerlagID = new JTextField(5);
	private JTextField eingabeErscheinungsjahr = new JTextField(4);
	private JTextField eingabeBestand = new JTextField(5);
	private JLabel[] beschriftungen = new JLabel[8];


	public CreateBuch(DB_Buecherverleih db) {

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

		GridLayout eingabeLayout = new GridLayout(8, 2);
		GridLayout bestaetigungLayout = new GridLayout(1, 1);
		
		JPanel eingabePanel = new JPanel();
		JPanel bestaetigungPanel = new JPanel();

		eingabePanel.setLayout(eingabeLayout);
		bestaetigungPanel.setLayout(bestaetigungLayout);

		bestaetigungPanel.setPreferredSize(new Dimension(200, 150));
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
		
		// GENRE
		
		ArrayList<String> genre = new ArrayList<>();

		for (int i = 0; i < db.getGenre().size(); i++) {

			LinkedHashMap<String, String> genreSchluessel = db.getGenre().get(i);

			System.out.println(genreSchluessel.keySet());

			genre.add(genreSchluessel.get("Bezeichnung"));
			System.out.println(genreSchluessel.get("Bezeichnung"));

		}

		String[] genreArray = new String[genre.size()];

		genreArray = genre.toArray(genreArray);
		
		JComboBox<String> genreComboBox = new JComboBox<String>(genreArray);
		genreComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		genreComboBox.setBounds(26, 152, 164, 47);
		
		// Create Button
		
		createButton = new JButton("Hinzufügen");
		menueButton.setPreferredSize(new Dimension(140, 50));
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String isbn = eingabeISBN.getText();
				String titel = eingabeTitel.getText();
				String autorNachname = eingabeAutorNachname.getText();
				String autorVorname = eingabeAutorVorname.getText();
				String genreid = Integer.toString(genreComboBox.getSelectedIndex()+1);
				String verlagid = eingabeVerlagID.getText();
				String jahr = eingabeErscheinungsjahr.getText();
				String bestand = eingabeBestand.getText();
				if(isbn.isEmpty() || titel.isEmpty() || autorNachname.isEmpty() || autorVorname.isEmpty() || verlagid.isEmpty() || jahr.isEmpty() || bestand.isEmpty()) {
					JOptionPane.showMessageDialog(jCreate, "Es sind nicht alle Felder ausgefüllt");
				} else {
					db.addBuch(isbn, titel, autorNachname, autorVorname, genreid, verlagid, jahr, bestand);
				}
			}
		});

		northPanel.add(menueButton);

		createMain.add(northPanel, BorderLayout.NORTH);
		createMain.add(eingabePanel, BorderLayout.WEST);
		createMain.add(bestaetigungPanel, BorderLayout.EAST);
		jCreate.add(createMain);
		jCreate.setVisible(true);
		
		
		// Eingabefelder
		
		eingabePanel.add(beschriftungen[0] = new JLabel("ISBN:"));
		eingabePanel.add(eingabeISBN);
		eingabePanel.add(beschriftungen[1] = new JLabel("Titel:"));
		eingabePanel.add(eingabeTitel);
		eingabePanel.add(beschriftungen[2] = new JLabel("Autor Nachname:"));
		eingabePanel.add(eingabeAutorNachname);
		eingabePanel.add(beschriftungen[3] = new JLabel("Autor Vorname:"));
		eingabePanel.add(eingabeAutorVorname);
		eingabePanel.add(beschriftungen[4] = new JLabel("Genre:"));
		eingabePanel.add(genreComboBox);
		eingabePanel.add(beschriftungen[5] = new JLabel("VerlagID:"));
		eingabePanel.add(eingabeVerlagID);
		eingabePanel.add(beschriftungen[6] = new JLabel("Erscheinungsjahr:"));
		eingabePanel.add(eingabeErscheinungsjahr);
		eingabePanel.add(beschriftungen[7] = new JLabel("Bestand:"));
		eingabePanel.add(eingabeBestand);
		bestaetigungPanel.add(createButton);
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