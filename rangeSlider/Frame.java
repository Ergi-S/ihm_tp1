package rangeSlider;
import java.awt.Graphics;
import javax.swing.JFrame;

/*
 * Fenetre: View
 */
public class Frame extends JFrame {

	Curseur [] ct;
	
	public Frame(Curseur [] ct) {
		this.ct = ct;
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
		for (Curseur c: ct) {
			g.drawRect(c.x, c.y, c.w, c.h);
		}
	}
}
