// Commit this file

package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    private Map<String, String> codeToCountryHashMap = new HashMap<String, String>();
    private Map<String, String> countryToCodeHashMap = new HashMap<String, String>();

    private final String file = "country-codes.txt";

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
        // Delimited by tab /t

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> lineByLine = lines.iterator();
            lineByLine.next();

            while (lineByLine.hasNext()) {
                String[] words = lineByLine.next().split("\t");
                codeToCountryHashMap.put(words[2], words[0]);
                countryToCodeHashMap.put(words[0], words[2]);
            }

            // TODO Task: use lines to populate the instance variable(s)

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
        // TODO Task: update this code to use an instance variable to return the correct value
        String upperCode = code.toUpperCase();
        CountryCodeConverter converter = new CountryCodeConverter(file);

        return converter.codeToCountryHashMap.get(upperCode);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        CountryCodeConverter converter = new CountryCodeConverter(file);
        return converter.countryToCodeHashMap.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct values
        System.out.println(codeToCountryHashMap);
        return codeToCountryHashMap.size();
    }
}
