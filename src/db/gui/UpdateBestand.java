




package db.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.backend.DB_Buecherverleih;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;

public class UpdateBestand {
	private Object[] reihe;
	private DefaultTableModel model;
	private DB_Buecherverleih db;
	private JFrame updateFrame;

	public UpdateBestand(DB_Buecherverleih db) {

		this.db = db;
		JTable table = new JTable();

		Object[] zeilen = { "Titel", "ISBN", "Bestand" };
		model = new DefaultTableModel() {

			// Einzelne Zellen koennen nicht mehr bearbeitet werden
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		updateFrame = new JFrame("Update Bestand");

		reihe = new Object[6];

		updateFrame.getContentPane().setBackground(new Color(221, 224, 229));
		updateFrame.getContentPane().setForeground(Color.WHITE);
		updateFrame.setSize(new Dimension(1280, 720));
		updateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateFrame.getContentPane().setLayout(null);
		updateFrame.setLocationRelativeTo(null);
		model.setColumnIdentifiers(zeilen);

		table.setModel(model);
		table.setBackground(Color.white);
		table.setForeground(Color.black);

		table.setSelectionBackground(Color.red);
		table.setGridColor(Color.BLACK);
		table.setSelectionForeground(Color.white);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setRowHeight(30);
		table.setAutoCreateRowSorter(true);
		/// Tabelle erstellen
		BuecherAnzeigenV2(0);
		///

		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(381, 89, 834, 584);
		updateFrame.getContentPane().add(pane);

		JLabel updateLabel = new JLabel("Buch auswählen");
		updateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		updateLabel.setForeground(new Color(0, 0, 0));
		updateLabel.setBounds(42, 60, 178, 45);
		updateFrame.getContentPane().add(updateLabel);

		JButton updateButtonHoch = new JButton("Bestand +1");
		updateButtonHoch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int i = table.getSelectedRow();
				String titel = table.getValueAt(i, 0).toString();
				String isbn = table.getValueAt(i, 1).toString();
				String bestand = table.getValueAt(i, 2).toString();
				System.out.println("...............................................................");
				System.out.println("Titel: " + titel);
				System.out.println("isbn: " + isbn);
				System.out.println("Bestand: " + bestand);
//				System.out.println("sprache: " +sprache);
//				System.out.println("auflage: " +auflage);

				System.out.println(i);

				if (i >= 0 && isbn != null) {
					try {
						db.addExemplar(isbn);
						model.setRowCount(0);
						BuecherAnzeigenV2(1);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(updateFrame, "Titel nicht vorhanden");
					}
				} else {
					JOptionPane.showMessageDialog(updateFrame, "Titel nicht vorhanden");
				}

			}
		});
		updateButtonHoch.setBounds(52, 281, 278, 55);
		updateFrame.getContentPane().add(updateButtonHoch);

		JButton updateButtonRunter = new JButton("Bestand -1");
		updateButtonRunter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int i = table.getSelectedRow();
				String isbn = table.getValueAt(i, 1).toString();
				String bestand = table.getValueAt(i, 2).toString();
				System.out.println("...............................................................");
				System.out.println("isbn: " + isbn);
				System.out.println(i);

				if (i >= 0 && isbn != null) {
					try {
						if(Integer.parseInt(bestand) >= 1) {
						db.deleteExemplar(isbn);
						System.out.println("-----------------------------------");
						model.setRowCount(0);
						BuecherAnzeigenV2(2);
						} else {
							JOptionPane.showMessageDialog(updateFrame, "Der Bestand kann nicht weiter verringert werden!");
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(updateFrame, "Isbn nicht vorhanden");
					}
				} else {
					JOptionPane.showMessageDialog(updateFrame, "Isbn nicht vorhanden");
				}

			}
		});
		updateButtonRunter.setBounds(52, 381, 278, 55);
		updateFrame.getContentPane().add(updateButtonRunter);
		JButton menueButton = new JButton("Menü");
		menueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menue m = new Menue(db);
				updateFrame.dispose();
				m.getjMenue().setVisible(true);

			}
		});
		menueButton.setBounds(1072, 21, 145, 36);
		updateFrame.getContentPane().add(menueButton);

		updateFrame.revalidate();

		updateFrame.setVisible(true);

	}

	public JFrame getUpdateFrame() {
		return updateFrame;
	}

	public void BuecherAnzeigenV2(int index) {

//		System.out.println(db.getBuecher().toString());
//		System.out.println(db.getExemplare().toString());

		// ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand
		for (int i = 0; i < db.getBuecher().size(); i++) {

			LinkedHashMap<String, String> buch = db.getBuecher().get(i);
			for (String key : buch.keySet()) {

				if (key.equals("Titel")) {
					reihe[0] = buch.get(key);

				}
				if (key.equals("ISBN")) {
					reihe[1] = buch.get(key);
				}
				if (key.equals("Bestand")) {

					reihe[2] = buch.get(key).toString();
					model.addRow(reihe);

				}
			}
		}
	}

}



















