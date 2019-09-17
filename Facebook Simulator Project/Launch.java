import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Launch extends JFrame{

	public static void main(String[] args) {
		new Launch();
	}
	
	public Launch() {
		super("Facebook simulator");
		final int WIDTH = 1000, HEIGHT = 700;
		
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		MainPanel p = new MainPanel(WIDTH,HEIGHT);
		this.add(p, BorderLayout.EAST);
		
		
		this.pack();
		this.setVisible(true);
	}

}
