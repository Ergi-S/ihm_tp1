package rangeSlider;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/*
 * Controller
 */
public class Control implements MouseInputListener {

	Curseur [] ct;
	Frame f;
	
	public Control(Curseur [] ct, Frame f) {
		this.ct = ct;
		this.f = f;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();
		if (ex >= ct[0].x && ex <= ct[0].x + ct[0].w && ey >= ct[0].y && ey <= ct[0].y + ct[0].h)
			System.out.println("Curseur Inferieur");
		else if (ex >= ct[1].x && ex <= ct[1].x + ct[1].w && ey >= ct[1].y && ey <= ct[1].y + ct[1].h)
			System.out.println("Curseur Superieur");
		else
			System.out.println("Aucun Curseur");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
