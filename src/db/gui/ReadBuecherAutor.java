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
import javax.swing.border.LineBorder;

import db.backend.DB_Buecherverleih;

public class ReadBuecherAutor {

	private DB_Buecherverleih db;

	private JFrame bAutorFrame;

	private JPanel bAutorMain;

	private JButton menueButton;

	private JTextField autorInputField;

	private JButton autorSuchenButton;

	public ReadBuecherAutor(DB_Buecherverleih db) {

		this.db = db;

		EventHandlerReadBAuto rba = new EventHandlerReadBAuto(this, db);
		bAutorFrame = new JFrame("Datenbank Buch Autor");
		bAutorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bAutorFrame.setSize(1280, 720);
		bAutorFrame.setResizable(false);
		bAutorFrame.setBackground(new Color(37, 40, 80));

		bAutorMain = new JPanel();
		bAutorMain.setBackground(new Color(221, 224, 229));
		bAutorMain.setLayout(new BorderLayout());

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
		menueButton.addActionListener(rba);

		northPanel.add(menueButton);

		bAutorMain.add(northPanel, BorderLayout.NORTH);

		// West Panel

		JPanel westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(250, 500));
		westPanel.setOpaque(true);
		westPanel.setBorder(new LineBorder(Color.YELLOW));

		/// Suchen Panel
		JPanel suchenPanel = new JPanel();
		suchenPanel.setPreferredSize(new Dimension(250, 250));
		GridLayout loeschenLayout = new GridLayout(3, 1);
		loeschenLayout.setVgap(50);
		suchenPanel.setLayout(loeschenLayout);

		// Autor Suche Eingabe

		JLabel suchenLabel = new JLabel("Name des Autors eingeben.");

		suchenLabel.setPreferredSize(new Dimension(200, 25));

		suchenPanel.add(suchenLabel);

		autorInputField = new JTextField("");

		autorInputField.setPreferredSize(new Dimension(200, 25));

		suchenPanel.add(autorInputField);

		autorSuchenButton = new JButton("Autor suchen");

		autorSuchenButton.setPreferredSize(new Dimension(200, 25));

		autorSuchenButton.addActionListener(rba);

		suchenPanel.add(autorSuchenButton);

		westPanel.add(suchenPanel);

		// Fill Panel West

		JPanel fillWestPanel = new JPanel();

		fillWestPanel.setOpaque(true);
		fillWestPanel.setPreferredSize(new Dimension(200, 250));
		
		westPanel.add(fillWestPanel);
		bAutorMain.add(westPanel,BorderLayout.WEST);

////////////////////////////////////////////////////////////////////////////
		bAutorFrame.add(bAutorMain);
		bAutorFrame.setVisible(true);
	}

	public JButton getMenueButton() {
		return menueButton;
	}

	public JFrame getbAutorFrame() {
		return bAutorFrame;
	}

	public JPanel getbAutorMain() {
		return bAutorMain;
	}

}
