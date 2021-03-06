import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Okno extends JFrame {

	private Wizytowka w;

	public static void main(String[] args) {

		new Okno();

	}

	public Okno() {
		setSize(700, 400);
		setLocation(500, 300);

		setLayout(new FlowLayout());
		JLabel ln = new JLabel("nazwisko");
		JLabel lw = new JLabel("wzrost");
		JTextField tfNazwisko = new JTextField("", 20);
		JFormattedTextField tfWzrost = new JFormattedTextField(NumberFormat.getNumberInstance());
		JLabel ln1 = new JLabel("nazwisko");
		JLabel lwz1 = new JLabel("wzrost");
		JLabel txNazwisko = new JLabel("_____");
		JLabel txWzrost = new JLabel("_____");

		JPanel lewyPanel = new JPanel();
		lewyPanel.setLayout(new BoxLayout(lewyPanel, BoxLayout.Y_AXIS));
		JPanel prawyPanel = new JPanel();
		prawyPanel.setLayout(new BoxLayout(prawyPanel, BoxLayout.X_AXIS));
		JSplitPane sp = new JSplitPane();
		sp.setRightComponent(prawyPanel);
		sp.setLeftComponent(lewyPanel);
		sp.setSize(800, 500);
		getContentPane().add(sp);
		prawyPanel.add(ln1);
		prawyPanel.add(txNazwisko);
		prawyPanel.add(lwz1);
		prawyPanel.add(txWzrost);
		lewyPanel.add(ln);
		lewyPanel.add(tfNazwisko);
		lewyPanel.add(lw);
		lewyPanel.add(tfWzrost);

		JButton b = new JButton("zapisz");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nazw = tfNazwisko.getText();
				int wzrost = Integer.parseInt(tfWzrost.getText());
				w = new Wizytowka(nazw, wzrost);
			}
		});
		getContentPane().add(b);

		JButton c = new JButton("odczytaj");
		c.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nazw = w.getNazwisko();
				int wzrost = w.getWzrost();
				txNazwisko.setText(nazw);
				txWzrost.setText(wzrost + "cm");
			}
		});
		getContentPane().add(c);

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
