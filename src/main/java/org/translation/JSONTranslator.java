package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
    public static final String ALPHA_3 = "alpha3";
    private final Map<String, Map<String, String>> countryTranslations = new HashMap<>();
    // TODO Task: pick appropriate instance variables for this class

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
        this.translations = new HashMap<>();
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);

                if (country.has(ALPHA_3)) {
                    String countryCode = country.getString(ALPHA_3);

                    Map<String, String> translations = new HashMap<>();

                    for (String key : country.keySet()) {
                        if (!"id".equals(key) && !"alpha2".equals(key) && !ALPHA_3.equals(key)) {
                            translations.put(key, country.getString(key));
                        }
                    }
                    countryTranslations.put(countryCode, translations);
                }

            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (countryTranslations.containsKey(country)) {
            return new ArrayList<>(countryTranslations.get(country).keySet());
        }
        return Collections.emptyList();

    }

    @Override
    public List<String> getCountries() {

        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryTranslations.keySet());

    }

    @Override
    public String translate(String country, String language) {
        if (countryTranslations.containsKey(country)) {
            Map<String, String> translations = countryTranslations.get(country);
            if (translations.containsKey(language)) {
                return translations.get(language);
            }
        }
        return "Country not found: " + country;
    }
}
