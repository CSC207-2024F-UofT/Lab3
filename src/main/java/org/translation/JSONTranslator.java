package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Task: pick appropriate instance variables for this class
    private final Map<String, Map<String, String>> countryTranslations;

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

            JSONArray jsonArray = new JSONArray(jsonString);

            // Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

            this.countryTranslations = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject curr = jsonArray.getJSONObject(i);
                String currCountry = curr.getString("alpha3");

                this.countryTranslations.put(currCountry, new HashMap<>());

                for (String key: curr.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        this.countryTranslations.get(currCountry).put(key, curr.getString(key));
                    }
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> languages = new ArrayList<>();

        if (this.countryTranslations.containsKey(country)) {
            languages.addAll(countryTranslations.get(country).keySet());
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object

        return new ArrayList<>(this.countryTranslations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // Task: complete this method using your instance variables as needed
        if (this.countryTranslations.containsKey(country)) {
            return this.countryTranslations.get(country).get(language);
        }
        return null;
    }
}
