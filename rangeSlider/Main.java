package rangeSlider;

public class Main {

	public static void main(String[] args) {
		RangeSlider r = new RangeSlider();
		Frame f = new Frame();
		Control c = new Control(r.ct,f);
		f.addMouseListener(c);
		f.addMouseMotionListener(c);
		f.addKeyListener(c);
	}
}
