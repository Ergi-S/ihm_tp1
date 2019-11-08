package markingMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.event.MouseInputListener;

public class MenuWidget extends JComponent implements MouseInputListener {
	private Ellipse2D.Double menu;
	ArrayList<JMenu> items = new ArrayList<JMenu>();
	

	public MenuWidget(Ellipse2D.Double m) {
		this.menu = m;
	}

	public MenuWidget() {
		super();
		this.menu = new Ellipse2D.Double();
		this.setSize(100,100);
		this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		System.out.println("Paint");
		if (this.isVisible()) {
			g2.setColor(Color.GREEN);
			g2.draw(menu);
			System.out.println("Visible");
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int eX = e.getPoint().x;
		int eY = e.getPoint().y;
		
		if (e.getButton() == 3) {
			System.out.println(e.getButton());
			this.menu.setFrame(eX, eY, 75, 75);
			this.setVisible(true);
			this.repaint();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 2) {
			this.setVisible(false);
			this.repaint();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
