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

    // Instance variables
    private final Map<String, Map<String, String>> countryTranslations;
    private final List<String> countries;

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

            // Initialize the data structures
            countryTranslations = new HashMap<>();
            countries = new ArrayList<>();

            // Populate the structures from JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String countryCode = countryObject.getString("alpha3");
                Map<String, String> translations = new HashMap<>();

                // Add all language translations for the country
                for (String key : countryObject.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        translations.put(key, countryObject.getString(key));
                    }
                }

                countryTranslations.put(countryCode, translations);
                countries.add(countryCode);

            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (!countries.contains(country)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(countryTranslations.get(country).keySet());
    }

    @Override
    public List<String> getCountries() {
        // Return a new copy of the countries list to avoid aliasing
        return new ArrayList<>(countries);
    }

    @Override
    public String translate(String country, String language) {
        // Check if the country exists in the map
        if (countryTranslations.containsKey(country)) {
            Map<String, String> translations = countryTranslations.get(country);
            // Check if the language exists for the country
            // If the language doesn't exist, return null or a default message
            return translations.getOrDefault(language, "Country not found");
        }
        else {
            // If the country doesn't exist, return null or a default message
            return "Country not found";
        }
    }
}
