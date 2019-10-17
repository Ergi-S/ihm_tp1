package rangeSlider;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class HomeView extends JPanel {

	Random r = new Random();
	int low = 200;
	int high = 920;

	public Home[] homes;

	public HomeView() {
		setSize(720, 720);	
		
		setFocusable(true);
		
		setVisible(true);
	}

	public void paint(Graphics g, int chambremin, int chambremax, int prixmin, int prixmax) {
		super.paint(g);
		for (Home h : homes) {
			if (h.nbChambres >= chambremin && h.nbChambres <= chambremax && h.prix >= prixmin && h.prix <= prixmax) {
				g.drawRect(r.nextInt(high - low) + low, r.nextInt(high - low) + low, 10, 10);
			}
		}
	}

}
