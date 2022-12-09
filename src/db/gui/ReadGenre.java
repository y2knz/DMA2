package db.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * Genre über Drop Down Menü auswählen
	 * Buecher Anzeigen wie bei Delete
	 */
	private DefaultTableModel model;
	private JFrame readGFrame;
	private DB_Buecherverleih db;
	private Object [] reihe;
//	private JComboBox<String> genreComboBox;
	public ReadGenre(DB_Buecherverleih db) {
		
		this.db = db;
		
		JTable buecherTable = new JTable();
		//Richtige werte
		Object[] zeilen = { "ISBN", "Titel", "Genre_ID", "Verlag_ID", "Erscheinungsjahr", "Bestand" };
		model = new DefaultTableModel(){

			//Einzelne Zellen koennen nicht mehr bearbeitet werden
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
		
		buecherTable.setSelectionBackground(Color.red);
		buecherTable.setGridColor(Color.BLACK);
		buecherTable.setSelectionForeground(Color.white);
		buecherTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buecherTable.setRowHeight(30);
		buecherTable.setAutoCreateRowSorter(true);
		
		
		JScrollPane pane = new JScrollPane(buecherTable);
		pane.setForeground(Color.red);
		pane.setBackground(Color.white);
		pane.setBounds(283, 89, 932, 584);
		readGFrame.getContentPane().add(pane);
		
		String[] test = {"lorem,", "ipsum", "etc"};
		
//		genreComboBox = new JComboBox<String>(test);
//		genreComboBox.setSize(new Dimension(100,100));
//		readGFrame.getContentPane().add(genreComboBox);
		
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
		
		
		
		
		JComboBox<String> genreComboBox = new JComboBox <String>(test);
		genreComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		genreComboBox.setBounds(26, 152, 164, 47);
		readGFrame.getContentPane().add(genreComboBox);
		
		JButton auswaehlenButton = new JButton("Auswählen");
		auswaehlenButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		auswaehlenButton.setBounds(26, 265, 164, 47);
		readGFrame.getContentPane().add(auswaehlenButton);

		readGFrame.revalidate();

		readGFrame.setVisible(true);
		
		
		
		readGFrame.revalidate();
		readGFrame.setVisible(true);
		
	}
	
}
