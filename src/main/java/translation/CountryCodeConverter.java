package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names and back.
 */
public class CountryCodeConverter {
    // Here are the instance attributes
    private Map<String, String> countryCodeToCountry = new HashMap<>();
    private Map<String, String> countryToCountryCode = new HashMap<>();

    /**
     * Default constructor that loads the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor that allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resources file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line == null || line.isBlank()) continue;
                String[] parts = line.split("\t");
                if (parts.length >= 3) {
                    String countryName  = parts[0].trim();
                    String countryCode = parts[2].trim();

                    if (!countryName.isEmpty() && !countryCode.isEmpty()) {
                        String nameKey = countryName.toLowerCase();
                        String codeKey = countryCode.toLowerCase();

                        countryCodeToCountry.put(codeKey, countryName);
                        countryToCountryCode.put(countryName, countryCode.toLowerCase());
                    }
                }

                // TODO Task B: use parts to populate the instance variables
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Return the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        if (code == null) {
            return null;
        }
        return countryCodeToCountry.get(code.toLowerCase());
    }

    /**
     * Return the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     * This seems good
     */
    public String fromCountry(String country) {
        // TODO Task B: Tick
        return countryToCountryCode.get(country);
    }
    /**
     * Returns a sorted list of all country names for use in the GUI.
     * @return A sorted List of country names.
     */
    public java.util.List<String> getCountryNames() {
        java.util.List<String> sortedNames = new java.util.ArrayList<>(countryToCountryCode.keySet());
        java.util.Collections.sort(sortedNames);
        return sortedNames;
    }

    /**
     * Return how many countries are included in this country code converter.
     * @return how many countries are included in this country code converter.
     *
     * I am not sure about this...
     */
    public int getNumCountries() {
        // TODO Task B: Tick
        return countryCodeToCountry.size();
    }
}
