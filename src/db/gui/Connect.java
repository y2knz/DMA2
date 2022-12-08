package db.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.backend.DB_Buecherverleih;
import db.backend.DB_Zugriff;
import db.backend.Zugangsdaten;

public class Connect {

	private DB_Buecherverleih db = null;

	private JFrame jConnect;

	private JTextField ipAdresseField;

	private JButton verbindenButton;

	public Connect() {
		jConnect = new JFrame("Datenbank Menue");
		jConnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jConnect.setSize(1280, 720);
		jConnect.setResizable(false);
		jConnect.setBackground(new Color(37, 40, 80));

		JPanel connectMainPanel = new JPanel();
		connectMainPanel.setBackground(new Color(221, 224, 229));
		connectMainPanel.setLayout(new BorderLayout());

		// Anzeige

		JLabel ipInputLabel = new JLabel("Ip eingeben: ");

		connectMainPanel.add(ipInputLabel, BorderLayout.NORTH);

		// MittelPanel

		JPanel mittelJPanel = new JPanel();
		mittelJPanel.setSize(new Dimension(200, 100));

		// Eingabe

		ipAdresseField = new JTextField();

		ipAdresseField.setPreferredSize(new Dimension(380, 50));

		ipAdresseField.setForeground(Color.BLACK);

		mittelJPanel.add(ipAdresseField);

		connectMainPanel.add(mittelJPanel, BorderLayout.CENTER);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setSize(new Dimension(200, 100));

		// Button

		verbindenButton = new JButton("Verbinden");

		verbindenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipAdresseField.getText();
				
				
				try {

					db = new DB_Buecherverleih(new DB_Zugriff(ip));

					Menue m = new Menue(db);
					
					jConnect.dispose();

					m.getjMenue().setVisible(true);

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,"ungültige Eingabe");
				}
				
			}
		});

		buttonPanel.add(verbindenButton);

		connectMainPanel.add(buttonPanel, BorderLayout.SOUTH);

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
