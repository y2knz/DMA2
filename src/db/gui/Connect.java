package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.backend.DB;
import db.backend.Zugangsdaten;

public class Connect {
	
	
	private DB db;

	private JFrame jConnect;
	
	private JTextField ipAdresseField;
	
	private JButton verbindenButton;

	public Connect() {
		EventHandlerConnect ec = new EventHandlerConnect(this,db);
		jConnect = new JFrame("Datenbank Menue");
		jConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jConnect.setSize(1280, 720);
		jConnect.setResizable(false);
		jConnect.setBackground(new Color(37, 40, 80));
		
		
		JPanel connectMainPanel = new JPanel();
		connectMainPanel.setBackground(new Color(221, 224, 229));
		connectMainPanel.setLayout(new BorderLayout());
		
	//Anzeige
		
		JLabel ipInputLabel = new JLabel("Ip eingeben: ");
		
		connectMainPanel.add(ipInputLabel,BorderLayout.NORTH);
		
		
		//Eingabe
		
		ipAdresseField = new JTextField();
		

		ipAdresseField.setPreferredSize(new Dimension(380, 50));


		ipAdresseField.setForeground(Color.BLACK);

		connectMainPanel.add(ipAdresseField,BorderLayout.CENTER);
		
		
		//Button
		
		verbindenButton = new JButton("Verbinden");
		
		verbindenButton.addActionListener(ec);
		
		connectMainPanel.add(verbindenButton,BorderLayout.SOUTH);
		
		jConnect.add(connectMainPanel);
		
		jConnect.setVisible(true);
		
	}

	public JFrame getjConnect() {
		return jConnect;
	}

	public JTextField getIpAdresseField() {
		return ipAdresseField;
	}

	public JButton getVerbindenButton() {
		return verbindenButton;
	}

}
