package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import db.backend.DB_Buecherverleih;
import db.backend.DB_Zugriff;

public class Menue {

	private JFrame jMenue;

	private Font spielFont = new Font("Arial", Font.PLAIN, 18);

	private JButton[] optionen = new JButton[6];

	private JPanel option_panel = new JPanel();

	private DB_Buecherverleih db;

	public Menue(DB_Buecherverleih db) {

		this.db = db;

		jMenue = new JFrame("Datenbank Menue");
		jMenue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jMenue.setSize(1280, 720);
		jMenue.setResizable(false);
		jMenue.setBackground(new Color(37, 40, 80));

		// Eventhandler Menue

		EventHandlerMenue ev = new EventHandlerMenue(this,db);

		// Main Panel
		JPanel menueMainPanel = new JPanel();
		menueMainPanel.setBackground(new Color(221, 224, 229));
		menueMainPanel.setLayout(new BorderLayout());

		// North Panel

		JPanel northPanel = new JPanel();

		northPanel.setPreferredSize(new Dimension(1000, 150));
		northPanel.setOpaque(false);
		menueMainPanel.add(northPanel, BorderLayout.NORTH);

		// Center Panel
		JPanel centerMenuePanel = new JPanel();
		centerMenuePanel.setPreferredSize(new Dimension(300, 720));
		centerMenuePanel.setOpaque(false);
//		centerMenuePanel.setBorder(new LineBorder(Color.BLACK));
		menueMainPanel.add(centerMenuePanel, BorderLayout.CENTER);

		GridLayout optionLayout = new GridLayout(6, 1);
		optionLayout.setVgap(20);
		option_panel.setLayout(optionLayout);
		option_panel.setBackground(new Color(221, 224, 229));

		option_panel.setPreferredSize(new Dimension(300, 400));
		for (int i = 0; i < optionen.length; i++) {
			optionen[i] = new JButton();

			option_panel.add(optionen[i]);

			optionen[i].addActionListener(ev);

			optionen[i].setBackground(Color.WHITE);
			optionen[i].setFont(spielFont);
			optionen[i].setForeground(Color.BLACK);

		}
		optionen[0].setText("Buch anlegen");
		optionen[1].setText("Buch l??schen");
		optionen[2].setText("Bestand Buch ??ndern");
		optionen[3].setText("Zeige alle B??cher von Autor");
		optionen[4].setText("Zeige alle B??cher eines Genres");
		optionen[5].setText("Credits");
		centerMenuePanel.add(option_panel);

		jMenue.add(menueMainPanel);

		jMenue.setContentPane(menueMainPanel);

		jMenue.setVisible(true);

	}

	public JButton[] getOptionen() {
		return optionen;
	}

	public JFrame getjMenue() {
		return jMenue;
	}

}
