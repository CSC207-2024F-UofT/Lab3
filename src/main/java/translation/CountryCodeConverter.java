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
                String[] parts = line.split("\t");
                // TODO Task B: use parts to populate the instance variables
                if (parts.length >= 3) {
                    String country = parts[0].trim();
                    String alpha3  = parts[2].trim();

                    if (!country.isEmpty() && !alpha3.isEmpty()) {
                        String normCode = alpha3.toUpperCase();       // normalize code
                        String normName = country.toLowerCase();      // normalize name key

                        countryCodeToCountry.put(normCode, country);
                        countryToCountryCode.put(normName, normCode);
                    }
                }
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
        // TODO Task B: update this code to use an instance variable to return the correct value
        if (code == null) return null;
        return countryCodeToCountry.get(code.trim().toUpperCase());
    }

    /**
     * Return the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task B: update this code to use an instance variable to return the correct value
        if (country == null){
            return null;
        }
        return countryToCountryCode.get(country.trim().toLowerCase());
    }

    /**
     * Return how many countries are included in this country code converter.
     * @return how many countries are included in this country code converter.
     */
    public int getNumCountries() {
        // TODO Task B: update this code to use an instance variable to return the correct value
        return countryCodeToCountry.size();
    }
}
