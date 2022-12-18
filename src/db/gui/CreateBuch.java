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
import java.util.regex.Pattern;

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
	private JTextField eingabeAutor2Nachname = new JTextField(30);
	private JTextField eingabeAutor2Vorname = new JTextField(50);
	private JTextField eingabeGenre = new JTextField(5);
	private JTextField eingabeVerlag = new JTextField(5);
	private JTextField eingabeErscheinungsjahr = new JTextField(4);
	private JLabel[] beschriftungen = new JLabel[10];

	public CreateBuch(DB_Buecherverleih db) {

		EventHandlerCreate ed = new EventHandlerCreate(this, db);

		this.db = db;
		jCreate = new JFrame("Create Buch");
		jCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jCreate.setSize(1280, 720);
		jCreate.setResizable(false);
		jCreate.setBackground(new Color(37, 40, 80));

		createMain = new JPanel();
		createMain.setBackground(new Color(221, 224, 229));
		createMain.setLayout(new BorderLayout());

		//

		GridLayout eingabeLayout = new GridLayout(10, 2);
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
			// ISBN Länge immer 13 und nur Zahlen
			// Zweiter Autor (optional)
			@Override
			public void actionPerformed(ActionEvent e) {
				String isbn = eingabeISBN.getText();
				String titel = eingabeTitel.getText();
				String autorNachname = eingabeAutorNachname.getText();
				String autorVorname = eingabeAutorVorname.getText();
				String autorNachname2 = eingabeAutor2Nachname.getText();
				String autorVorname2 = eingabeAutor2Vorname.getText();
				String genre = (String) genreComboBox.getSelectedItem();
				System.out.println(genre);
				String verlag = eingabeVerlag.getText();
				String jahr = eingabeErscheinungsjahr.getText();
				if (isbn.isEmpty() || titel.isEmpty() || autorNachname.isEmpty() || verlag.isEmpty()
						|| jahr.isEmpty()) {
					JOptionPane.showMessageDialog(jCreate, "Es sind nicht alle Felder ausgefüllt");
				} else {
					if (isbn.length() != 13) {
						JOptionPane.showMessageDialog(jCreate, "Die ISBN muss 13 Stellen lang sein");
					} else {
						if (!Pattern.matches("^[0-9]*$", isbn) || !isbn.startsWith("978")) {
							JOptionPane.showMessageDialog(jCreate,
									"Die ISBN darf nur aus Zahlen bestehen und muss mit 978 anfangen");
						} else if (!Pattern.matches("^[0-9]*$", isbn) || jahr.length() != 4) {
							JOptionPane.showMessageDialog(jCreate, "Die Jahresangabe besteht aus 4 Zahlen");
						} else {
							if (autorVorname.isEmpty()) {
								autorVorname = "";
							}
							if (autorVorname2.isEmpty()) {
								autorVorname2 = "";
							}
							if (autorNachname2.isEmpty()) {
								if (!db.autorVorhanden(autorNachname)) {
									db.addAutor(autorNachname, autorVorname);
								}
								if (!db.verlagVorhanden(verlag)) {
									db.addVerlag(verlag);
								}
								db.addBuch(isbn, titel, autorNachname, autorVorname, genre, verlag, jahr, "1");
								JOptionPane.showMessageDialog(jCreate, "Die Erstellung wurde durchgeführt");
								getjCreate().dispose();
								Menue m = new Menue(db);
								m.getjMenue().setVisible(true);

							} else {
								if (!db.autorVorhanden(autorNachname)) {
									db.addAutor(autorNachname, autorVorname);
								}
								if (!db.autorVorhanden(autorNachname2)) {
									db.addAutor(autorNachname2, autorVorname2);
								}
								if (!db.verlagVorhanden(verlag)) {
									db.addVerlag(verlag);
								}
								db.addBuch(isbn, titel, autorNachname, autorVorname, autorNachname2, autorVorname2,
										genre, verlag, jahr, "1");
								JOptionPane.showMessageDialog(jCreate, "Die Erstellung wurde durchgeführt");
								getjCreate().dispose();
								Menue m = new Menue(db);
								m.getjMenue().setVisible(true);

							}
						}
					}
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
		eingabePanel.add(beschriftungen[4] = new JLabel("Zweiter Autor Nachname (optional):"));
		eingabePanel.add(eingabeAutor2Nachname);
		eingabePanel.add(beschriftungen[5] = new JLabel("Zweiter Autor Vorname (optional):"));
		eingabePanel.add(eingabeAutor2Vorname);
		eingabePanel.add(beschriftungen[6] = new JLabel("Genre:"));
		eingabePanel.add(genreComboBox);
		eingabePanel.add(beschriftungen[7] = new JLabel("Verlag:"));
		eingabePanel.add(eingabeVerlag);
		eingabePanel.add(beschriftungen[8] = new JLabel("Erscheinungsjahr:"));
		eingabePanel.add(eingabeErscheinungsjahr);
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