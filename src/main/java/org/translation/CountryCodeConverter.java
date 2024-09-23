package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private HashMap<String,List<String>[]> countryDictionary = new HashMap<>();
    /*
    // A list of the country names in this CountryCodeConverter
    private List<String> countryNames = new ArrayList<String>();
    // A list of the country Alpha-2 codes in this CountryCodeConverter
    private List<String> alpha2Codes = new ArrayList<String>();
    // A list of the country Alpha-3 codes in this CountryCodeConverter
    private List<String> alpha3Codes = new ArrayList<String>();
    // A list of the country numeric codes in this CountryCodeConverter
    private List<String> numericCodes = new ArrayList<String>();
    */

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
            for (String line : lines) {
                String[] values = line.split("\t");
                List<String> temp = Arrays.asList(new String[]{values[1], values[2], values[3]});
                this.countryDictionary.put(values[0], temp]);
                /*this.countryNames.add(values[0]);
                this.alpha2Codes.add(values[1]);
                this.alpha3Codes.add(values[2]);
                this.numericCodes.add(values[3]);
                 */
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
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TODO Task: update this code to use an instance variable to return the correct value
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TODO Task: update this code to use an instance variable to return the correct value
        return 0;
    }
}
