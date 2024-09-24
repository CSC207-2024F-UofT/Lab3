package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private final JSONArray jsonArray;
    private List<String> countryLanguages;
    private List<String> countries;
    private String id = "id";
    private String alpha2 = "alpha2";
    private String alpha3 = "alpha3";

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);
            this.countryLanguages = new ArrayList<>();
            this.countries = new ArrayList<>();
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject country = jsonArray.getJSONObject(index);
                for (String lang : country.keySet()) {
                    if (!(id.equals(lang) || alpha2.equals(lang) || alpha3.equals(lang))) {
                        if (!countryLanguages.contains(lang)) {
                            countryLanguages.add(lang);
                        }
                    }
                }
                if (!countries.contains(country.getString(alpha3))) {
                    countries.add(country.getString(alpha3));
                }
            }

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return countryLanguages;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        for (int index = 0; index < jsonArray.length(); index++) {
            JSONObject translation = jsonArray.getJSONObject(index);
            if (translation.getString("alpha3").equals(country)) {
                return translation.getString(language);
            }
        }
        return "Country not found";
    }
}
