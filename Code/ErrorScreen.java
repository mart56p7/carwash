/**
Shows error screen
*/
public class ErrorScreen extends JPanel
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