import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

enum Operation{
    STANDARD_WASH,
    EARLY_STANDARD_WASH,
    ECONOMY_WASH,
    EARLY_ECONOMY_WASH,
    DELUX_WASH,
    ABORT,
    RECHARGE,
    INSERT_CARD,
    RECEIPT,
    NO_RECEIPT,
    START_WASH
}

public class Terminal
{
   public static void main(String[] args)
   {
      new WashHallGUI();
   }
}

/**
Shows Insert card screen
*/
class InsertCardScreen extends JPanel
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

/**
Shows the early bird wash type menu
*/
class WashTypeEarlyBirdMenu extends JPanel
{
   WashHallGUI gui;
   JLabel balancelabel;   
   public WashTypeEarlyBirdMenu(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String balancelabeltext = "Balance " + gui.getWashHallManager().getAccount().getBalance();
   	//Creates a Label
      JLabel balancelabel = new JLabel(balancelabeltext);
   	//Sets our label font
      balancelabel.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
   	//Centers the label
      balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());  
      this.add(balancelabel);

      String selectwashtypelabeltext = "Please select wash type";
   	//Creates a Label
      JLabel selectwashtypelabel = new JLabel(selectwashtypelabeltext);
   	//Sets our label font
      selectwashtypelabel.setFont(gui.getFontBig());
   	//Calculate the size of the text in the label
      Dimension selectwashtypelabelsize = gui.getTextDimensions(balancelabel, gui.getFontBig(), selectwashtypelabeltext);
   	//Centers the label
      selectwashtypelabel.setBounds(10, 50,(int)selectwashtypelabelsize.getWidth(),(int)selectwashtypelabelsize.getHeight());  
      this.add(selectwashtypelabel);

      String buttonlabel0 = "Recharge wash card";
      JButton button0=new JButton(buttonlabel0); 
      button0.setFont(gui.getFontBig());   
      button0.setBounds(gui.getWidth()-10-400, 10, 400,50);  
      button0.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.RECHARGE);
            }
         });		
      this.add(button0);	
   	
      String buttonlabel1 = "De Luxe 120 kr";
      JButton button1=new JButton(buttonlabel1); 
      button1.setFont(gui.getFontBig());   
      button1.setBounds(10, 150, gui.getWidth()-20,50);  
      button1.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.DELUX_WASH);
            }
         });		
      this.add(button1);
      
      String buttonlabel2 = "Early Bird Special 64 kr";
      JButton button2=new JButton(buttonlabel2); 
      button2.setFont(gui.getFontBig());
      button2.setBounds(10, 230, gui.getWidth()-20,50);  
      button2.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.EARLY_STANDARD_WASH);
            }
         });		
      this.add(button2);	  

      String buttonlabel3 = "Early Bird Economy 40 kr";
      JButton button3=new JButton(buttonlabel3); 
      button3.setFont(gui.getFontBig());   
      button3.setBounds(10, 310, gui.getWidth()-20,50);  
      button3.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.EARLY_ECONOMY_WASH);
            }
         });		
      this.add(button3);	              

      String buttonlabel4 = "Abort purchase";
      JButton button4=new JButton(buttonlabel4); 
      button4.setFont(gui.getFontBig());   
      button4.setBounds(10, gui.getHeight()-60, gui.getWidth()-20,50);  
      button4.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.ABORT);
            }
         });		
      this.add(button4);	 
      
      this.setBackground(Color.GREEN);		
   }
   
   public void setVisible(boolean b)
   {   
      if(gui != null && balancelabel != null)
      {
         String balancelabeltext = "Balance " + Double.toString(((gui.getWashHallManager()).getAccount()).getBalance());         
         balancelabel.setText(balancelabeltext);
         balancelabel.setFont(gui.getFontMedium());
      	//Calculate the size of the text in the label
         Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
      	//Centers the label
         balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());       
      }    
      super.setVisible(b);
   }
}

/**
Shows normal wash type menu
*/
class WashTypeMenu extends JPanel
{
   
   WashHallGUI gui;
   JLabel balancelabel;
   public WashTypeMenu(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String balancelabeltext = "Balance " + gui.getWashHallManager().getAccount().getBalance();
   	//Creates a Label
      balancelabel = new JLabel(balancelabeltext);
   	//Sets our label font
      balancelabel.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
   	//Centers the label
      balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());  
      this.add(balancelabel);

      String selectwashtypelabeltext = "Please select wash type";
   	//Creates a Label
      JLabel selectwashtypelabel = new JLabel(selectwashtypelabeltext);
   	//Sets our label font
      selectwashtypelabel.setFont(gui.getFontBig());
   	//Calculate the size of the text in the label
      Dimension selectwashtypelabelsize = gui.getTextDimensions(balancelabel, gui.getFontBig(), selectwashtypelabeltext);
   	//Centers the label
      selectwashtypelabel.setBounds(10, 50,(int)selectwashtypelabelsize.getWidth(),(int)selectwashtypelabelsize.getHeight());  
      this.add(selectwashtypelabel);

