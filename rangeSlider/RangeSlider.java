package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;

	Curseur [] ct;

	private int curs_inf;
	private int curs_sup;

	public RangeSlider() {
		Curseur curs_inf = new Curseur(100, 100, 30, 30);
		Curseur curs_sup = new Curseur(600, 100, 30, 30);
		ct = new Curseur [] {curs_inf, curs_sup};
	}

	public void setCurseur(Curseur c, int v) throws Exception {
		switch(c) {
		case Inferieur : 
			curs_inf = v;
			break;
		case Superieur : 
			curs_sup = v;
			break;
		default : 
			throw new Exception("Erreur Curseur Inconnu");
		}
	}
}
