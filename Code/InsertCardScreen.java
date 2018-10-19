import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
/**
Shows Insert card screen
*/
public class InsertCardScreen extends JPanel
{
   WashHallGUI gui;
   public InsertCardScreen(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String blabel = "Insert washcard...";
   	//Creates a Label
      JLabel b=new JLabel(blabel);
   	//Sets our label font
      b.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = gui.getTextDimensions(b, gui.getFontMedium(), blabel);
   	//Centers the label
      b.setBounds(_gui.getWidth()/2-(int)(labelsize.getWidth()/2),_gui.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());  
      this.add(b);
   	
      String simbuttonlabel = "Simulate card insert";
      JButton simbutton=new JButton(simbuttonlabel); 
      simbutton.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the labInsertCardScreenel
      Dimension simbuttonlabelsize = gui.getTextDimensions(simbutton, gui.getFontMedium(), simbuttonlabel);       
      simbutton.setBounds(_gui.getWidth()/2-(int)(simbuttonlabelsize.getWidth()/2),_gui.getHeight()/2-(int)(simbuttonlabelsize.getHeight()/2)+50,(int)simbuttonlabelsize.getWidth()+40,(int)simbuttonlabelsize.getHeight());  
      simbutton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.INSERT_CARD);
            }
         });		
      this.add(simbutton);	  
      
      this.setBackground(Color.GREEN);		
   }
}