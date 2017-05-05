import javax.swing.*;
import java.awt.*;

/**
 * Created by Paul on 5/2/2017.
 */
public class TestGraphView {
    public static void main(String[] args) {
        System.out.println("TestGraphView");

        // establish main frame in which program will run:
        JFrame frmMyWindow = new JFrame("World Development Indicators");
        //frmMyWindow.setLocationRelativeTo(null);
        frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMyWindow.setSize(800, 600);
        // System.out.println("Window size is: " + frmMyWindow.getSize() + " width is: " + frmMyWindow.getWidth());

        // reposition jframe to middle of screen
        // repositioning code from: http://stackoverflow.com/questions/3480102/java-
        // jframe-setlocationrelativetonull-not-centering-the-window-on-ubuntu-10-0
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // System.out.println("ScreenSize is: " + screenSize);
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - (frmMyWindow.getWidth() / 2),
                middle.y - (frmMyWindow.getHeight() / 2));
        frmMyWindow.setLocation(newLocation);
        frmMyWindow.setVisible(true);

        MyJPanelDemo01 demo1 = new MyJPanelDemo01();
        frmMyWindow.add(demo1);
        demo1.repaint();


    }
}
