import javax.swing.*;
import java.awt.*;
//import java.awt.Graphics;                                                       // NEEDED ??????

/**
 * An object of this class maps the cellular data of a country to the width and
 * height of the panel
 */
public class GraphView extends JPanel {
    private int width, height;
    private double plottedXmin, plottedXmax, plottedYmin, plottedYmax;
    private Font font;

    private final int YEARS_FOR_MAX = 10;
    private final int MARGIN = 20;
    final int POINT_SIZE = 20;

    // DO THESE NEED TO BE MEMBERS???
    private int dataMinX, dataMaxX;
    private double dataMinY = 0.0, dataMaxY = 0.0;

    public GraphView(int width, int height, LinkedList<Country> countries) {
        this.width = width;
        this.height = height;
        font = new Font("Serif", Font.PLAIN, 11);

        // Set X-axis min and max
        dataMinX = countries.getIndex(0).getStartYear();
        dataMaxX = countries.getIndex(0).getEndYear();
        int numYears = dataMaxX - dataMinX + 1;

        // Find maximum Y-axis value
        int yearsToSkip = numYears - Math.min(numYears, YEARS_FOR_MAX);
        for(Country country : countries) {
            SubscriptionYear[] subYear = country.getSubscriptions();
            for(int i = yearsToSkip; i <= numYears; ++i)
                dataMaxY = Math.max(dataMaxY, subYear[i].getSubscriptions());
        }

        // map method parameters
        plottedYmin = height - MARGIN;
        plottedYmax = MARGIN;
        plottedXmin = MARGIN;
        plottedXmax = width-MARGIN;

        // map values and save
        for(Country country : countries) {
            int yearCounter = 0;
            SubscriptionYear[] subYears = country.getSubscriptions();
            for(SubscriptionYear subYear : subYears) {
                // DO MAPPING AND SAVING HERE
                double mappedX = map(dataMinX + yearCounter, dataMinX, dataMaxX, plottedXmin, plottedXmax);
                double mappedY = map(subYear.getSubscriptions(), dataMinY, dataMaxY, plottedYmin, plottedYmax);
                // INVERT COORDINATES FOR JAVA 2D LAYOUT

            }
        }


    }

    static public final double map(double value, double dataMin, double dataMax, double plottedMin, double plottedMax)
    {
        return plottedMin + (plottedMax - plottedMin) * ((value - dataMin) / (dataMax - dataMin));
    }

    @Override
    protected void paintComponent(Graphics g) {
        ColoredPoint current = new ColoredPoint(Color.BLUE,0,0,0,0); // PLACEHOLDER

        // COMPILER TEST ONLY
        g.setColor(Color.BLUE);
        g.setColor(current.getColor());
        g.fillOval((int)current.getX(), (int)current.getY(), POINT_SIZE, POINT_SIZE);
        g.drawString("Test", (int)current.getX(),(int)current.getY());
    }
}
