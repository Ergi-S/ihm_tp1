package rangeSlider;

public class Curseur {
	public int valeur;
	public int min,max;
	public boolean isSelected = false;  //Slection pour l'utilisation des touches du claviers
	
	public Curseur(int x) {
		this.valeur = x;
	}
	
	public Curseur(int min, int max) {
		this.min = min;
		this.max = max;
	}
}
