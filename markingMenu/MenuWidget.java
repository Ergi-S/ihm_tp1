package markingMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class MenuWidget extends JComponent {
	private Ellipse2D.Double menu;
	ArrayList<JMenu> items = new ArrayList<JMenu>();
	JPanel panel;
	

	public MenuWidget(Ellipse2D.Double m) {
		this.menu = m;
	}

	public MenuWidget(JPanel panel) {
		super();
		this.panel = panel;
		this.menu = new Ellipse2D.Double();
		this.setSize(100,100);
		this.setVisible(true);
	}

	public void drawMenu(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		System.out.println("Paint");
		if (this.isVisible()) {
			g2.setColor(Color.GREEN);
			g2.draw(menu);
			System.out.println("Visible");
		}

	}

	public void openMenu(MouseEvent e) {
		int eX = e.getPoint().x;
		int eY = e.getPoint().y;
		System.out.println("Open Menu");
		
		if (e.getButton() == 3) {
			System.out.println(e.getButton());
			this.menu.setFrame(eX, eY, 75, 75);
			this.setVisible(true);
			panel.repaint();
		}

	}


	public void closeMenu(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 3) {
			this.setVisible(false);
			panel.repaint();
		}

	}

}
