import javax.swing.*;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

public class Carwash
{
   public static void main(String[] args)
   {
      new WashFrame();
   }
}

class WashFrame extends JFrame
{
   public WashFrame()
   {
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      setUndecorated(true);
      gd.setFullScreenWindow(this);
      this.setVisible(true);
      add(new InsertCardCanvas());
   }
}

class InsertCardCanvas extends JPanel
{
   public InsertCardCanvas()
   {
      this.setLayout(null);
      setBackground(Color.BLACK);
      
   }
}