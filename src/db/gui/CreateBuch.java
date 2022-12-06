package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import db.backend.DB;

public class CreateBuch {

	private JFrame jCreate;

	private JPanel createMain;

	private DB db;

	private JButton menueButton;

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

		JPanel buecherPanel = new JPanel();

		GridLayout buecherLayout = new GridLayout(0, 1);

		buecherPanel.setLayout(buecherLayout);

		buecherPanel.setPreferredSize(new Dimension(800, 600));


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
		jCreate.add(createMain);
		jCreate.setVisible(true);
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