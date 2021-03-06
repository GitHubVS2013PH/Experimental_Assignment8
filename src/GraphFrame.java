import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Paul on 5/16/2017.
 */
public class GraphFrame extends JFrame {
    final static int NUM_RAND_COUNTRIES = 11;

    public GraphFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);

        LinkedList<Country> selectedCountries = buildSelectedCountryList();
        GraphView myGraphView = new GraphView(width, height, selectedCountries);
        add(myGraphView);
    }

    private LinkedList<Country> buildSelectedCountryList() {
        LinkedList<Country> selectedCountries = new LinkedList<>();

        // Get array of countries and pick a random few for LinkedList
        final String FILENAME = "resources/cellular.csv";	// Directory path for Mac OS X
        // final String FILENAME = "resources/cellular_short_oneDecade.csv";	// Directory path for Mac OS X
        CSVReader parser = new CSVReader(FILENAME);
        String [] countryNames = parser.getCountryNames();
        int [] yearLabels = parser.getYearLabels();
        double [][] parsedTable = parser.getParsedTable();

        Country current;
        Country [] allCountries = new Country[countryNames.length];
        for (int countryIndex = 0; countryIndex < allCountries.length; countryIndex++) {
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
            // int selectedIndex = i;                                                  // FOR DEBUGGING
            Country countryToAdd = allCountries[selectedIndex];
            System.out.printf("Adding country with name %s to the end of the list.\n", countryToAdd.getName());
            selectedCountries.add(countryToAdd);
        }

        return selectedCountries;
    }
}
