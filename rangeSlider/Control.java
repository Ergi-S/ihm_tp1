package rangeSlider;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

/*
 * Controller
 */
public class Control implements MouseInputListener, KeyListener {

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
		this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].max).toString());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("Mouse Cliked");
		int ex = e.getX();
		int ey = e.getY();
		f.paint(f.getGraphics());
		if (f.rect[0].contains(ex, ey))
			System.out.println("Curseur Inferieur");
		else if (f.rect[1].contains(ex, ey))
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
			if (f.rect[0].contains(ex, ey)) {
				this.state = State.DraggedInf;
				if (ex > f.rect[1].x) {
					f.rect[0].x = f.rect[1].x;
				} else if (ex < f.xMin) {
					f.rect[0].x = f.xMin;
				} else {
					f.rect[0].x = ex;
				}
				//ct[0].valeur = (f.rect[0].x - (f.xMin - ct[0].min)) / (((f.xMax - 30) - (f.xMin - ct[0].min)) / ct[0].max);
				ct[0].valeur = ct[0].min + ((ct[0].max - ct[0].min)/(f.xMax - f.xMin)) * (f.rect[0].x - f.xMin);
				this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
				f.paint(f.getGraphics());
				// posX = e.getX();
				// posY = e.getY();
			} else if (f.rect[1].contains(ex, ey)) {
				this.state = State.DraggedSup;
				if (ex < f.rect[0].x) {
					f.rect[1].x = f.rect[0].x;
				} else if (ex > f.xMax) {
					f.rect[1].x = f.xMax;
				} else {
					f.rect[1].x = ex;
				}
				ct[1].valeur = (int)(ct[1].min + ((ct[1].max - ct[1].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[1].x) - f.xMin)));
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
			ct[0].valeur = (int)(ct[0].min + ((ct[0].max - ct[0].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[0].x) - f.xMin)));
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
			ct[1].valeur = (int)(ct[1].min + ((ct[1].max - ct[1].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[1].x) - f.xMin)));
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
		int ex = e.getX();
		int ey = e.getY();
		switch(state) {
		case Pressed :
			if(!f.rect[0].contains(ex, ey) && !f.rect[1].contains(ex, ey)) {
				if((Math.abs(f.rect[0].x - ex) <= Math.abs(f.rect[1].x - ex)) && ex > f.rect[0].x && ex < f.rect[1].x ) {
					if (ex < f.xMin) {
						f.rect[0].x = f.xMin;
					} else {
						f.rect[0].x = ex;
					}
					ct[0].valeur = (int)(ct[0].min + ((ct[0].max - ct[0].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[0].x) - f.xMin)));
					this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
					f.paint(f.getGraphics());
					break;
				} else if(ex < f.rect[0].x){
					if (ex < f.xMin) {
						f.rect[0].x = f.xMin;
					} else {
						f.rect[0].x = ex;
					}
					ct[0].valeur = (int)(ct[0].min + ((ct[0].max - ct[0].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[0].x) - f.xMin)));
					this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
					f.paint(f.getGraphics());
					break;
				} else {
					if (ex > f.xMax-30) {
						f.rect[1].x = f.xMax-30;
					} else {
						f.rect[1].x = ex;
					}
					ct[1].valeur = (int)(ct[1].min + ((ct[1].max - ct[1].min)/((float)((f.xMax-30) - f.xMin)) * ((f.rect[1].x) - f.xMin)));
					this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
					f.paint(f.getGraphics());
					break;
					
				}
			}
		default:
			break;
		}
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
