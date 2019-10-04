package rangeSlider;

/*
 * Modele
 */
public class RangeSlider {
	public static final int min = 0;
	public static final int max = 10;

	Curseur [] ct;

	enum TypeCursuer {
		Inferieur, Superieur
	}
	private int curs_inf;
	private int curs_sup;

	public RangeSlider() {
		Curseur curs_inf = new Curseur(0);
		Curseur curs_sup = new Curseur(10);
		ct = new Curseur [] {curs_inf, curs_sup};
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
