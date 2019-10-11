package rangeSlider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Fenetre: View
 */
public class Frame extends JFrame {

	
	Rectangle []rect;
	public final int xMin = 100;
	public final int xMax = 500;
	public JLabel vCurseurInf = new JLabel("0");
	public JLabel vCurseurSup = new JLabel("0");
	

	public Frame() {
		

		Rectangle rectInf = new Rectangle(xMin,100,30,30);
		Rectangle rectSupp = new Rectangle(xMax-30,100,30,30);
		rect = new Rectangle[] {rectInf,rectSupp};
		JPanel panel = (JPanel) this.getContentPane();
		panel.add(vCurseurInf,BorderLayout.WEST);
		panel.add(vCurseurSup, BorderLayout.EAST);
		setSize(720, 200);	
		setTitle("Range Slider");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		setResizable(false);
		setVisible(true);
		this.paint(this.getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(xMin, 113, xMax-xMin, 3);
		g.setColor(Color.RED);
		for (Rectangle c: rect) {
			g.fillRect(c.x, c.y, c.width, c.height);
		}
		
	}
}
