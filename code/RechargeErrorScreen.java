import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Shows Insert card screen

public class RechargeErrorScreen extends JPanel
{
    WashHallGUI gui;
    public RechargeErrorScreen(WashHallGUI _gui)
    {
        gui = _gui;
        this.setLayout(null);

        String blabel = "Recharge of your Washcard failed...";
        JLabel b=new JLabel(blabel);
        b.setFont(gui.getFontMedium());
        Dimension labelsize = gui.getTextDimensions(b, gui.getFontMedium(), blabel);
        b.setBounds(_gui.getWidth()/2-(int)(labelsize.getWidth()/2),_gui.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());
        this.add(b);

        String buttonoklabel = "OK";
        JButton buttonok=new JButton(buttonoklabel);
        buttonok.setFont(gui.getFontBig());
        buttonok.setBounds(10, gui.getHeight()-60, gui.getWidth()-20,50);
        buttonok.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        gui.runCommand(Operation.CARD_INSERTED);
                    }
                });
        this.add(buttonok);

        this.setBackground(Color.GREEN);
    }
}
