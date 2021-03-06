import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




public class WstawPozFaktury extends JFrame {

	public static void main(String[] args) {
		new WstawPozFaktury();
	}
	
	private static  final int 
	WINDOW_LOCATION_X = 250,
	WINDOW_LOCATION_Y = 50,
	WINDOW_WIDTH = 550,
	WINDOW_HEIGHT = 100,
	WINDOW_GRID_ROWS = 3,
	WINDOW_GRID_COLS = 5;
	
	private static final JLabel 
	kodProduktu = new JLabel("Kod produktu", JLabel.LEFT),
	nazwaProduktu = new JLabel("Nazwa produktu", JLabel.LEFT), 
	ilosc = new JLabel("Ilość", JLabel.LEFT),
	cenaNetto = new JLabel("Cena netto", JLabel.LEFT), 
	vat = new JLabel("Vat", JLabel.LEFT);
	
	private static final JLabel[] 
			label_array = { kodProduktu, nazwaProduktu, ilosc, cenaNetto, vat };
	
	public static final JTextField 
	txt_kodProduktu = new JTextField(""), 
	txt_nazwaProduktu = new JTextField(""),
	txt_ilosc = new JTextField(""), 
	txt_cenaNetto = new JTextField(""),
	txt_vat = new JTextField("");
	
//	kontener do jtextfield
	public static JTextField[] jtxt_array = { txt_kodProduktu, txt_nazwaProduktu, txt_ilosc, txt_cenaNetto, txt_vat };
	
//	kontener do arraya z jtxtfield
	static HashMap contener_prodoktow = new HashMap();
	
	public static JButton
	zamknij = new JButton("zamknij"),
	wyczysc = new JButton("wyczyść"),
	zapisz  = new JButton("zapisz"),
	wstaw = new JButton("wstaw"),
	usun = new JButton("usuń");

	public static JComboBox cbox = new JComboBox(); 

	public WstawPozFaktury() {
		JFrame frame = new JFrame("Wstaw pozycję do faktury");
		//frame.setSize(300, 300);
		//frame.setLocation(500, 300);
		frame.setDefaultLookAndFeelDecorated(true);

		//JPanel panel = new JPanel();
		Container panel = frame.getContentPane();
		
		for(int i=0;i < label_array.length; i++) {
			panel.add(label_array[i]);
			jtxt_array[i].setColumns(4);
			panel.add(jtxt_array[i]);
		}
		
		frame.setLayout(new GridLayout(WINDOW_GRID_ROWS,WINDOW_GRID_COLS));
		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frame.setLocation(WINDOW_LOCATION_X,WINDOW_LOCATION_Y);
		
		panel.add(cbox);
		panel.add(wyczysc);
		panel.add(usun);
		panel.add(zapisz);
		panel.add(wstaw);
		//panel.add(zamknij);
		
		wyczysc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	//			petla zaczyna sie od jeden bo pierwszy jtxtfield nadaje nazwe
	//			calej tablicy i musi byc zachowana
				for(int i=0;i<Okno.jtxt_array.length;i++) {
					jtxt_array[i].setText("");
				}
			}
		});
		zapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nazwa_nabywcy = JOptionPane.showInputDialog(null,"Wpisz nazwę produktu","Zapisywanie danych", JOptionPane.PLAIN_MESSAGE);
				zapiszDane(nazwa_nabywcy);
			}
		});
		
		zamknij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		cbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox t = (JComboBox)e.getSource();
				if(t.getSelectedItem()!="wybierz odbiorce")
					wczytajDane((String)t.getSelectedItem());
			}
		});
		usun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usunDane((String)cbox.getSelectedItem());
			}
		});
		wstaw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Produkty prod = new Produkty(txt_kodProduktu.getText(),
					txt_nazwaProduktu.getText(),
					Integer.parseInt(txt_ilosc.getText()),
					Double.parseDouble(txt_cenaNetto.getText()),
					Integer.parseInt(txt_vat.getText())
					);
		DefaultTableModel model = (DefaultTableModel) (FormularzFaktury.tablePozFakt).getModel();
				Object [] rzad = new Object[6];
				rzad[0] = prod.getKodProduktu();
				rzad[1] = prod.getNazwaProduktu();
				rzad[2] = prod.getIlosc();
				rzad[3] = prod.getCenaNetto();
				rzad[4] = prod.getVat();
				rzad[5] = String.valueOf(prod.getCenaNetto()*prod.getVat()+prod.getCenaNetto());
			model.addRow(rzad);
			for (Object i:rzad) 
				System.out.println(i);
			}

		});

		frame.setVisible(true);

	}
	
	private void zapiszDane(String nazwa) {
		try {
//			wczytuje obiekt z pliku ktory jezeli nie istnieje to jest tworzony podczas startu programu
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tablica_nabywcow.txt"));
			contener_prodoktow = (HashMap)in.readObject();
			in.close();
//			dodaje nowe pola i zapisuje spowrotem
			if(contener_prodoktow.containsKey(nazwa))
				throw new MyException();
			cbox.addItem(nazwa);
			contener_prodoktow.put(nazwa, jtxt_array);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tablica_nabywcow.txt"));
			out.writeObject(contener_prodoktow);
			out.close();
		}
		catch(MyException E) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd: podana nazwa już istnieje","Podaj inną nazwę",JOptionPane.WARNING_MESSAGE);
		}
		catch(ClassCastException E) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd: "+E,"Błąd",JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception ioE) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd: podczas odczytu/zapisu pliku: "+ioE,"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	}
	
	private void usunDane(String klucz){
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tablica_nabywcow.txt"));
			contener_prodoktow = (HashMap)in.readObject();
			in.close();
			contener_prodoktow.remove(klucz);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tablica_nabywcow.txt"));
			out.writeObject(contener_prodoktow);
			out.close();
			cbox.removeItem(klucz);
		}
		catch(ClassCastException E) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd:  "+E,"Błąd",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException ioE) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd podczas odczytu/zapisu pliku: "+ioE,"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		catch(ClassNotFoundException E) {
			JOptionPane.showMessageDialog(null,"Błąd w strukturze pliku tablica_nabywcow.txt usuń ten plik i uruchom ponownie program UWAGA utracisz dane odbiorców."+E,"Błąd", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void wczytajDane(String klucz) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tablica_nabywcow.txt"));
			contener_prodoktow = (HashMap)in.readObject();
			String[] t = (String[])contener_prodoktow.keySet().toArray(new String[contener_prodoktow.size()]);
			JTextField[] tmp_array = (JTextField[])contener_prodoktow.get(klucz);
			for(int i=0;i<tmp_array.length;i++) {
				jtxt_array[i].setText(tmp_array[i].getText());
			}
		}
		catch(Exception E) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd:  "+E,"Błąd",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void zapisz() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tablica_nabywcow.txt"));
			out.writeObject(contener_prodoktow);
			out.close();
		}
		catch(Exception E) {
			JOptionPane.showMessageDialog(null,"Wystąpił błąd: "+E,"Błąd",JOptionPane.ERROR_MESSAGE);
		}
	}
	class MyException extends Exception {
		//static final long serialVersionUID = 15l; 
	}
	
	
	
	
}
