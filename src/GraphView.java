import javax.swing.*;
import java.awt.*;

/**
 * An object of this class maps the cellular data of a country to the width and
 * height of the panel
 */
public class GraphView extends JPanel {
    final private int width, height, numYears;
    private int plottedXmin, plottedXmax, plottedYmin, plottedYmax;
    private Font font;

    final private int YEARS_FOR_MAX = 10;
    final private LegendPanel pointLegends = new LegendPanel();
    final private LinkedList<ColoredPoint> graphPoints = new LinkedList<>();  // STRUCTURE SAME AS LEGENDS???

    // Data plotting parameters
    final int MARGIN = 40, TICK_SIZE = 10, POINT_SIZE = 10;
    final int DATA_SHIFT = 35, DATE_SHIFT = 13;
    final int MAX_X_INTERVALS = 10, NUM_Y_INTERVALS = 10;
    final private int dataMinX, dataMaxX;
    final private double dataMinY = 0.0, dataMaxY;                 // SHOULD THESE BE ALL CAPS?
    final private double TOP_Y_VALUE_DEFAULT = 200.0;

    // Legend graphing parameters
    final private int LBL_DX = 5, LBL_DY = 14; // text offset wrt label rectangle
    final private int LBL_WIDTH = 180, LBL_HEIGHT = 20, MONO_SIZE = 8; // label rectangle dimensions
    final private int LEG__X = 60, LEG_START_Y = 40, NEXT_LEG_Y = 25; // legend positions

    // Color scheme
    final private static Color[] colorArray = {Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.green, Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red, Color.yellow};

