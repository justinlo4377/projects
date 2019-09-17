import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Launch extends JFrame{

	public static void main(String[] args) {
		new Launch();
	}
	
	public Launch() {
		super("BST and RB Tree SSN");
		final int WIDTH = 1115, HEIGHT = 630;
		
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
	
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		MainPanel p = new MainPanel(WIDTH,HEIGHT);
		this.add(p, BorderLayout.EAST);
		
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
