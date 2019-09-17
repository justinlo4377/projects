
import java.lang.reflect.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

//*******************************************************************
//NodeDisplay
//
//Frame which paints the given nodes
//Is called upon when the button Enlarge is pressed
//*******************************************************************	

public class NodeDisplay extends JFrame implements ActionListener {
	private ArrayList<Dot> dots;
	private DotPanel dotPanel;
	private JButton quitBtn;
	Boolean SSNToggle;

	public NodeDisplay(boolean SSN, ArrayList<Dot> dots2) {

		SSNToggle = SSN;
		dots = dots2;
		dotPanel = new DotPanel();
		JScrollPane jp = new JScrollPane(dotPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jp.setPreferredSize(new Dimension(1200, 650));
		JViewport view = jp.getViewport();
		view.setViewPosition(new Point(900, 0)); // views in the center
		view.setSize(new Dimension(100, 100));
		view.setExtentSize(new Dimension(300, 300));
		jp.setViewport(view);
		;

		add(jp, BorderLayout.CENTER);
		JPanel controls = new JPanel();
		quitBtn = new JButton("Close Enlarged Tree");
		quitBtn.addActionListener(this);
		controls.add(quitBtn);
		add(controls, BorderLayout.NORTH);
		pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == quitBtn)
			this.dispose();

		dotPanel.repaint();
	}

	private class DotPanel extends JPanel {

		public Dimension getPreferredSize() {

			return new Dimension(3000, 1500);
		}

		public void paintComponent(Graphics g) {

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 3000, 1500);

			for (Dot dot : dots) {
				// Compute bounding box for the dot, and paint.

				Graphics2D g2d = (Graphics2D) g;

				g.setColor(Color.BLACK);
				g.drawRect(dot.getX() - 25, dot.getY() - 25, dot.getRadius() * 2, dot.getRadius() * 2);
				g.setColor(dot.getColor());
				g.drawRect(dot.getX() - 24, dot.getY() - 24, dot.getRadius() * 2, dot.getRadius() * 2);
				g.setColor(Color.BLACK);

				if (dot.getHead() != null) {

					g.drawLine(dot.getX(), dot.getY() - 25, dot.getHead().getX(), dot.getHead().getY() + 25);

				}

				g2d.setColor(Color.BLACK);

				int stlength = (int) (g2d.getFontMetrics().getStringBounds(dot.getDisplayString(), g2d).getWidth()) / 2;
				int stheight = (int) (g2d.getFontMetrics().getStringBounds(dot.getDisplayString(), g2d).getHeight())
						/ 2;

				if (!SSNToggle) {
					drawString(g2d, dot.getDisplayString(), dot.getX() - 15, dot.getY() - 30);
				} else {
					g2d.drawString(dot.getName(), dot.getX() - stlength + 25, dot.getY() + stheight);
				}

			}
		}
	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
}