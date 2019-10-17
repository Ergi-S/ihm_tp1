package rangeSlider;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class HomeView extends JPanel implements ActionListener {

	Random r = new Random();
	int low = 15;
	int high = 300;
	int taille = 10;
	Curseur[] c;

	public ArrayList<Home> homes = new ArrayList<Home>();

	public HomeView(int nb, Curseur[] c) {

		this.c = c;
		for (int i = 0; i < nb; i++) {
			homes.add(new Home(r.nextInt(21), r.nextInt(high - low) + low, r.nextInt(high - low) + low)); // On génère des maisons à des positions aléatoires ainsi qu'une nombre de pièces aléatoire
		}

		setSize(720, 720);

		setFocusable(true);

		setVisible(true);
	}

	public void paintComponent(Graphics g, int chambremin, int chambremax) {
		super.paint(g);
		for (Home h : homes) {
			if (h.nbChambres >= chambremin && h.nbChambres <= chambremax) {
				g.drawString(Integer.toString(h.nbChambres), h.longitude, h.latitude);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //Listener du click button
		this.paintComponent(this.getGraphics(), c[0].valeur, c[1].valeur);

	}

}