      String buttonlabel0 = "Recharge wash card";
      JButton button0=new JButton(buttonlabel0); 
      button0.setFont(gui.getFontBig());   
      button0.setBounds(gui.getWidth()-10-400, 10, 400,50);  
      button0.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.RECHARGE);
            }
         });		
      this.add(button0);	
   	
      String buttonlabel1 = "De Luxe 120 kr";
      JButton button1=new JButton(buttonlabel1); 
      button1.setFont(gui.getFontBig());   
      button1.setBounds(10, 150, gui.getWidth()-20,50);  
      button1.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.DELUX_WASH);
            }
         });		
      this.add(button1);
      
      String buttonlabel2 = "Standard 80 kr";
      JButton button2=new JButton(buttonlabel2); 
      button2.setFont(gui.getFontBig());
      button2.setBounds(10, 230, gui.getWidth()-20,50);  
      button2.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.STANDARD_WASH);
            }
         });		
      this.add(button2);	  

      String buttonlabel3 = "Economy 50 kr";
      JButton button3=new JButton(buttonlabel3); 
      button3.setFont(gui.getFontBig());   
      button3.setBounds(10, 310, gui.getWidth()-20,50);  
      button3.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.ECONOMY_WASH);
            }
         });		
      this.add(button3);	              

      String buttonlabel4 = "Abort purchase";
      JButton button4=new JButton(buttonlabel4); 
      button4.setFont(gui.getFontBig());   
      button4.setBounds(10, gui.getHeight()-60, gui.getWidth()-20,50);  
      button4.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.ABORT);
            }
         });		
      this.add(button4);	 
      
      this.setBackground(Color.GREEN);		
   }

   public void setVisible(boolean b)
   {   
      if(gui != null && balancelabel != null)
      {
         String balancelabeltext = "Balance " + Double.toString(((gui.getWashHallManager()).getAccount()).getBalance());         
         balancelabel.setText(balancelabeltext);
         balancelabel.setFont(gui.getFontMedium());
      	//Calculate the size of the text in the label
         Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
      	//Centers the label
         balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());       
      }    
      super.setVisible(b);
   }
}

/**
Receipt Menu.
*/
class ReceiptMenu extends JPanel
{
   WashHallGUI gui;
   public ReceiptMenu(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String receiptlabeltext = "Do you wish a receipt for the purchase?";
   	//Creates a Label
      JLabel receiptlabel=new JLabel(receiptlabeltext);
   	//Sets our label font
      receiptlabel.setFont(gui.getFontBig());
   	//Calculate the size of the text in the label
      Dimension receiptlabelsize = gui.getTextDimensions(receiptlabel, gui.getFontBig(), receiptlabeltext);
   	//Centers the label
      receiptlabel.setBounds(gui.getWidth()/2-(int)(receiptlabelsize.getWidth()/2),gui.getHeight()/2-(int)(receiptlabelsize.getHeight()/2),(int)receiptlabelsize.getWidth(),(int)receiptlabelsize.getHeight());  
      this.add(receiptlabel);

      String buttonnotext = "No";
      JButton buttonno=new JButton(buttonnotext); 
      buttonno.setFont(gui.getFontBig());   
      buttonno.setBounds(10, gui.getHeight()-110, 300,100);  
      buttonno.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.NO_RECEIPT);
            }
         });		
      this.add(buttonno);      
      
      String buttonyestext = "Yes";
      JButton buttonyes=new JButton(buttonyestext); 
      buttonyes.setFont(gui.getFontBig());   
      buttonyes.setBounds(gui.getWidth()-10-300, gui.getHeight()-110, 300,100);  
      buttonyes.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.RECEIPT);
            }
         });		
      this.add(buttonyes);            
   }
}

/**
Shows error screen
*/
class ErrorScreen extends JPanel
{
   WashHallGUI gui;
   public ErrorScreen(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String blabel = "An Error occured.\nA technician has been contacted, please check back later.";
   	//Creates a Label
      JLabel b=new JLabel(blabel);
   	//Sets our label font
      b.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelsize = gui.getTextDimensions(b, gui.getFontMedium(), blabel);
   	//Centers the label
      b.setBounds(_gui.getWidth()/2-(int)(labelsize.getWidth()/2),_gui.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());  
      this.add(b);
   	
      this.setBackground(Color.GREEN);		
   }
}

