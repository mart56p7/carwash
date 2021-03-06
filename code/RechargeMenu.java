import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Shows wait screen

public class RechargeMenu extends JPanel
{
   WashHallGUI gui;
   JLabel balancelabel;
   public RechargeMenu(WashHallGUI _gui)
   {
      gui = _gui;
      this.setLayout(null);
   
      String balancelabeltext = "";
      if (gui.getWashHallManager().getAccount() == null)
      {
         balancelabeltext = "Balance 0";
      }
      else
      {
         balancelabeltext = "Balance " +gui.getWashHallManager().getAccount().getBalance();
      }
   
      balancelabel = new JLabel(balancelabeltext);
      balancelabel.setFont(gui.getFontMedium());
      Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
      balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());
      this.add(balancelabel);
   
      String selectwashtypelabeltext = "Please select recharge amount:";
      JLabel selectwashtypelabel = new JLabel(selectwashtypelabeltext);
      selectwashtypelabel.setFont(gui.getFontBig());
      Dimension selectwashtypelabelsize = gui.getTextDimensions(balancelabel, gui.getFontBig(), selectwashtypelabeltext);
      selectwashtypelabel.setBounds(10, 50,(int)selectwashtypelabelsize.getWidth(),(int)selectwashtypelabelsize.getHeight());
      this.add(selectwashtypelabel);
   
      String buttonlabel1 = "1000 kr";
      JButton button1=new JButton(buttonlabel1);
      button1.setFont(gui.getFontBig());
      button1.setBounds(10, 150, gui.getWidth()-20,50);
      button1.addActionListener(
                new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                      gui.runCommand(Operation.RECHARGE_1000);
                   }
                });
      this.add(button1);
   
      String buttonlabel2 = "500 kr";
      JButton button2=new JButton(buttonlabel2);
      button2.setFont(gui.getFontBig());
      button2.setBounds(10, 230, gui.getWidth()-20,50);
      button2.addActionListener(
                new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                      gui.runCommand(Operation.RECHARGE_500);
                   }
                });
      this.add(button2);
   
      String buttonlabel3 = "200 kr";
      JButton button3=new JButton(buttonlabel3);
      button3.setFont(gui.getFontBig());
      button3.setBounds(10, 310, gui.getWidth()-20,50);
      button3.addActionListener(
                new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                      gui.runCommand(Operation.RECHARGE_200);
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
                      gui.runCommand(Operation.ABORT_RECHARGE);
                   }
                });
      this.add(button4);
   }

   public void setVisible(boolean b)
   {
      if(gui != null && balancelabel != null)
      {
         String balancelabeltext = "";
         if (gui.getWashHallManager().getAccount() == null){
            balancelabeltext = "Balance 0"; }
         else {
            balancelabeltext = "Balance " +gui.getWashHallManager().getAccount().getBalance();
         }
         balancelabel.setText(balancelabeltext);
         balancelabel.setFont(gui.getFontMedium());
         Dimension labelsize = gui.getTextDimensions(balancelabel, gui.getFontMedium(), balancelabeltext);
         balancelabel.setBounds(10, 10 ,(int)labelsize.getWidth(),(int)labelsize.getHeight());
      }
      super.setVisible(b);
   }
}
