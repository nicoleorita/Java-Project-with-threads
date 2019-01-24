package ServerPack;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.WindowConstants;

import trabalho.Airport;
import trabalho.Plane;

public class GUIServer {

	private JFrame frame;
	private JTextField pista1, pista2;
	private JTextArea avioesEspera;
	private JLabel esperaLabel, pistasLabel, pistaUmLabel, pistaDoisLabel, labelFuncionamento;
	private JButton pistaUm, pistaDois;
	private JCheckBox aberta1, aberta2;
	private JPanel painelEspera, painelFuncionamento, painelPistas;
	private Airport airport;

	public GUIServer(Airport airport2){
		this.airport = airport2;
		
		frame = new JFrame("Servidor");
		frame.setLayout(new BorderLayout());
		
		frame.setResizable(false);

		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addContents();

		frame.pack();

		open();
	}

	void addContents(){

		//Aviões em espera
		avioesEspera = new JTextArea();
		avioesEspera.setPreferredSize(new Dimension(300, 390));
		esperaLabel = new JLabel("Aviões em espera");

		painelEspera = new JPanel();
		painelEspera.setLayout(new BorderLayout());
		painelEspera.add(esperaLabel, BorderLayout.NORTH);
		painelEspera.add(avioesEspera, BorderLayout.SOUTH);

		frame.add(painelEspera, BorderLayout.WEST);

		//Pistas em funcionamento
		pistaUm = new JButton("Pista 1");
		pistaUm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aberta1.setSelected(!aberta1.isSelected());
				if(aberta1.isSelected())
					airport.informRunway(false, 0);
				else
					airport.informRunway(true, 0);
			}
		});

		pistaDois = new JButton("Pista 2");
		pistaDois.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aberta2.setSelected(!aberta2.isSelected());
				if(aberta2.isSelected())
					airport.informRunway(false, 1);
				else 
					airport.informRunway(true, 1);
			}
		});

		labelFuncionamento = new JLabel("Pistas em funcionamento: ");

		painelFuncionamento = new JPanel();
		painelFuncionamento.setLayout(new BorderLayout());
		painelFuncionamento.add(labelFuncionamento, BorderLayout.NORTH);
		painelFuncionamento.add(pistaUm, BorderLayout.CENTER);
		painelFuncionamento.add(pistaDois, BorderLayout.SOUTH);

		frame.add(painelFuncionamento, BorderLayout.SOUTH);

		//Pistas
		pistasLabel = new JLabel("Pistas");

		pistaUmLabel = new JLabel("Pista 1");

		pista1 = new JTextField();

		aberta1 = new JCheckBox("fechada");
		aberta1.setEnabled(false);

		pistaDoisLabel = new JLabel("Pista 2");
		pista2 = new JTextField();
		
		aberta2 = new JCheckBox("fechada");
		aberta2.setEnabled(false);


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
	}

	public void open(){
		frame.setVisible(true);

	}

	public JTextArea getTextArea(){
		return avioesEspera;
	}

	public void refreshlist(LinkedList<Plane> l_planes_queue) {
		avioesEspera.setText("");
		for(Plane p : l_planes_queue){
			avioesEspera.append(p.toString()+"\n"); 
			}
	}

	public void refreshRunway(int runway, Plane plane) {
		if(runway == 0) {
			pista1.setText("");
			pista1.setText(plane.toString());
			pista1.repaint();
		}
		else {
			pista2.setText("");
			pista2.setText(plane.toString());
			pista2.repaint();
		}
	}
	
	public void clearRunway(int runway) {
		if(runway == 0)
			pista1.setText(" ");
		else
			pista2.setText(" ");
	}

	public void setAirport(Airport airport2) {
		this.airport=airport2;
	}
	
}

