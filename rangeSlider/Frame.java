package rangeSlider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Fenetre: View
 */
public class Frame extends JPanel {

	
	Rectangle []rect;
	public int xMin = 100;
	public int xMax = 500;
	public JLabel vCurseurInf = new JLabel("0");
	public JLabel vCurseurSup = new JLabel("0"); //Text pour afficher la valeur des curseurs
	

	public Frame(int xMin, int xMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		Rectangle rectInf = new Rectangle(xMin,100,30,30);
		Rectangle rectSupp = new Rectangle(xMax-30,100,30,30); //Curseur
		rect = new Rectangle[] {rectInf,rectSupp};
		this.add(vCurseurInf,BorderLayout.WEST);
		this.add(vCurseurSup, BorderLayout.EAST);
		this.setFocusable(true);
		requestFocusInWindow(); 
		
	}

	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(xMin, 113, xMax-xMin, 3);
		g.setColor(Color.RED);
		for (Rectangle c: rect) {
			g.fillRect(c.x, c.y, c.width, c.height);
		}
		
	}
}
