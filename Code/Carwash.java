import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Hej

public class Carwash
{
   public static void main(String[] args)
   {
      new WashFrame();
   }
}

class WashFrame extends SwingWorker<Object, Object>
{
   private int screenwidth = 0;
   private int screenheight = 0;
   Font fontbig;
   Font fontmedium;
   Font fontsmall;
   JFrame frame;

   JPanel[] screens = null;

   public WashFrame()
   {
   	//Setting up our font setting
      fontbig = new Font("Arial", Font.PLAIN, 40);
      fontmedium = new Font("Arial", Font.PLAIN, 24);
      fontsmall = new Font("Arial", Font.PLAIN, 12);
   	//Prepares our frame, which we want to fill the entire screen
      frame = new JFrame();
      frame.setUndecorated(true);
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      gd.setFullScreenWindow(frame);
      frame.setLayout(null);
      frame.addWindowListener(
            new WindowAdapter() {
               public void windowClosed(WindowEvent e) {
                  cancel(true);
                  System.out.println("windowClosed");
                  Runtime.getRuntime().exit(0);
               }
            
               public void windowClosing(WindowEvent e) {
                  cancel(true);
                  System.out.println("windowClosing");
                  Runtime.getRuntime().exit(0);
               }
            });
   	//Saves our screen width / height
      screenwidth = frame.getWidth();
      screenheight = frame.getHeight();
   	//Creates our info screen
      this.execute();
   
   
   }

   public Object doInBackground() throws Exception
   {
      screens = new JPanel[3];
   
      screens[0] = new InsertCardScreen(this);
      screens[0].setBounds(0, 0, screenwidth, screenheight);
      screens[0].setVisible(false);
      frame.add(screens[0]);
   
      screens[1] = new InfoScreen(this);
      screens[1].setBounds(0, 0, screenwidth, screenheight);
      screens[1].setVisible(false);
      frame.add(screens[1]);
   
      screens[2] = new ErrorScreen(this);
      screens[2].setBounds(0, 0, screenwidth, screenheight);
      screens[2].setVisible(false);
      frame.add(screens[2]);
   
   
      int n = 0;
      System.out.println("doInBackground started");
      while(!isCancelled())
      {
         System.out.println("doInBackground 1");
         if(n % 2 == 0)
         {
            System.out.println("doInBackground 2");
            screens[1].setVisible(true);
            screens[2].setVisible(false);
         }
         else
         {
            System.out.println("doInBackground 3");
            screens[1].setVisible(false);
            screens[2].setVisible(true);
         }
         n++;
         System.out.println("doInBackground 4");
         Thread.sleep(1000);
      }
      System.out.println("doInBackground returning");
      return(null);
   }

   public int getWidth()
   {
      return(screenwidth);
   }

   public int getHeight()
   {
      return(screenheight);
   }

   public Font getFontBig()
   {
      return(fontbig);
   }

   public Font getFontMedium()
   {
      return(fontmedium);
   }

   public Font getFontSmall()
   {
      return(fontsmall);
   }

   public static Dimension getTextDimensions(Component c, Font font, String text)
   {
      FontMetrics metrics = c.getFontMetrics(font);
   	// get the height of a line of text in this
   	// font and render context
      int hgt = metrics.getHeight();
   	// get the advance of my text in this font
   	// and render context
      int adv = metrics.stringWidth(text);
   	// calculate the size of a box to hold the
   	// text with some padding.
      return(new Dimension(adv+2, hgt+2));
   }

	/**
	 Resets system dependencies.
	 - Reset the current user object
	 */
   public void SystemReset()
   {
   
   }

	/**
	 Checks that the washing hall is working.
	 Checks that there is an active connection to the mainframe
	 */
   public boolean SystemCheck()
   {
      return(true);
   }

}

class InsertCardScreen extends JPanel
{
   WashFrame wf;
   public InsertCardScreen(WashFrame _wf)
   {
      wf = _wf;
      this.setLayout(null);
   
      String blabel = "Insert washcard...";
   	//Creates a Label
      JLabel b=new JLabel(blabel);
   	//Sets our label font
      b.setFont(wf.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = wf.getTextDimensions(b, wf.getFontMedium(), blabel);
   	//Centers the label
      b.setBounds(_wf.getWidth()/2-(int)(labelsize.getWidth()/2),_wf.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());
      this.add(b);
   
      this.setBackground(Color.GREEN);
   }
}

class ErrorScreen extends JPanel
{
   WashFrame wf;
   public ErrorScreen(WashFrame _wf)
   {
      wf = _wf;
      this.setLayout(null);
   
      String blabel = "An Error occured.\nA technician has been contacted, please check back later.";
   	//Creates a Label
      JLabel b=new JLabel(blabel);
   	//Sets our label font
      b.setFont(wf.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = wf.getTextDimensions(b, wf.getFontMedium(), blabel);
   	//Centers the label
      b.setBounds(_wf.getWidth()/2-(int)(labelsize.getWidth()/2),_wf.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());
      this.add(b);
   
      this.setBackground(Color.GREEN);
   }
}

class InfoScreen extends JPanel
{
   WashFrame wf;
   public InfoScreen(WashFrame _wf)
   {
      wf = _wf;
      this.setLayout(null);
      JButton b=new JButton("Click Here");
      b.setBounds(50,100,95,30);
      b.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                  b.setText("Stop clicking me!");
               }
            });
      this.add(b);
      this.setBackground(Color.BLACK);
      JButton button = new JButton("bla");
      this.add(button);
   }
}
//virker//