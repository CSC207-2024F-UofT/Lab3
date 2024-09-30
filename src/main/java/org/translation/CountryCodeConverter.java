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
    private final Map<String, String> countryToAlpha3;
    private final Map<String, String> alpha3ToCountry;

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
        countryToAlpha3 = new HashMap<>();
        alpha3ToCountry = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\\s+");

                final int spaces = 4;
                if (parts.length >= spaces) {

                    StringBuilder countryNameBuilder = new StringBuilder();
                    final int stopper = 3;
                    for (int j = 0; j < parts.length - stopper; j++) {
                        countryNameBuilder.append(parts[j]).append(" ");
                    }
                    String countryName = countryNameBuilder.toString().trim();

                    String alpha3Code = parts[parts.length - 2].trim().toLowerCase();

                    alpha3ToCountry.put(alpha3Code, countryName);
                    countryToAlpha3.put(countryName, alpha3Code);
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
        return alpha3ToCountry.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryToAlpha3.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryToAlpha3.size();
    }
}
