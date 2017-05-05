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
    final int MAX_X_INTERVALS = 10, NUM_Y_INTERVALS = 10;

    final double TEST_MAX_Y = 800;

    private static Color[] colorArray = {Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.green, Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red, Color.yellow};
    // Removed: color.white and color.gray

    // To specify our own Graphics objects we want to paint on panel,
    // we override method inherited from JComponent
    @Override
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
        DrawXAxisTicks(28, 2000, g);

        // draw y-axis and ticks
        g.drawLine(xAxisBegin, yAxisBegin, xAxisBegin, yAxisEnd);
        DrawYAxisTicksAndLabels(5, TEST_MAX_Y, g);

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

        // test findTopYValue
/*        System.out.println("findTopYValue of 1.0 is: " + findTopYValue(1.0));
        System.out.println("findTopYValue of 1.5 is: " + findTopYValue(1.5));
        System.out.println("findTopYValue of 2.0 is: " + findTopYValue(2.0));
        System.out.println("findTopYValue of 3.0 is: " + findTopYValue(3.0));
        System.out.println("findTopYValue of 5.0 is: " + findTopYValue(5.0));
        System.out.println("findTopYValue of 7.0 is: " + findTopYValue(7.0));
        System.out.println("findTopYValue of 10.0 is: " + findTopYValue(10.0));*/

/*
        int numYears =  2;
        int numInt = findNumberYearIntervals(numYears);
        int intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  3;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  4;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  9;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  10;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  11;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  12;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  13;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  14;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  15;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  16;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  17;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  18;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  19;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  20;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  21;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  22;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  23;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  24;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  25;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  26;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  27;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  28;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  29;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  30;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  31;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  49;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  50;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
        numYears =  51;
        numInt = findNumberYearIntervals(numYears);
        intSize = findXIntervalSize(numYears, numInt);
        System.out.println("find X Interval for " + numYears + " is: " + numInt + " of size: " + intSize);
*/



    }

    // DOESN'T WORK FOR NUMYEARS = 1
/*    private void DrawXAxisTicks(int numYears, int startYear, Graphics g) {
        if (numYears <= 1)
            return;

        int numYearLabels =  findNumberYearIntervals(numYears) + 1;
        double spacing = (xAxisEnd - xAxisBegin) / ((double) numYears - 1);
        int tickTop = yAxisBegin - TICK_SIZE / 2;
        int tickBtm = yAxisBegin + TICK_SIZE / 2;

        for (int i = 0; i < numYears; ++i) {
            int xPos = xAxisBegin + (int) (i * spacing);
            g.drawLine(xPos, tickTop, xPos, tickBtm);
            g.drawString(Integer.toString(startYear + i), xPos - DATE_SHIFT, yAxisBegin + 2*TICK_SIZE);
        }
    } */

    private void DrawXAxisTicks(int numYears, int startYear, Graphics g) {
        if (numYears <= 1)
            return;

        int numYearLabels =  findNumberYearIntervals(numYears);
        int yearsPerInterval = findXIntervalSize(numYears, numYearLabels);
        double spacing = (xAxisEnd - xAxisBegin) / ((double)numYearLabels);
        int tickTop = yAxisBegin - TICK_SIZE / 2;
        int tickBtm = yAxisBegin + TICK_SIZE / 2;

        for (int i = 0; i <= numYearLabels; ++i) {
            int xPos = xAxisBegin + (int) (i * spacing);
            g.drawLine(xPos, tickTop, xPos, tickBtm);
            g.drawString(Integer.toString(startYear + i * yearsPerInterval), xPos - DATE_SHIFT, yAxisBegin + 2*TICK_SIZE);
        }
    }

    private int findNumberYearIntervals(int numYears) {
        int numIntervals = numYears - 1;
        if (numIntervals <= MAX_X_INTERVALS)
            return numIntervals;

        while (numIntervals > MAX_X_INTERVALS) {
            int tempIntval = numIntervals / 2;
            if (tempIntval < MAX_X_INTERVALS || (tempIntval == MAX_X_INTERVALS && numIntervals % 2 == 0))
                return numIntervals / 2 + ((numYears - 1) % ((numYears - 1) / (numIntervals / 2)) == 0 ? 0 : 1);

            tempIntval = numIntervals / 5;
            if (tempIntval < MAX_X_INTERVALS || (tempIntval == MAX_X_INTERVALS && numIntervals % 5 == 0))
                return numIntervals / 5 + ((numYears - 1) % ((numYears - 1) / (numIntervals / 5)) == 0 ? 0 : 1);

            numIntervals /= 10;
        }
        return numIntervals + ((numYears - 1) % ((numYears - 1) / numIntervals) == 0 ? 0 : 1); // FIX THIS, LIKE THE OTHERS
    }


    private int findXIntervalSize(int numYears, int numIntervals) {
        int interval = (numYears - 1) / numIntervals;
        if ((numYears - 1) % numIntervals == 0)
            return interval;
        interval =  (int)Math.round(interval * (1.0 + 1.0/numIntervals) + 0.5);
        return interval;

    }

    private void DrawYAxisTicksAndLabels(int numIntervals, double maxY, Graphics g) {
        if (numIntervals == 1)
            return;

        double topYValue = findTopYValue(maxY);
        double spacing = (yAxisBegin - yAxisEnd) / ((double)NUM_Y_INTERVALS);
        double yDelta = topYValue / ((double)NUM_Y_INTERVALS);
        int tickLeft = xAxisBegin - TICK_SIZE / 2;
        int tickRight = xAxisBegin + TICK_SIZE / 2;
        String formatStr = makeFormatString(topYValue);

        for (int i = 0; i <= NUM_Y_INTERVALS; ++i) {
            int yPos = yAxisBegin - (int)(i * spacing);
            g.drawLine(tickLeft, yPos, tickRight, yPos);
            String dataStr = String.format(formatStr, yDelta * i);
            g.drawString(dataStr, xAxisBegin - DATA_SHIFT, yPos + TICK_SIZE / 2);
        }
    }

    private String makeFormatString(double maxY) {
        final double MAX_LOG = 3.0;
        return String.format("%%5.%df", Math.log10(maxY) >= MAX_LOG ? 0 : 1 );
    }

    private double findTopYValue(double max) {
        final double LOG2 = Math.log10(2.0), LOG5 = Math.log10(5.0);
        double logMax = Math.log10(max);
        double logFraction = logMax % 1; // Java returns fractional part
        double logAdd = 0.0;

        if (logFraction > LOG5)
            logAdd = 1.0;
        else if (logFraction > LOG2)
            logAdd = LOG5;
        else if (logFraction > 0.0)
            logAdd = LOG2;

        return Math.pow(10.0,(int)logMax + logAdd);
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
