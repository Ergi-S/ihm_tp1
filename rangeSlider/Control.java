package rangeSlider;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/*
 * Controller
 */
public class Control implements MouseInputListener {

	Curseur[] ct;
	Frame f;

	enum State {
		Rien, Pressed, DraggedInf, DraggedSup
	}

	private State state = State.Rien;

	public Control(Curseur[] ct, Frame f) {
		this.ct = ct;
		this.f = f;
		this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].min).toString());
		this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[0].max).toString());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("Mouse Cliked");
		int ex = e.getX();
		int ey = e.getY();
		f.rect[0].x += 5;
		f.paint(f.getGraphics());
		if (ex >= f.rect[0].x && ex <= f.rect[0].x + f.rect[0].width && ey >= f.rect[0].y
				&& ey <= f.rect[0].y + f.rect[0].height)
			System.out.println("Curseur Inferieur");
		else if (ex >= f.rect[1].x && ex <= f.rect[1].x + f.rect[1].width && ey >= f.rect[1].y
				&& ey <= f.rect[1].y + f.rect[1].height)
			System.out.println("Curseur Superieur");
		else
			System.out.println("Aucun Curseur");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse Dragged");
		int ex = e.getX();
		int ey = e.getY();
		int posX;
		int posY;
		switch (state) {
		case Pressed:
			if (ex >= f.rect[0].x && ex <= f.rect[0].x + f.rect[0].width && ey >= f.rect[0].y
					&& ey <= f.rect[0].y + f.rect[0].height) {
				this.state = State.DraggedInf;
				if (ex > f.rect[1].x) {
					f.rect[0].x = f.rect[1].x;
				} else if (ex < f.xMin) {
					f.rect[0].x = f.xMin;
				} else {
					f.rect[0].x = ex;
				}
				ct[0].valeur = (f.rect[0].x - (f.xMin - ct[0].min)) / (((f.xMax - 30) - (f.xMin - ct[0].min)) / ct[0].max);
				this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
				f.paint(f.getGraphics());
				// posX = e.getX();
				// posY = e.getY();
			} else if (ex >= f.rect[1].x && ex <= f.rect[1].x + f.rect[1].width && ey >= f.rect[1].y
					&& ey <= f.rect[1].y + f.rect[1].height) {
				this.state = State.DraggedSup;
				if (ex < f.rect[0].x) {
					f.rect[1].x = f.rect[0].x;
				} else if (ex > f.xMax) {
					f.rect[1].x = f.xMax;
				} else {
					f.rect[1].x = ex;
				}
				ct[1].valeur = (f.rect[1].x - (f.xMin - ct[1].min)) / (((f.xMax - 30) - (f.xMin - ct[1].min)) / ct[1].max);
				this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
				f.paint(f.getGraphics());
			}
			break;
		case DraggedInf:
			this.state = State.DraggedInf;
			if (ex > f.rect[1].x) {
				f.rect[0].x = f.rect[1].x;
			} else if (ex < f.xMin) {
				f.rect[0].x = f.xMin;
			} else {
				f.rect[0].x = ex;
			}
			ct[0].valeur = (f.rect[0].x - (f.xMin - ct[0].min)) / (((f.xMax - 30) - (f.xMin - ct[0].min)) / ct[0].max);

			this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
			f.paint(f.getGraphics());
			break;
		case DraggedSup:
			this.state = State.DraggedSup;
			if (ex < f.rect[0].x) {
				f.rect[1].x = f.rect[0].x;
			} else if (ex > f.xMax-30) {
				f.rect[1].x = f.xMax-30;
			} else {
				f.rect[1].x = ex;
			}
			ct[1].valeur = (f.rect[1].x - (f.xMin - ct[1].min)) / (((f.xMax - 30) - (f.xMin - ct[1].min)) / ct[1].max);
			this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
			f.paint(f.getGraphics());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Moved");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (state) {
		case Rien:
			this.state = State.Pressed;
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.state = State.Rien;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Entered");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Exited");

	}
}
