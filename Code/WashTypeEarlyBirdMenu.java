/**
Shows the early bird wash type menu
*/
public class WashTypeEarlyBirdMenu extends JPanel
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
               gui.runCommand(7);
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
               gui.runCommand(3);
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
               gui.runCommand(3);
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
               gui.runCommand(3);
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
               gui.runCommand(0);
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