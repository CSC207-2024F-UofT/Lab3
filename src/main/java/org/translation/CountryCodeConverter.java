package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class provides the service of converting country codes to their names.
 */

public class CountryCodeConverter {

    static final int THREE = 3;
    static final int FOUR = 4;
    private static Map<String, String> countryCodes = new HashMap<>();

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

            // maybe did: use lines to populate the instance variable(s)
            var iterator = lines.iterator();

            // skips header
            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");
                if (parts.length == FOUR) {
                    countryCodes.put(parts[2], parts[0]);
                }
                else {
                    String[] countryNames = Arrays.copyOfRange(parts, 0, parts.length - FOUR);
                    String countryName = String.join(" ",
                            countryNames);
                    String countryCode3 = String.join(" ",
                            parts[parts.length - THREE]);
                    countryCodes.put(countryCode3, countryName);
                }
            }
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
        String uppercaseCode = code.toUpperCase();
        for (Map.Entry<String, String> entry : countryCodes.entrySet()) {
            if (uppercaseCode.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "N/A";
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        for (Map.Entry<String, String> entry: countryCodes.entrySet()) {
            if (entry.getValue().equals(country)) {
                return entry.getKey();
            }
        }
        return "N/A";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryCodes.size();
    }
}
