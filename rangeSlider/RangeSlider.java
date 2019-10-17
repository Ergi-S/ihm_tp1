package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;
	public Frame f;

	Curseur [] ct;

	enum TypeCursuer {
		Inferieur, Superieur
	}
	private int curs_inf;
	private int curs_sup;
	
	public RangeSlider(int xMin, int xMax, int min, int max) {
		f = new Frame(xMin,xMax);
		Curseur curs_inf = new Curseur(min,max);
		Curseur curs_sup = new Curseur(min,max);
		ct = new Curseur [] {curs_inf, curs_sup};
		Control c = new Control(ct,f);
		f.addMouseListener(c);
		f.addMouseMotionListener(c);
		f.addKeyListener(c);
	}

	public void setCurseur(int c, int v) throws Exception {
		switch(c) {
		case 0 : 
			curs_inf = v;
			break;
		case 1 : 
			curs_sup = v;
			break;
		default : 
			throw new Exception("Erreur Curseur Inconnu");
		}
	}
}
