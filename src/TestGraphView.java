import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class TestGraphView {
    public static void main(String[] args) {
        System.out.println("TestGraphView");

        final int WIDTH = 800, HEIGHT = 600;
        final int NUM_RAND_COUNTRIES = 11;

        // establish main frame in which program will run:
        JFrame frmMyWindow = new JFrame("World Development Indicators");
        //frmMyWindow.setLocationRelativeTo(null);
        frmMyWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmMyWindow.setSize(WIDTH, HEIGHT);
        System.out.println("Window size (after setSize) is: " + frmMyWindow.getSize() + " width is: " + frmMyWindow.getWidth());

        // reposition jframe to middle of screen
        // repositioning code from: http://stackoverflow.com/questions/3480102/java-
        // jframe-setlocationrelativetonull-not-centering-the-window-on-ubuntu-10-0
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - (frmMyWindow.getWidth() / 2),
                middle.y - (frmMyWindow.getHeight() / 2));
        frmMyWindow.setLocation(newLocation);

/*
        MyJPanelDemo01 demo1 = new MyJPanelDemo01();
        frmMyWindow.add(demo1);
        demo1.repaint();
*/
       // Get array of countries and pick a random few for LinkedList
        LinkedList<Country> selectedCountries = new LinkedList<>();
        final String FILENAME = "resources/cellular.csv";	// Directory path for Mac OS X
        // final String FILENAME = "resources/cellular_short_oneDecade.csv";	// Directory path for Mac OS X
        CSVReader parser = new CSVReader(FILENAME);
        String [] countryNames = parser.getCountryNames();
        int [] yearLabels = parser.getYearLabels();
        double [][] parsedTable = parser.getParsedTable();

        Country current;
        Country [] allCountries = new Country[countryNames.length];
        for (int countryIndex = 0; countryIndex < allCountries.length; countryIndex++)
        {
            int numberOfYears = yearLabels.length;
            current = new Country(countryNames[countryIndex], numberOfYears);

            // Go through each year of cellular data read from the CSV file.
            for (int yearIndex = 0; yearIndex < numberOfYears; yearIndex++)
            {
                double [] allSubscriptions = parsedTable[countryIndex];
                double countryData = allSubscriptions[yearIndex];
                current.addSubscriptionYear(yearLabels[yearIndex], countryData);
            }
            allCountries[countryIndex] = current;
        }

        // select random countries
        Random random = new Random();
        for (int i = 0; i < NUM_RAND_COUNTRIES; i++)
        {
            // Selects a random index of the cellularData.Country data array
            int selectedIndex = random.nextInt(allCountries.length);
            // int selectedIndex = i;  // FOR DEBUGGING
            Country countryToAdd = allCountries[selectedIndex];
            System.out.printf("Adding country with name %s to the end of the list.\n", countryToAdd.getName());
            selectedCountries.add(countryToAdd);
        }

        GraphView myGraphView = new GraphView(WIDTH, HEIGHT, selectedCountries);
        frmMyWindow.add(myGraphView);
        myGraphView.repaint();
        frmMyWindow.setVisible(true);
        System.out.println("Window size (after last setVisible) is: " + frmMyWindow.getSize());
    }
}
