package rangeSlider;

public class Main {

	public static void main(String[] args) {
		RangeSlider r = new RangeSlider();
		Frame f = new Frame(r.ct);
		f.addMouseListener(new Control(r.ct, f));
	}

}
