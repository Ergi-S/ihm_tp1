package rangeSlider;

import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{
		RangeSlider ra = new RangeSlider(100,500,0,20);
		JFrame f = new JFrame();
		f.setTitle("Range Slider");
		HomeView hv = new HomeView(10,ra.ct);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridLayout(2,2));
		f.add(ra.f);
		Button button = new Button("Valider");
		button.addActionListener(hv);
		f.add(button);
		f.add(hv);
		f.setVisible(true);
		f.setSize(1500, 800);	
	}
}
