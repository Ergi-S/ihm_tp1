package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;

	Curseur [] ct;

	public RangeSlider() {
		Curseur curs_inf = new Curseur(100, 100, 30, 30);
		Curseur curs_sup = new Curseur(600, 100, 30, 30);
		ct = new Curseur [] {curs_inf, curs_sup};
	}

	void setc(Curseur c, int v) {
		
	}
}
