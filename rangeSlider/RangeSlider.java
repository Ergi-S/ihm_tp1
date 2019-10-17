package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;
	public Frame f;
	public Control c;

	Curseur [] ct;
	
	public RangeSlider(int xMin, int xMax, int min, int max) {
		f = new Frame(xMin,xMax);
		Curseur curs_inf = new Curseur(min,max);
		Curseur curs_sup = new Curseur(min,max);
		ct = new Curseur [] {curs_inf, curs_sup};
		c = new Control(ct,f);
		f.addMouseListener(c);
		f.addMouseMotionListener(c);
		f.addKeyListener(c);
	}
}
