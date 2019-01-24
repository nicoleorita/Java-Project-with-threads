package trabalho;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUIClient {

	
	private JFrame frame;
	private JTextField avioesEspera, pista1, pista2;
	private JLabel esperaLabel, pistasLabel, pistaUmLabel, pistaDoisLabel;
	private JCheckBox aberta1, aberta2;
	private JPanel painelEspera, painelPistas;

	
	public GUIClient(){

		frame = new JFrame("Cliente");
		frame.setLayout(new BorderLayout());
		//frame.setLayout(new GridLayout(1, 2));

		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addContents();

		frame.pack();


	}

	void addContents(){
		
		//Aviões em espera
		avioesEspera = new JTextField();
		avioesEspera.setPreferredSize(new Dimension(300, 390));
		esperaLabel = new JLabel("Aviões em espera");

		painelEspera = new JPanel();
		painelEspera.setLayout(new BorderLayout());
		painelEspera.add(esperaLabel, BorderLayout.NORTH);
		painelEspera.add(avioesEspera, BorderLayout.SOUTH);

		frame.add(painelEspera, BorderLayout.WEST);
	
		
		//Pistas
		pistasLabel = new JLabel("Pistas");
		
		pistaUmLabel = new JLabel("Pista 1");
		pista1 = new JTextField();
		//pista1.setPreferredSize(new Dimension(400,50));
		aberta1 = new JCheckBox("aberta");
		
		pistaDoisLabel = new JLabel("Pista 1");
		pista2 = new JTextField();
		//pista2.setPreferredSize(new Dimension(400,50));
		aberta2 = new JCheckBox("aberta");
		
		
		painelPistas = new JPanel();
		painelPistas.setLayout(new GridLayout(7, 1));
		painelPistas.add(pistasLabel);
		painelPistas.add(pistaUmLabel);
		painelPistas.add(pista1);
		painelPistas.add(aberta1);
		painelPistas.add(pistaDoisLabel);
		painelPistas.add(pista2);
		painelPistas.add(aberta2);
		
		frame.add(painelPistas, BorderLayout.CENTER);
		//frame.add(painelPistas);
		
	}

	void open(){
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		GUIClient g = new GUIClient();
		g.open();
	}
}
