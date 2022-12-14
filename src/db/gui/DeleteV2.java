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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;

public class DeleteV2 {
	private Object[] reihe;
	private DefaultTableModel model;
	private DB_Buecherverleih db;
	private JFrame deleteFrame;

	public DeleteV2(DB_Buecherverleih db) {
		
		
		
		this.db = db;
		JTable table = new JTable();

		Object[] zeilen = { "ISBN", "Titel", "Genre_ID", "Verlag_ID", "Erscheinungsjahr", "Bestand" };
		model = new DefaultTableModel(){

			//Einzelne Zellen koennen nicht mehr bearbeitet werden
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		      
		       return false;
		    }
		};

		deleteFrame = new JFrame("Delete Buch");

		reihe = new Object[6];

		deleteFrame.getContentPane().setBackground(new Color(221, 224, 229));
		deleteFrame.getContentPane().setForeground(Color.WHITE);
		deleteFrame.setSize(new Dimension(1280, 720));
		deleteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		deleteFrame.getContentPane().setLayout(null);
		deleteFrame.setLocationRelativeTo(null);
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
		buecherAnzeigenV2();
		///

		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(381, 89, 834, 584);
		deleteFrame.getContentPane().add(pane);

		JLabel deleteLabel = new JLabel("Buch ausw??hlen");
		deleteLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deleteLabel.setForeground(new Color(0,0,0));
		deleteLabel.setBounds(42, 60, 178, 45);
		deleteFrame.getContentPane().add(deleteLabel);

		

		JButton deleteButton = new JButton("L??schen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int i = table.getSelectedRow();
				String titel = table.getValueAt(i, 1).toString();
				
				System.out.println(titel);
				
				System.out.println(i);
				
				if(i >= 0 && titel != null) {
					try {
						db.deleteBuch(titel);
					}catch (Exception e) {
						JOptionPane.showMessageDialog(deleteFrame, "Titel nicht vorhanden");
						}
					
					model.removeRow(i);
				}else {
					JOptionPane.showMessageDialog(deleteFrame, "Titel nicht vorhanden");
				}

			}
		});
		deleteButton.setBounds(42, 281, 178, 45);
		deleteFrame.getContentPane().add(deleteButton);

		JButton menueButton = new JButton("Men??");
		menueButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Menue m = new Menue(db);
				deleteFrame.dispose();
				m.getjMenue().setVisible(true);

			}
		});
		menueButton.setBounds(1072, 21, 145, 36);
		deleteFrame.getContentPane().add(menueButton);

		deleteFrame.revalidate();

		deleteFrame.setVisible(true);

	}

	public JFrame getDeleteFrame() {
		return deleteFrame;
	}

	public void buecherAnzeigenV2() {
		
		System.out.println(db.getBuecher().toString());

		// ISBN, Titel, Genre_ID, Verlag_ID, Erscheinungsjahr, Bestand
		for (int i = 0; i < db.getBuecher().size(); i++) {

			LinkedHashMap<String, String> buch = db.getBuecher().get(i);
			

			for (String key : buch.keySet()) {
//				System.out.println(key);

				if (key.equals("ISBN")) {
//					System.out.println(buch.get(key));

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