    public GraphView(int width, int height, LinkedList<Country> countries) {
        //super(new GridLayout(1, 2, 40, 40)); // Call layout manager
        //this.setSize(width ,height);

        // new stuff
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.CYAN));
        setOpaque(true);
        setForeground(Color.BLACK);
        setBounds(0,0, width, height);
        JPanel legends = new LegendPanel();
        //////

        this.width = width;
        this.height = height;
        font = new Font("Serif", Font.PLAIN, 11);

        // map method parameters
        plottedXmin = MARGIN;
        plottedYmin = height - MARGIN;
        plottedXmax = width - MARGIN;
        plottedYmax = MARGIN;

        // Set X-axis min and max
        dataMinX = countries.getIndex(0).getStartYear();
        dataMaxX = countries.getIndex(0).getEndYear();
        numYears = dataMaxX - dataMinX + 1;

        // Set Y-axis max (min already set as 0.0)
        int yearsToSkip = numYears - Math.min(numYears, YEARS_FOR_MAX);
        double dataMax = 0.0;
        for(Country country : countries) {
            SubscriptionYear[] subYear = country.getSubscriptions();
            for(int i = yearsToSkip; i < numYears; ++i)
                dataMax = Math.max(dataMax, subYear[i].getSubscriptions());
        }
        dataMaxY = findTopYValue(dataMax); // "round up" to next whole value

        // map values and save
        int countryCntr = 0;
        for(Country country : countries) {
            SubscriptionYear[] subYears = country.getSubscriptions();
            Color pntColor = colorArray[countryCntr % colorArray.length];
            for(SubscriptionYear subYear : subYears) {
                double mappedX = map(subYear.getYear(), dataMinX, dataMaxX, plottedXmin, plottedXmax) - POINT_SIZE / 2;
                double mappedY = map(subYear.getSubscriptions(), dataMinY, dataMaxY, plottedYmin, plottedYmax) - POINT_SIZE / 2;
                ColoredPoint tempPnt = new ColoredPoint(pntColor, mappedX, mappedY, subYear.getYear(), subYear.getSubscriptions());
                graphPoints.add(tempPnt);
            }
            pointLegends.add(country.getName(), pntColor);
            ++countryCntr;
        }
    }

    static public final double map(double value, double dataMin, double dataMax, double plottedMin, double plottedMax) {
        return plottedMin + (plottedMax - plottedMin) * ((value - dataMin) / (dataMax - dataMin));
    }

    LegendPanel getPointLegends() { return pointLegends; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TestColorDots(g); // test colors

        // draw x-axis and ticks
        g.drawLine(plottedXmin, plottedYmin, plottedXmax, plottedYmin);
        DrawXAxisTicksAndLabels(numYears, dataMinX, g); // dataMinX is startYear

        // draw y-axis and ticks
        g.drawLine(plottedXmin, plottedYmin, plottedXmin, plottedYmax);
        int numIntervals = findNumberYearIntervals(numYears);
        DrawYAxisTicksAndLabels(numIntervals, dataMaxY, g, false);

        // graphs points in graphPoints LinkedList
        Color oldColor = g.getColor();
        for (ColoredPoint currPnt : graphPoints) {
            g.setColor(currPnt.getColor());
            g.fillOval((int)currPnt.getX(), (int)currPnt.getY(), POINT_SIZE, POINT_SIZE);
        }
        pointLegends.graphLegends(g);
        //add(pointLegends); // doesn't add legend
        g.setColor(oldColor);
    }

    private void DrawXAxisTicksAndLabels(int numYears, int startYear, Graphics g) {
        if (numYears <= 1)
            return;

        int numYearLabels =  findNumberYearIntervals(numYears);
        int yearsPerInterval = findXIntervalSize(numYears, numYearLabels);
        double spacing = (plottedXmax - plottedXmin) / ((double)numYearLabels);
        int tickTop = plottedYmin - TICK_SIZE / 2;
        int tickBtm = plottedYmin + TICK_SIZE / 2;

        for (int i = 0; i <= numYearLabels; ++i) {
            int xPos = plottedXmin + (int) (i * spacing);
            g.drawLine(xPos, tickTop, xPos, tickBtm);
            g.drawString(Integer.toString(startYear + i * yearsPerInterval), xPos - DATE_SHIFT, plottedYmin + 2*TICK_SIZE);
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
        return (int)Math.round(interval * (1.0 + 1.0/numIntervals) + 0.5);
    }

    private void DrawYAxisTicksAndLabels(int numIntervals, double maxY, Graphics g, boolean findTop) {
        if (numIntervals == 1)
            return;

        double topYValue = maxY;
        if (findTop)
            topYValue = findTopYValue(maxY);

        double spacing = (plottedYmin - plottedYmax) / ((double)NUM_Y_INTERVALS);
        double yDelta = topYValue / ((double)NUM_Y_INTERVALS);
        int tickLeft = plottedXmin - TICK_SIZE / 2;
        int tickRight = plottedXmin + TICK_SIZE / 2;
        String formatStr = makeFormatString(topYValue);

        for (int i = 0; i <= NUM_Y_INTERVALS; ++i) {
            int yPos = plottedYmin - (int)(i * spacing);
            g.drawLine(tickLeft, yPos, tickRight, yPos);
            String dataStr = String.format(formatStr, yDelta * i);
            g.drawString(dataStr, plottedXmin - DATA_SHIFT, yPos + TICK_SIZE / 2);
        }
    }

    private String makeFormatString(double maxY) {
        final double MAX_LOG = 3.0;

        if (maxY <= 0.0)
            return "%%5.1f";

        return String.format("%%5.%df", Math.log10(maxY) >= MAX_LOG ? 0 : 1 );
    }

    private double findTopYValue(double max) {
        if (max <= 0.0)
            return TOP_Y_VALUE_DEFAULT;

        double increment = Math.pow(10.0, Math.floor(Math.log10(max)));
        return (1.0 + Math.floor(max / increment)) * increment;
    }

    private void TestColorDots(Graphics g) {
        Color oldColor = g.getColor();
        for (int i = 0; i < colorArray.length; ++i) {
            g.setColor(colorArray[i]);
            g.fillOval(plottedXmax + POINT_SIZE, plottedYmin - (i + 3) * 3 * POINT_SIZE, POINT_SIZE, POINT_SIZE);

            // complimentary color
            g.setColor(getComplimentColor(colorArray[i]));
            g.fillOval(plottedXmax + 2 * POINT_SIZE, plottedYmin - (i + 3) * 3 * POINT_SIZE, POINT_SIZE, POINT_SIZE);
        }
        g.setColor(oldColor);
    }

    /**
     * Returns the complimentary (opposite) color.
     * Modified and adapted from: http://www.java2s.com/Code/Android/2D-Graphics/
     * Returnsthecomplimentaryoppositecolor.htm
     * @param color Color RGB color to return the compliment of
     * @return Color RGB of compliment color
     */
    public static Color getComplimentColor(Color color) {
        int red = (~color.getRed()) & 0xff;
        int blue = (~color.getBlue()) & 0xff;
        int green = (~color.getGreen()) & 0xff;

        return new Color(red, green, blue);
    }
}