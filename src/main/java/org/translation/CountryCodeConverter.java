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

    // Map to store the country codes (3-letter codes) and country names
    private final Map<String, String> codeToCountryMap = new HashMap<>();
    private final Map<String, String> countryToCodeMap = new HashMap<>();

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

            // Skip the first line as it is the header
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\t");
                // Alpha-3 code (third column)
                String code = parts[2].trim();
                // Country name is in the first column (index 0)
                String name = parts[0].trim();
                codeToCountryMap.put(code, name);
                countryToCodeMap.put(name, code);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException("Error loading country codes from file", ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        String upperCode = code.toUpperCase();
        return codeToCountryMap.getOrDefault(upperCode, "Unknown country code");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryToCodeMap.getOrDefault(country, "Unknown country name");
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return codeToCountryMap.size();
    }
}
