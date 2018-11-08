import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class ErrorScreen extends JPanel
{
   WashHallGUI gui;
   public ErrorScreen(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String blabel = "An Error occured.\nA technician has been contacted, please check back later.";
      JLabel b=new JLabel(blabel);
      b.setFont(gui.getFontMedium());
      Dimension labelsize = gui.getTextDimensions(b, gui.getFontMedium(), blabel);
      b.setBounds(_gui.getWidth()/2-(int)(labelsize.getWidth()/2),_gui.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());
      this.add(b);
   }
}
