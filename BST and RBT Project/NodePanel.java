

import java.lang.reflect.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



import java.util.*;

//*******************************************************************
// NodePanel
//
// Panel which paints the given nodes
//*******************************************************************	


	public class NodePanel extends JPanel
		{
		ArrayList<Dot> dots = new ArrayList<Dot>();
		Boolean SSNToggle;
		
		public NodePanel(ArrayList<Dot> Dots) {
			for(Dot d:Dots) {
				dots.add(d);
			}
			SSNToggle = false;
			this.repaint();
		}

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 3000, 1000);
			
			
			for (Dot dot: dots)
			{
				Graphics2D g2d = (Graphics2D) g;

				g.setColor(Color.BLACK);
				g.drawRect(dot.getX()-25, dot.getY()-25, dot.getRadius()*2, dot.getRadius()*2);
				g.setColor(dot.getColor());
				g.drawRect(dot.getX()-24, dot.getY()-24, dot.getRadius()*2, dot.getRadius()*2);
				g.setColor(Color.BLACK);

				if(dot.getHead()!=null) {
				
				g.drawLine(dot.getX(), dot.getY()-25, dot.getHead().getX(), dot.getHead().getY()+25);

				}

				g2d.setColor(Color.BLACK);

				int stlength= (int)( g2d.getFontMetrics().getStringBounds(dot.getDisplayString(), g2d).getWidth())/2;
				int stheight= (int) (g2d.getFontMetrics().getStringBounds(dot.getDisplayString(), g2d).getHeight())/2;
				if(!SSNToggle) {
					drawString(g2d,dot.getDisplayString(),dot.getX()-15,dot.getY()-30);
				}
				else {
					g2d.drawString(dot.getName(), dot.getX()-stlength+25, dot.getY()+stheight);
				}
				
			}
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(3000, 1000);
		}
		
		//*******************************************************************
		// drawString
		//
		// Draws the SSN of a person in the following format using FontMetrics
		// 	XXX-
		// 	XX-
		// 	XXXX
		//*******************************************************************	
		
		private void drawString(Graphics g, String text, int x, int y) {
				int stlength= (int)( g.getFontMetrics().getStringBounds(text, g).getWidth())/2;
				int stheight= (int) (g.getFontMetrics().getStringBounds(text, g).getHeight())/2;

			 
		        for (String line : text.split("\n"))
		            g.drawString(line, x, y += g.getFontMetrics().getHeight());
		    }
		//*******************************************************************
		// replaceList
		//
		// replaces the list being drawn with a new list.
		// then updates the panel by calling repaint on itself
		//*******************************************************************			 
		public void replaceList(ArrayList<Dot> dots2) {
			dots = new ArrayList <Dot>();
			for(Dot d: dots2) {
				dots.add(d);
			}
			this.repaint();
			
		}
		public boolean getToggle() {
			return SSNToggle;
		}
		//*******************************************************************
		// Toggle
		//
		// Toggles whether or not the panel should show the name or the SSN
		//*******************************************************************	
		public void Toggle() {
			if(!SSNToggle) {
				SSNToggle=true;
			}
			else {
				SSNToggle=false;
			}
			
		}
	}  
	
	