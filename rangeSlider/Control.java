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

	/*
	 * Etat de la machine à état
	 * 
	 */
	enum State {
		Rien, Pressed, DraggedInf, DraggedSup, PressedInf, PressedSup
	}

	private State state = State.Rien;

	public Control(Curseur[] ct, Frame f) {
		this.ct = ct;
		this.ct[0].valeur = ct[0].min;
		this.ct[1].valeur = ct[1].max;
		this.f = f;
		this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
		this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("Mouse Cliked");
		int ex = e.getX();
		int ey = e.getY(); //Récupère les coordonnés
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
		// System.out.println("Mouse Dragged");
		int ex = e.getX(); //récupère les coordonnés
		int ey = e.getY();
		int posX;
		int posY;
		switch (state) {
		case Pressed: 

			if (f.rect[0].contains(ex, ey)) { // on test si on click sur le curseur inférieur
				this.state = State.DraggedInf;
				if (ex > f.rect[1].x) {
					f.rect[0].x = f.rect[1].x;
				} else if (ex < f.xMin) {
					f.rect[0].x = f.xMin;
				} else {
					f.rect[0].x = ex;
				}
				ct[0].valeur = ct[0].min + ((ct[0].max - ct[0].min) / (f.xMax - f.xMin)) * (f.rect[0].x - f.xMin);  //on met à jour la valeur en fonction de la pos en x
				this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
				ct[0].isSelected = true;
				ct[1].isSelected = false;
				f.paint(f.getGraphics());
			} else if (f.rect[1].contains(ex, ey)) { //On test si on click sur le curseur suppérieur
				this.state = State.DraggedSup;
				if (ex < f.rect[0].x) {
					f.rect[1].x = f.rect[0].x;
				} else if (ex > f.xMax) {
					f.rect[1].x = f.xMax;
				} else {
					f.rect[1].x = ex;
				}
				ct[0].isSelected = false;
				ct[1].isSelected = true;
				ct[1].valeur = (int) (ct[1].min + ((ct[1].max - ct[1].min) / ((float) ((f.xMax - 30) - f.xMin)) * ((f.rect[1].x) - f.xMin))); 
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
			ct[0].valeur = (int) (ct[0].min
					+ ((ct[0].max - ct[0].min) / ((float) ((f.xMax - 30) - f.xMin)) * ((f.rect[0].x) - f.xMin)));
			this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
			f.paint(f.getGraphics());
			break;
		case DraggedSup:
			this.state = State.DraggedSup;
			if (ex < f.rect[0].x) {
				f.rect[1].x = f.rect[0].x;
			} else if (ex > f.xMax - 30) {
				f.rect[1].x = f.xMax - 30;
			} else {
				f.rect[1].x = ex;
			}
			ct[1].valeur = (int) (ct[1].min
					+ ((ct[1].max - ct[1].min) / ((float) ((f.xMax - 30) - f.xMin)) * ((f.rect[1].x) - f.xMin)));
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
		f.requestFocusInWindow();
		int ex = e.getX();
		int ey = e.getY();
		switch (state) {
		case Rien:
			this.state = State.Pressed;     //  Cette étape permet de selectionner le curseur si on veut le bouger avec les touches du clavier
			if(f.rect[0].contains(ex,ey)) {
				ct[0].isSelected = true;
				ct[1].isSelected = false;
			} else if (f.rect[1].contains(ex,ey)) {
				ct[0].isSelected = false;
				ct[1].isSelected = true;
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();
		switch (state) {
		case Pressed:   // On veut pouvoir clicker sur le rangeslider et placer le curseur le plus pres
			if (!f.rect[0].contains(ex, ey) && !f.rect[1].contains(ex, ey)) {
				if ((Math.abs(f.rect[0].x - ex) <= Math.abs(f.rect[1].x - ex)) && ex > f.rect[0].x
						&& ex < f.rect[1].x) {
					if (ex < f.xMin) {
						f.rect[0].x = f.xMin;
					} else {
						f.rect[0].x = ex;
					}
					ct[0].valeur = (int) (ct[0].min + ((ct[0].max - ct[0].min) / ((float) ((f.xMax - 30) - f.xMin))
							* ((f.rect[0].x) - f.xMin)));  //On calcule la valeur du curseur en fonction de sa position en x
					ct[0].isSelected = true;
					ct[1].isSelected = false;
					this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
					f.paint(f.getGraphics());
					break;
				} else if (ex < f.rect[0].x) {
					if (ex < f.xMin) {
						f.rect[0].x = f.xMin;
					} else {
						f.rect[0].x = ex;
					}
					ct[0].valeur = (int) (ct[0].min + ((ct[0].max - ct[0].min) / ((float) ((f.xMax - 30) - f.xMin))
							* ((f.rect[0].x) - f.xMin)));
					ct[0].isSelected = true;
					ct[1].isSelected = false;
					this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
					f.paint(f.getGraphics());
					break;
				} else {
					if (ex > f.xMax - 30) {
						f.rect[1].x = f.xMax - 30;
					} else {
						f.rect[1].x = ex;
					}
					ct[1].valeur = (int) (ct[1].min + ((ct[1].max - ct[1].min) / ((float) ((f.xMax - 30) - f.xMin))
							* ((f.rect[1].x) - f.xMin)));
					ct[0].isSelected = false;
					ct[1].isSelected = true;
					this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
					f.paint(f.getGraphics());
					break;
				}
			}
		default:
			this.state = State.Rien;
			break;
		}

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

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key, pas;
		if (ct[0].isSelected == true) {  
			pas = (f.xMax - 30 - f.xMin) / (ct[0].max - ct[0].min); //On calcule le pas en fonction de la longueur du range slider et de la valeur max et min des curseurs
			if ((key = e.getKeyCode()) == KeyEvent.VK_LEFT) {
				//System.out.println("Curseur droit touche left");
				if (f.rect[0].x - pas < f.xMin) {
					f.rect[0].x = f.xMin;
					ct[0].valeur = ct[0].min;
				} else {
					ct[0].valeur--; //Incrémente la valeur si on le peut
					f.rect[0].x -= pas;

				}
				this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
			}
			if ((key = e.getKeyCode()) == KeyEvent.VK_RIGHT) {
				//System.out.println("Curseur droit touche right");
				if (f.rect[0].x + pas > f.xMax - 30) {
					f.rect[0].x = f.xMax - 30;
				} else if (f.rect[0].x + pas > f.rect[1].x) {
					f.rect[0].x = f.rect[1].x;
					ct[0].valeur = ct[1].valeur;
				} else if (f.rect[0].x + pas > f.xMax - 30) {
					f.rect[0].x = f.xMax - 30;
					ct[0].valeur = ct[0].max;
				} else {
					ct[0].valeur++;
					f.rect[0].x += pas;
				}
				this.f.vCurseurInf.setText("Curseur A : " + new Integer(ct[0].valeur).toString());
			}
		} else if (ct[1].isSelected) {
			pas = (f.xMax - 30 - f.xMin) / (ct[1].max - ct[1].min);
			if ((key = e.getKeyCode()) == KeyEvent.VK_LEFT) {
				//System.out.println("Curseur gauche touche left");
				if (f.rect[1].x - pas < f.xMin) {
					f.rect[1].x = f.xMin;
				} else if (f.rect[1].x - pas < f.rect[0].x) {
					f.rect[1].x = f.rect[0].x;
					ct[1].valeur = ct[0].valeur;
				} else if (f.rect[1].x - pas < f.xMin) {
					f.rect[1].x = f.xMin;
					ct[1].valeur = ct[1].min;
					} 
				else {
					ct[1].valeur--;
					f.rect[1].x -= pas;

				}
				this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
			}
			if ((key = e.getKeyCode()) == KeyEvent.VK_RIGHT) {
				//System.out.println("Curseur gauche touche right");
				if (f.rect[1].x + pas > f.xMax - 30) {
					f.rect[1].x = f.xMax - 30;
					ct[1].valeur = ct[1].max;
				} else {
					ct[1].valeur++;
					f.rect[1].x += pas;

				}
				this.f.vCurseurSup.setText("Curseur B : " + new Integer(ct[1].valeur).toString());
			}
		}
		f.paint(f.getGraphics());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
