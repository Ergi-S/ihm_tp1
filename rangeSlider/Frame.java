package rangeSlider;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

/*
 * Fenetre: View
 */
public class Frame extends JFrame {

	
	
	Rectangle rectInf = new Rectangle(100,100,30,30);
	Rectangle rectSupp = new Rectangle(600,100,30,30);
	Rectangle []rect = new Rectangle[] {rectInf,rectSupp};
	

	public Frame() {
		
		setSize(720, 400);
	
		
		setTitle("Range Slider");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRoundRect(90, 113, 550, 3, 10, 10);
		for (Rectangle c: rect) {
			g.drawRect(c.x, c.y, c.width, c.height);
		}
		
	}
}
