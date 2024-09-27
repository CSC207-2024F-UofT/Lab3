package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    private static int countCountry;
    private static List<String> countryName;
    // private static List countryAlpha3Code;
    private static List<String> countryNumeric;
    private static int countryIndex = 3;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            countCountry = lines.size();

            for (String line:lines) {
                List<String> lists = line.split("\t");
                countryName.add(lists.get(0));
                countryNumeric.add(lists.get(countryIndex));

            }
            //Done TODO Task: use lines to populate the instance variable(s)

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // Done TODO Task: update this code to use an instance variable to return the correct value
        return countryName.get(countryNumeric.indexOf(code));
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // Done TODO Task: update this code to use an instance variable to return the correct value
        return countryNumeric.get(countryName.indexOf(country));
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // Done TODO Task: update this code to use an instance variable to return the correct value
        return countCountry;
    }
}
