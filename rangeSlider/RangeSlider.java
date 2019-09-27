package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;

	enum Curseur {
		Inferieur, Superieur
	}

	int curs_inf;
	int curs_sup;

	public RangeSlider() {
		curs_inf = min;
		curs_sup = max;
	}

	void setc(Curseur c, int v) {
		
	}
}
