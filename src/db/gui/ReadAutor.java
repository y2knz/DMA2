package db.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.backend.DB_Buecherverleih;
import javax.swing.JTextField;

public class ReadAutor {
	private DefaultTableModel model;
	private JFrame readAutorFrame;
	private DB_Buecherverleih db;
	private Object[] reihe;
	private JTextField autorTextField;

	public ReadAutor(DB_Buecherverleih db) {

		this.db = db;

		JTable buecherTable = new JTable();
		// Richtige werte
		Object[] zeilen = { "Titel", "Vorname", "Nachname" };
		model = new DefaultTableModel() {

			// Einzelne Zellen koennen nicht mehr bearbeitet werden
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		reihe = new Object[3];

		readAutorFrame = new JFrame("Read Autor");
		readAutorFrame.getContentPane().setBackground(new Color(221, 224, 229));
		readAutorFrame.getContentPane().setForeground(Color.WHITE);
		readAutorFrame.setSize(new Dimension(1280, 720));
		readAutorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		readAutorFrame.getContentPane().setLayout(null);
		readAutorFrame.setLocationRelativeTo(null);
		model.setColumnIdentifiers(zeilen);

		buecherTable.setModel(model);
		buecherTable.setBackground(Color.white);
		buecherTable.setForeground(Color.black);

		buecherTable.setGridColor(Color.BLACK);
		buecherTable.setFocusable(false);
		buecherTable.setRowSelectionAllowed(false);
		buecherTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buecherTable.setRowHeight(30);
		buecherTable.setAutoCreateRowSorter(true);

		JScrollPane pane = new JScrollPane(buecherTable);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(283, 89, 932, 584);
		readAutorFrame.getContentPane().add(pane);

		autorTextField = new JTextField();
		autorTextField.setBounds(26, 150, 164, 47);
		readAutorFrame.getContentPane().add(autorTextField);
		autorTextField.setColumns(10);

		JButton menueButton = new JButton("Men??");
		menueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menue m = new Menue(db);
				readAutorFrame.dispose();
				m.getjMenue().setVisible(true);

			}
		});
		menueButton.setBounds(1072, 21, 145, 36);
		readAutorFrame.getContentPane().add(menueButton);

		JLabel autorLabel = new JLabel("Nachname des Autors eingeben");
		autorLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		autorLabel.setBounds(26, 38, 312, 47);
		readAutorFrame.getContentPane().add(autorLabel);

		JButton auswaehlenButton = new JButton("Ausw??hlen");
		auswaehlenButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		auswaehlenButton.setBounds(26, 265, 164, 47);
		auswaehlenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				String autor = autorTextField.getText();
				System.out.println(autor);
				if (autor == null) {
					JOptionPane.showMessageDialog(readAutorFrame, "Feld ausf??llen");
				} else {
					ArrayList<LinkedHashMap<String, String>> autorBuecher = db.getBuecherVonAutor(autor);

					System.out.println(autorBuecher.toString() + "1");

					if (autorBuecher.isEmpty()) {
						JOptionPane.showMessageDialog(readAutorFrame, "Autor nicht vorhanden");
					} else {
						model.fireTableDataChanged();
						buecherTable.repaint();

						autorBuecherAnzeigen(autorBuecher);
						buecherTable.revalidate();
						buecherTable.repaint();

					}
				}

			}
		});
		readAutorFrame.getContentPane().add(auswaehlenButton);

		readAutorFrame.revalidate();

		readAutorFrame.setVisible(true);

		readAutorFrame.revalidate();
		readAutorFrame.setVisible(true);

	}

	public JFrame getReadAutorFrame() {
		return readAutorFrame;
	}

	public void autorBuecherAnzeigen(ArrayList<LinkedHashMap<String, String>> autorBuecher) {
		for (int i = 0; i < autorBuecher.size(); i++) {

			LinkedHashMap<String, String> buch = autorBuecher.get(i);

			for (String key : buch.keySet()) {

				if (key.equals("Nachname")) {
					reihe[2] = buch.get(key);
				}else if (key.equals("Vorname")) {
					reihe[1] = buch.get(key);
				} else if (key.equals("Titel")) {
					reihe[0] = buch.get(key);
					model.addRow(reihe);
				}
			}
		}
	}
}
