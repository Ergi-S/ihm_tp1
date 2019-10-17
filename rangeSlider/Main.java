package rangeSlider;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) 
	{
		RangeSlider ra = new RangeSlider(15,50,0,5);
		RangeSlider rb = new RangeSlider(15,50,0,20);
		JFrame f = new JFrame();
		JPanel rs = new JPanel(new BorderLayout());
		rs.add(ra.f,BorderLayout.NORTH);
		rs.add(rb.f,BorderLayout.SOUTH);
		f.add(rs,BorderLayout.EAST);
		f.setTitle("Range Slider");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
		f.setSize(1500, 800);	
		f.paint(f.getGraphics());
		
		
	}
}
