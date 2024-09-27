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

    private static final int PARTS_LENGTH = 4;
    private Map<String, String> countryCodeToName;
    private Map<String, String> countryNameToCode;

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
        countryCodeToName = new HashMap();
        countryNameToCode = new HashMap();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }
            while (iterator.hasNext()) {
                String line = iterator.next().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\t");
                if (parts.length >= PARTS_LENGTH) {
                    String name = parts[0];
                    String code = parts[2].trim();

                    countryNameToCode.put(name.toLowerCase(), code);
                    countryCodeToName.put(code, name);

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
        return countryCodeToName.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryNameToCode.get(country.toLowerCase());
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return countryCodeToName.size();
    }

}
