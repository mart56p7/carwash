import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Shows InsertCardScreen

public class InsertCardScreen extends JPanel
{
    WashHallGUI gui;
    public InsertCardScreen(WashHallGUI _gui)
    {
        gui = _gui;
        this.setLayout(null);

        String blabel = "Input Washcard ID";
        JLabel b=new JLabel(blabel);
        b.setFont(gui.getFontMedium());
        Dimension labelsize = gui.getTextDimensions(b, gui.getFontMedium(), blabel);
        b.setBounds(_gui.getWidth()/2-(int)(labelsize.getWidth()/2),_gui.getHeight()/2-(int)(labelsize.getHeight()/2),(int)labelsize.getWidth(),(int)labelsize.getHeight());
        this.add(b);

        JTextField accountField = new JTextField();
        accountField.setBounds(_gui.getWidth()/2-50,_gui.getHeight()/2+50,100,30);
        this.add(accountField);

        String simbuttonlabel = "Simulate card insert";
        JButton simbutton=new JButton(simbuttonlabel);
        simbutton.setFont(gui.getFontMedium());
        Dimension simbuttonlabelsize = gui.getTextDimensions(simbutton, gui.getFontMedium(), simbuttonlabel);
        simbutton.setBounds(_gui.getWidth()/2-(int)(simbuttonlabelsize.getWidth()/2),_gui.getHeight()/2-(int)(simbuttonlabelsize.getHeight()/2)+100,(int)simbuttonlabelsize.getWidth()+40,(int)simbuttonlabelsize.getHeight());
        simbutton.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (WashCardReader.setCardId(accountField.getText())) {
                            gui.runCommand(Operation.CARD_INSERTED);
                        }


                    }
                });
        this.add(simbutton);

        this.setBackground(Color.GREEN);
    }
}
