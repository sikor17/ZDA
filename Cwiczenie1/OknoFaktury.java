import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OknoFaktury extends JFrame {

	public static void main(String[] args) {
	new OknoFaktury();
	}
	
	private String[] kolumny;
	private Object[][] wiersze;
	private JTable tableFakt;
	public static DefaultTableModel tableModel1;

	private static final JLabel nazwaProgramu = new JLabel("Program do wystawiania faktur"), listaFaktur = new JLabel("Lista faktur:");
	private static final JButton dodaj = new JButton("dodaj faktur�"), edytuj = new JButton("edytuj faktur�"), usun = new JButton("usu� faktur�"), zamknij = new JButton("zamknij");
	
	public OknoFaktury() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Program do wystawiania faktur");
		frame.setSize(800, 750);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel1 = new JPanel();
		panel1.add(Box.createRigidArea(new Dimension(0, 80)));
		panel1.add(nazwaProgramu);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		nazwaProgramu.setFont(new Font("Courier New", Font.ITALIC, 30));

		
		JPanel panel2 = new JPanel();
		panel2.add(Box.createRigidArea(new Dimension(50, 00)));
		panel2.add(listaFaktur);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		kolumny = new String[] { "Data", "Numer Faktury", "Nabywca", "NIP", "Warto�� og�em", "Zamkni�ta" };
		// wiersze tabeli
		wiersze = new Object[][] {};
		tableFakt = new JTable(wiersze, kolumny);
		tableModel1 = new DefaultTableModel(wiersze, kolumny) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableFakt.setModel(tableModel1);

		JScrollPane panel3 = new JScrollPane(tableFakt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel4 = new JPanel();
		panel4.add(dodaj);
		
		dodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new FormularzFaktury();
				
			}
		});
		
		//panel4.add(edytuj);
		
		panel4.add(usun);
		
		usun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int zaznaczonyWiersz = tableFakt.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel) tableFakt.getModel();
					model.removeRow(tableFakt.getSelectedRow());
				} catch (java.lang.ArrayIndexOutOfBoundsException e2) {
					JOptionPane.showMessageDialog(panel, "Prosz� zaznaczy� wiersz", "Wiersz nie zaznaczony",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		panel4.add(zamknij);
		zamknij.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				System.exit(0);
				
			}
		});
		panel4.add(Box.createRigidArea(new Dimension(50, 00)));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));


		
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		frame.add(panel);
		
		pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
