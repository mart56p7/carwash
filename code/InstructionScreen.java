import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

// Shows instruction screen

public class InstructionScreen extends JPanel
{
    WashHallGUI gui;
    public InstructionScreen(WashHallGUI _gui)
    {
        gui = _gui;
        this.setLayout(null);

        String labelline1text = "Please park your car in the washing hall";
        JLabel labelline1=new JLabel(labelline1text);
        labelline1.setFont(gui.getFontMedium());
        Dimension labelline1size = gui.getTextDimensions(labelline1, gui.getFontMedium(), labelline1text);
        labelline1.setBounds(gui.getWidth()/2-(int)(labelline1size.getWidth()/2),gui.getHeight()/2-(int)(labelline1size.getHeight()/2)-30,(int)labelline1size.getWidth(),(int)labelline1size.getHeight());
        this.add(labelline1);

        String labelline2text = "When your ready to start the wash, push the red button on the wall";
        JLabel labelline2=new JLabel(labelline2text);
        labelline2.setFont(gui.getFontMedium());
        Dimension labelline2size = gui.getTextDimensions(labelline2, gui.getFontMedium(), labelline2text);
        labelline2.setBounds(gui.getWidth()/2-(int)(labelline2size.getWidth()/2),gui.getHeight()/2-(int)(labelline2size.getHeight()/2)+30,(int)labelline2size.getWidth(),(int)labelline2size.getHeight());
        this.add(labelline2);

        String simbuttonlabel = "Simulate red button push";
        JButton simbutton=new JButton(simbuttonlabel);
        simbutton.setFont(gui.getFontMedium());
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
