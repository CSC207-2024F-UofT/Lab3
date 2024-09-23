package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private Map<String, String> codeToCountry = new HashMap<String, String>();
    private Map<String, String> countryToCode = new HashMap<String, String>();

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     *
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (String line : lines) {
                String[] parts = line.split(" ");
                codeToCountry.put(parts[2], parts[0]);
                countryToCode.put(parts[0], parts[2]);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     *
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {

        return codeToCountry.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     *
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {

        return countryToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     *
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {

        return countryToCode.size();
    }
}
