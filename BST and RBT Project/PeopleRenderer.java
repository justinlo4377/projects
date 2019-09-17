import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.ImageIcon;

public class PeopleRenderer extends JLabel implements ListCellRenderer<Person> {
	Boolean b = false;
	Boolean c = true;

	//*******************************************************************
	// PeopleRenderer
	//
	// A customized listcellrenderer which only works on a person 
	// Will display the person's name and their icon 
	// Modified from facebook project to display the node's color instead of a person's profile.
	//*******************************************************************

	@Override
	public Component getListCellRendererComponent(JList<? extends Person> list, Person person, int index,
			boolean isSelected, boolean cellHasFocus) {

		if(person!=null) {
			
			ImageIcon i = new ImageIcon(person.getImage());
			
			setIcon(i);
			 setIconTextGap(20);
			 
			 this.setText(person.getName()+" "+ person.getListString());
			
			setOpaque(false);
			
			if (isSelected)

		      {
		        setBackground(list.getSelectionBackground());
		      }
		      else
		      {
		         setBackground(list.getBackground());
		      }

		      setFont(list.getFont());
		      
		      if (isSelected && cellHasFocus)
		         setBorder(BorderFactory.createEtchedBorder(1));
		      else
		    	  this.setBorder(BorderFactory.createEtchedBorder(0));
		      
		 
			}
			return this;
	}

}
