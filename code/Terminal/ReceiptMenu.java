import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Receipt Menu.

public class ReceiptMenu extends JPanel
{
    WashHallGUI gui;
    public ReceiptMenu(WashHallGUI _gui)
    {
        gui = _gui;
        this.setLayout(null);

        String receiptlabeltext = "Would you like a receipt of your purchase?";
        JLabel receiptlabel=new JLabel(receiptlabeltext);
        receiptlabel.setFont(gui.getFontBig());
        Dimension receiptlabelsize = gui.getTextDimensions(receiptlabel, gui.getFontBig(), receiptlabeltext);
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
