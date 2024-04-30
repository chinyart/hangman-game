import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Write a description of class Congratulations here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Congratulations
{
    public static void main(String[] args) {
        JFrame f = new JFrame();
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("bg.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        f.pack();
        f.setVisible(true);
    }
}
