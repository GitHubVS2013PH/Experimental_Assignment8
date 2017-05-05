import java.awt.*;
import javax.swing.JPanel;

/**
 * Override's JPanel's paintComponent() method
 * and draws a line.
 */
public class MyJPanelDemo01 extends JPanel
{
    int xAxisBegin, xAxisEnd, yAxisBegin, yAxisEnd;
    final int MARGIN = 40, TICK_SIZE = 10, POINT_SIZE = 10;
    final int DATA_SHIFT = 35, DATE_SHIFT = 13;

    final double TEST_MAX_Y = 100.0;

    private static Color[] colorArray = {Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.green, Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red, Color.yellow};
    // Removed: color.white and color.gray

    // To specify our own Graphics objects we want to paint on panel,
    // we override method inherited from JComponent
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // g.drawLine(20, 20, 300, 20);

        // My test code
        // initialize graph variables
        xAxisBegin = MARGIN;
        yAxisBegin = getHeight() - MARGIN;
        xAxisEnd = getWidth() - MARGIN;
        yAxisEnd = MARGIN;

        int width = getWidth(), height = getHeight();
        System.out.println("Width: " + width + " Height: " + height);

        // draw x-axis and ticks
        g.drawLine(xAxisBegin, yAxisBegin, xAxisEnd, yAxisBegin);
        DrawXAxisTicks(4, 2000, g);

        // draw y-axis and ticks
        g.drawLine(xAxisBegin, yAxisBegin, xAxisBegin, yAxisEnd);
        DrawYAxisTicks(5, TEST_MAX_Y, g);

        g.drawString("Test", 300,300);
        g.fillOval(400, 400, POINT_SIZE, POINT_SIZE);

        // testing ColorPoint including graphing it
        ColoredPoint myPnt = new ColoredPoint(Color.BLUE, 400,400,2014,20.4);
        g.setColor(myPnt.getColor());
        g.fillOval((int)myPnt.getX(), (int)myPnt.getY(), POINT_SIZE, POINT_SIZE);
        g.setColor(Color.BLACK);
        g.drawString(myPnt.getLabel(),400,400);

        // test colors
        TestColorDots(g);



    }

    // DOESN'T WORK FOR NUMYEARS = 1
    private void DrawXAxisTicks(int numYears, int startYear, Graphics g) {
        if (numYears == 1)
            return;
        double spacing = (xAxisEnd - xAxisBegin) / ((double) numYears - 1);
        int tickTop = yAxisBegin - TICK_SIZE / 2;
        int tickBtm = yAxisBegin + TICK_SIZE / 2;
        for (int i = 0; i < numYears; ++i) {
            int xPos = xAxisBegin + (int) (i * spacing);
            g.drawLine(xPos, tickTop, xPos, tickBtm);
            g.drawString(Integer.toString(startYear + i), xPos - DATE_SHIFT, yAxisBegin + 2*TICK_SIZE);
        }
    }

    private void DrawYAxisTicks(int numIntervals, double maxY, Graphics g) {
        if (numIntervals == 1)
            return;
        double spacing = (yAxisBegin - yAxisEnd) / ((double)numIntervals - 1);
        double yDelta = maxY / ((double)numIntervals - 1 );
        int tickLeft = xAxisBegin - TICK_SIZE / 2;
        int tickRight = xAxisBegin + TICK_SIZE / 2;
        for (int i = 0; i < numIntervals; ++i) {
            int yPos = yAxisBegin - (int)(i * spacing);
            g.drawLine(tickLeft, yPos, tickRight, yPos);
            String dataStr = String.format("%5.1f", yDelta * i);             // MAKE THIS DYNAMIC BASED ON MAGNITUDE
            g.drawString(dataStr, xAxisBegin - DATA_SHIFT, yPos + TICK_SIZE / 2);
        }
    }

    private void TestColorDots(Graphics g) {
        Color oldColor = g.getColor();
        for (int i = 0; i < colorArray.length; ++i) {
            g.setColor(colorArray[i]);
            g.fillOval(xAxisEnd, yAxisBegin - (i + 3) * 3 * POINT_SIZE, POINT_SIZE, POINT_SIZE);
        }
        g.setColor(oldColor);
    }
}
