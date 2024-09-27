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
    private Map<String, String> codeToCountry = new HashMap<>();
    private Map<String, String> countryToCode = new HashMap<>();

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
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\t");
                    final int len = 4;
                    final int le = 3;
                    if (parts.length == len) {
                        String country = parts[0].trim();
                        String alpha3Code = parts[2].trim();
                        codeToCountry.put(alpha3Code, country);
                        countryToCode.put(country, alpha3Code);
                    }
                    else if (parts.length > len) {
                        String alpha3Code = parts[parts.length - 2].trim();
                        String country = "";
                        for (int c = 0; c < parts.length - le; c++) {
                            if (c > 0) {
                                country += " ";
                            }
                            country += parts[c].trim();
                        }
                        codeToCountry.put(alpha3Code, country);
                        countryToCode.put(country, alpha3Code);
                    }
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
        return codeToCountry.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return codeToCountry.size();
    }
}
