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

    private final JSONArray jsonArrayNew;

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

            String jsonString = Files.readString(
                    Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

            this.jsonArrayNew = jsonArray;

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object

        List<String> languages = new ArrayList<>(this.jsonArrayNew.length());
        List<String> countries = getCountries();

        int index = countries.indexOf(country);

        languages.addAll(this.jsonArrayNew.getJSONObject(index).keySet());

        languages.remove("id");
        languages.remove("alpha2");
        languages.remove("alpha3");

        return languages;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object

        int length = this.jsonArrayNew.length();
        List<String> countries = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            String countryCode = this.jsonArrayNew.getJSONObject(i).getString("alpha3");
            countries.add(countryCode);
        }

        return countries;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed

        List<String> countryCodes = getCountries();

        final int countryIndex = countryCodes.indexOf(country);
        JSONObject targetCountry = this.jsonArrayNew.getJSONObject(countryIndex);

        return targetCountry.getString(language);
    }
}
