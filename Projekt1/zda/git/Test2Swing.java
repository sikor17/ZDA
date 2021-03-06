package zda.git;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

class Test2Swing extends JFrame {
	public static void main(String[] a) {
		new Test2Swing();
	}

	Test2Swing() {
		setLayout(new FlowLayout());
		JButton b = new JButton("Przycisk 1");
		JLabel l = new JLabel("Etykieta");
		JTextField tf = new JTextField("Pole tekstowe");
		getContentPane().add(b);
		getContentPane().add(l);
		getContentPane().add(tf);
		

		JComponent listaElementow[] = new JComponent[10];
		listaElementow[0] = new JButton("Przycisk 1");
		listaElementow[1] = new JToggleButton("Przycisk dwustanowy");
		listaElementow[2] = new JCheckBox("CheckBox");
		listaElementow[3] = new JRadioButton("RadioButton");
		listaElementow[4] = new JLabel("Etykieta");
		listaElementow[5] = new JTextField("Pole tekstowe");
		listaElementow[6] = new JPasswordField("Has�o");
		listaElementow[7] = new JFormattedTextField("Tekst sformatowany");
		listaElementow[8] = new JTextArea("Ramka tekstowa");
		listaElementow[9] = new JPanel();
		Container c = getContentPane();
		for (JComponent el : listaElementow){
		c.add(el);
	

		b.addActionListener(e -> tf.setText(""));
		tf.addActionListener(e ->{
		String txt = tf.getText();
		b.setText(txt);
		l.setText(txt);
		});
		
		
		
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JList<String> jl= new JList<String>(); jp1.add(jl);
		JTextField jtf= new JTextField("Pole tekstowe"); jp1.add(jtf);
		ArrayList<String> lista= new ArrayList<String>();
		jtf.addActionListener(e -> {
		String txt = jtf.getText();
		lista.add(txt);
		String[] tablica= lista.toArray(new String[lista.size()]);
		jl.setListData(tablica);
		});
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JSlider jsl= new JSlider(1, 10); jp2.add(jsl);
		JSpinner jsp= new JSpinner(); jp2.add(jsp);
		jsl.addChangeListener(e -> {
		jsp.setValue(jsl.getValue());
		});
		jsp.addChangeListener(e -> {
		SpinnerModel model = jsp.getModel();
		if(model instanceof SpinnerNumberModel) {
		SpinnerNumberModel numberModel= (SpinnerNumberModel) model;
		Number selectedNumber = numberModel.getNumber();
		jsl.setValue(selectedNumber.intValue());
		}
		});
		JPanel jp3 = new JPanel();
		String[] nazwy = new String[] { "A", "B", "C" };
		JComboBox<String> combo = new JComboBox<String>(nazwy);
		jp3.add(combo);
		JButton[] tabButt = new JButton[3];
		for (int i = 0; i < tabButt.length; i++) {
		tabButt[i] = new JButton(nazwy[i]);
		jp3.add(tabButt[i]);
		tabButt[i].setEnabled(false);
		}
		combo.addItemListener(e -> {
		for (JButton b1 : tabButt)
		b1.setEnabled(false);
		tabButt[combo.getSelectedIndex()].setEnabled(true);
		});
		
		getContentPane().add(jp1);
		getContentPane().add(jp2);
		getContentPane().add(jp3);
		
		}
		pack();
		setVisible(true);
	}
}
