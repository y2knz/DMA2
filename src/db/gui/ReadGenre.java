package db.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.backend.DB_Buecherverleih;
import javax.swing.JLabel;

public class ReadGenre {

	/*
	 * Genre über Drop Down Menü auswählen Buecher Anzeigen wie bei Delete
	 */
	private DefaultTableModel model;
	private JFrame readGFrame;
	private DB_Buecherverleih db;
	private Object[] reihe;

//	private JComboBox<String> genreComboBox;
	public ReadGenre(DB_Buecherverleih db) {

		this.db = db;

		JTable buecherTable = new JTable();
		// Richtige werte
		Object[] zeilen = { "ISBN", "Titel", "Genre_ID", "Verlag_ID", "Erscheinungsjahr", "Bestand" };
		model = new DefaultTableModel() {

			// Einzelne Zellen koennen nicht mehr bearbeitet werden
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		reihe = new Object[6];

		readGFrame = new JFrame("Read Genre");
		readGFrame.getContentPane().setBackground(new Color(221, 224, 229));
		readGFrame.getContentPane().setForeground(Color.WHITE);
		readGFrame.setSize(new Dimension(1280, 720));
		readGFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		readGFrame.getContentPane().setLayout(null);
		readGFrame.setLocationRelativeTo(null);
		model.setColumnIdentifiers(zeilen);

		buecherTable.setModel(model);
		buecherTable.setBackground(Color.white);
		buecherTable.setForeground(Color.black);

		buecherTable.setFocusable(false);
		buecherTable.setRowSelectionAllowed(false);

		buecherTable.setGridColor(Color.BLACK);

		buecherTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buecherTable.setRowHeight(30);
		buecherTable.setAutoCreateRowSorter(true);

		JScrollPane readGenrePane = new JScrollPane(buecherTable);
		readGenrePane.setForeground(Color.red);
		readGenrePane.setBackground(Color.white);
		readGenrePane.setBounds(283, 89, 932, 584);
		readGFrame.getContentPane().add(readGenrePane);

		ArrayList<String> genre = new ArrayList<>();

		for (int i = 0; i < db.getGenre().size(); i++) {

			LinkedHashMap<String, String> genreSchluessel = db.getGenre().get(i);

			genre.add(genreSchluessel.get("Bezeichnung"));
			System.out.println(genreSchluessel.get("Bezeichnung"));

		}

		String[] genreArray = new String[genre.size()];

		genreArray = genre.toArray(genreArray);

		JButton menueButton = new JButton("Menü");
		menueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menue m = new Menue(db);
				readGFrame.dispose();
				m.getjMenue().setVisible(true);

			}
		});
		menueButton.setBounds(1072, 21, 145, 36);
		readGFrame.getContentPane().add(menueButton);

		JLabel genreLabel = new JLabel("Genre auswählen");
		genreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		genreLabel.setBounds(26, 54, 198, 47);
		readGFrame.getContentPane().add(genreLabel);

		JComboBox<String> genreComboBox = new JComboBox<String>(genreArray);
		genreComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		genreComboBox.setBounds(26, 152, 164, 47);
		readGFrame.getContentPane().add(genreComboBox);

		JButton auswaehlenButton = new JButton("Auswählen");
		auswaehlenButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		auswaehlenButton.setBounds(26, 265, 164, 47);
		auswaehlenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				model.setRowCount(0);

				String genre = (String) genreComboBox.getSelectedItem();
				ArrayList<LinkedHashMap<String, String>> genreBuecher = db.getBuecherVonGenre(genre);

				genreBuecherAnzeigen(genreBuecher);

			}
		});
		readGFrame.getContentPane().add(auswaehlenButton);

		readGFrame.revalidate();

		readGFrame.setVisible(true);

		readGFrame.revalidate();
		readGFrame.setVisible(true);

	}

	public JFrame getReadGFrame() {
		return readGFrame;
	}

	public void genreBuecherAnzeigen(ArrayList<LinkedHashMap<String, String>> genreBuecher) {
		for (int i = 0; i < genreBuecher.size(); i++) {

			LinkedHashMap<String, String> buch = genreBuecher.get(i);

			for (String key : buch.keySet()) {
				System.out.println(key);

				if (key.equals("ISBN")) {

					reihe[0] = buch.get(key);

				}
				if (key.equals("Titel")) {
					reihe[1] = buch.get(key);

				}
				if (key.equals("Genre_ID")) {
					reihe[2] = buch.get(key);

				}
				if (key.equals("Verlag_ID")) {
					reihe[3] = buch.get(key);

				}
				if (key.equals("Erscheinungsjahr")) {
					reihe[4] = buch.get(key);

				}
				if (key.equals("Bestand")) {
					reihe[5] = buch.get(key);

					model.addRow(reihe);

				}

			}

		}
	}

}
