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
    private static Map<String, String> dictionary = new HashMap<>();

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            lines.remove(0);


            for (String line : lines) {
                String[] items = line.split("\t");
//                int i = 1;
//                String countr = items[0];
//                while (i < items.length - (1 + 1 + 1)) {
//                    countr += " " + items[i];
//                    i += 1;
//                }

//                dictionary.put((items[items.length - 2]).toLowerCase(), countr);
                if (items.length == 4) {
                    String countryName = items[0];
                    String alpha3Code = items[2].toLowerCase();
                    dictionary.put(alpha3Code, countryName);
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
    public static String fromCountryCode(String code) {
        String country = dictionary.get(code.toLowerCase());
        if (country != null) {
            return country;
        }
        return "Country not found.";
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public static String fromCountry(String country) {
        for (String key : dictionary.keySet()) {
            if (dictionary.get(key).equals(country)) {
                return key;
            }
        }
        return "Country code not found.";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return dictionary.size();
    }
}
