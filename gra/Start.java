package samplesGUI.gra;

public class Start {

	public static void main(String[] args) {
		Data d = new Data();
		Mechanics m = new Mechanics(d);
		
		new UI(m);
	}

}