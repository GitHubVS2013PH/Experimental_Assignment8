import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class TestGraphView {
    public static void main(String[] args) {
        final int WIDTH = 800, HEIGHT = 600;

        EventQueue.invokeLater(() -> {
            System.out.println("TestGraphView");

            GraphFrame graphFrame = new GraphFrame("World Development Indicators", WIDTH, HEIGHT);
            graphFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            graphFrame.setVisible(true);
        });
    }
}