/**
Shows instruction screen
*/
class InstructionScreen extends JPanel
{
   WashHallGUI gui;
   public InstructionScreen(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String labelline1text = "Please park your car in the washing hall";
   	//Creates a Label
      JLabel labelline1=new JLabel(labelline1text);
   	//Sets our label font
      labelline1.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelline1size = gui.getTextDimensions(labelline1, gui.getFontMedium(), labelline1text);
   	//Centers the label
      labelline1.setBounds(gui.getWidth()/2-(int)(labelline1size.getWidth()/2),gui.getHeight()/2-(int)(labelline1size.getHeight()/2)-30,(int)labelline1size.getWidth(),(int)labelline1size.getHeight());  
      this.add(labelline1);
      
      String labelline2text = "When your ready to start the wash, push the red button on the wall";
   	//Creates a Label
      JLabel labelline2=new JLabel(labelline2text);
   	//Sets our label font
      labelline2.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelline2size = gui.getTextDimensions(labelline2, gui.getFontMedium(), labelline2text);
   	//Centers the label
      labelline2.setBounds(gui.getWidth()/2-(int)(labelline2size.getWidth()/2),gui.getHeight()/2-(int)(labelline2size.getHeight()/2)+30,(int)labelline2size.getWidth(),(int)labelline2size.getHeight());  
      this.add(labelline2);
      
      String simbuttonlabel = "Simulate red button push";
      JButton simbutton=new JButton(simbuttonlabel); 
      simbutton.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the labInsertCardScreenel
      Dimension simbuttonlabelsize = gui.getTextDimensions(simbutton, gui.getFontMedium(), simbuttonlabel);       
      simbutton.setBounds(_gui.getWidth()/2-(int)(simbuttonlabelsize.getWidth()/2),_gui.getHeight()/2-(int)(simbuttonlabelsize.getHeight()/2)+100,(int)simbuttonlabelsize.getWidth()+40,(int)simbuttonlabelsize.getHeight());  
      simbutton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.START_WASH);
            }
         });		
      this.add(simbutton);	        
   }
}

/**
Shows wait screen
*/
class WaitScreen extends JPanel
{
   WashHallGUI gui;
   String availabletime;
   JLabel labelline2;
   public WaitScreen(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String labelline1text = "The washing system is currently busy";
   	//Creates a Label
      JLabel labelline1=new JLabel(labelline1text);
   	//Sets our label font
      labelline1.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelline1size = gui.getTextDimensions(labelline1, gui.getFontMedium(), labelline1text);
   	//Centers the label
      labelline1.setBounds(gui.getWidth()/2-(int)(labelline1size.getWidth()/2),gui.getHeight()/2-(int)(labelline1size.getHeight()/2)-30,(int)labelline1size.getWidth(),(int)labelline1size.getHeight());  
      this.add(labelline1);
      
      String labelline2text = "Next wash is available at " + gui.getWashHallManager().getWashDoneTime();

   	//Creates a Label
      labelline2=new JLabel(labelline2text);
   	//Sets our label font
      labelline2.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the label
      Dimension labelline2size = gui.getTextDimensions(labelline2, gui.getFontMedium(), labelline2text);
   	//Centers the label
      labelline2.setBounds(gui.getWidth()/2-(int)(labelline2size.getWidth()/2),gui.getHeight()/2-(int)(labelline2size.getHeight()/2)+30,(int)labelline2size.getWidth(),(int)labelline2size.getHeight());  
      this.add(labelline2);
      
      
      String simbuttonlabel = "Simulate Wash Hall done";
      JButton simbutton=new JButton(simbuttonlabel); 
      simbutton.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the labInsertCardScreenel
      Dimension simbuttonlabelsize = gui.getTextDimensions(simbutton, gui.getFontMedium(), simbuttonlabel);       
      simbutton.setBounds(_gui.getWidth()/2-(int)(simbuttonlabelsize.getWidth()/2),_gui.getHeight()/2-(int)(simbuttonlabelsize.getHeight()/2)+100,(int)simbuttonlabelsize.getWidth()+40,(int)simbuttonlabelsize.getHeight());  
      simbutton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.ABORT);
            }
         });		
      this.add(simbutton);	       
   }
     
   public void setVisible(boolean b)
   {
      if(gui != null && labelline2 != null)
      {
         String labelline2text = "Next wash is available at " + gui.getWashHallManager().getWashDoneTime();
         labelline2.setText(labelline2text);
         //Calculate the size of the text in the label
         Dimension labelline2size = gui.getTextDimensions(labelline2, gui.getFontMedium(), labelline2text);
         //Centers the label
         labelline2.setBounds(gui.getWidth()/2-(int)(labelline2size.getWidth()/2),gui.getHeight()/2-(int)(labelline2size.getHeight()/2)+30,(int)labelline2size.getWidth(),(int)labelline2size.getHeight());     
      }
      super.setVisible(b);
   }
}

/**
Shows wait screen
*/
class RechargeMenu extends JPanel
{
   WashHallGUI gui;
   public RechargeMenu(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
      String simbuttonlabel = "This part is not started yet, go back to start!";
      JButton simbutton=new JButton(simbuttonlabel); 
      simbutton.setFont(gui.getFontMedium());
   	//Calculate the size of the text in the labInsertCardScreenel
      Dimension simbuttonlabelsize = gui.getTextDimensions(simbutton, gui.getFontMedium(), simbuttonlabel);       
      simbutton.setBounds(_gui.getWidth()/2-(int)(simbuttonlabelsize.getWidth()/2),_gui.getHeight()/2-(int)(simbuttonlabelsize.getHeight()/2),(int)simbuttonlabelsize.getWidth()+40,(int)simbuttonlabelsize.getHeight());  
      simbutton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent e)
            {
               gui.runCommand(Operation.RECHARGE);
            }
         });		
      this.add(simbutton);	    
   }
}

// class WashType
// {
    // String name;
    // double price;
// }

class Account
{
   public double getBalance()
   {
      System.out.println("returning balance");
      return(100);
   }
}
