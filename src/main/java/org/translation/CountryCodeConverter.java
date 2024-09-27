package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// note one of the TODOs was that hashMap has to be above util.list, fix it if you need it.

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // maybe did pick appropriate instance variable(s) to store the data necessary for this class
    public static Map<String,String> country_codes = new HashMap<>();

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

            while(iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    country_codes.put(parts[2], parts[0]);
                }
                else {
                    String[] country_names = Arrays.copyOfRange(parts, 0, parts.length - 4);
                    String country_name = String.join(" ", country_names);
                    String country_code_3 = String.join(" ", parts[parts.length - 3]);
                    country_codes.put(country_code_3, country_name);
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
        // maybe did: update this code to use an instance variable to return the correct value
        String uppercase_Code = code.toUpperCase();
        for(Map.Entry<String,String> entry : country_codes.entrySet()) {
            if(uppercase_Code.equals(entry.getKey())) {
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
        // maybe did: update this code to use an instance variable to return the correct value
        for(Map.Entry<String,String> entry: country_codes.entrySet()) {
            if(entry.getValue().equals(country)) {
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
        // maybe did: update this code to use an instance variable to return the correct value
        return country_codes.size();
    }
}
