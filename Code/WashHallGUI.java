import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
class WashHallGUI extends SwingWorker<Object, Object>
{
   private int screenwidth = 0;
   private int screenheight = 0;
   Font fontbig;
   Font fontmedium;
   Font fontsmall;
   JFrame frame;
   WashHallManager whmanager;
   
   JPanel[] screens = null;
	
   Account account = null;
   
   public WashHallGUI()
   {
      //Object that contains all business logic
      whmanager = new WashHallManager(this);
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
      //We will close Java when the screen is exited
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
   	
      //Setting up our different screens
      screens = new JPanel[8];
   	
      screens[0] = new InsertCardScreen(this);
      screens[1] = new WashTypeEarlyBirdMenu(this);
      screens[2] = new WashTypeMenu(this);
      screens[3] = new ReceiptMenu(this);
      screens[4] = new ErrorScreen(this);
      screens[5] = new InstructionScreen(this);
      screens[6] = new WaitScreen(this);
      screens[7] = new RechargeMenu(this);
      
      for(int i = 0; i < screens.length; i++)
      {
         screens[i].setBounds(0, 0, screenwidth, screenheight);
         screens[i].setVisible(false);
         frame.add(screens[i]);
      }
      screens[0].setVisible(true);      
   	      
      //Creates our info screen
      //this.execute();
   
   }
   
   
   
   public Object doInBackground() throws Exception
   {  	
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
   
   public void runCommand(Operation o)
   {
      System.out.println("Run command started with " + o);
      int activescreen = whmanager.runCommand(o);
      for(int j = 0; j < screens.length; j++)
      {
         screens[j].setVisible(false);
      }
      screens[activescreen].setVisible(true);
   }
    
   public WashHallManager getWashHallManager()
   {
      return(whmanager);
   }
}
