package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private String countryname;
    private String alpha2code;
    private String alpha3code;
    private int numerc;
    // do we aassign the variable to
    // TODO Task: pick appropriate instance variable(s) to store the data necessary for this class

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
    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:SuppressWarnings"})
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // so it converts the txt file into json file
            this.countryname = lines.get(0);
            this.alpha2code = lines.get(1);
            this.alpha3code = lines.get(2);
            this.numerc = Integer.parseInt(lines.get(3));

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

    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:SuppressWarnings"})
    public String getCountryname() {
        return countryname;
    }

    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:SuppressWarnings"})
    public String getAlpha2code() {
        return alpha2code;
    }

    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:SuppressWarnings"})
    public String getAlpha3code() {
        return alpha3code;
    }

    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:SuppressWarnings"})
    public int getNumerc() {
        return numerc;
    }
}
