package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * This class provides the service of converting country codes to their names.
 */

public class CountryCodeConverter {

    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class
    List<String> country_name = new ArrayList<>();
    List<String> alpha2 = new ArrayList<>();
    List<String> alpha3 = new ArrayList<>();
    List<String> numeric = new ArrayList<>();

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

            // TODO Task: use lines to populate the instance variable(s)
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\t");
                if (parts.length == 4) {
                    country_name.add(parts[0].trim());
                    alpha2.add(parts[1].trim());
                    alpha3.add(parts[2].trim());
                    numeric.add(parts[3].trim());
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
        // TODO Task: update this code to use an instance variable to return the correct value
        int country_index = 0;
        for (int i = 0; i < alpha3.size(); i++) {
            if (code.equals(alpha3.get(i))) {
                country_index = i;
            }
        }
        return country_name.get(country_index);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        int code_index = 0;
        for (int i = 0; i < country_name.size(); i++) {
            if (country.equals(country_name.get(i))) {
                code_index = i;
            }
        }
        return alpha3.get(code_index);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return country_name.size();
    }
}
