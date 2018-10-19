/**
Receipt Menu.
*/
public class ReceiptMenu extends JPanel
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
               gui.runCommand(5);
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
               gui.runCommand(5);
            }
         });		
      this.add(buttonyes);            
   }
}