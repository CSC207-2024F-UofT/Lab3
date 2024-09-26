package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, String>> translations;
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

            String jsonString = Files.readString(Paths.get(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource(filename)).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);
            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String alpha3code = jsonObject.getString("alpha3");

                Map<String, String> countryTranslations = new HashMap<>();

                for (String key : jsonObject.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        countryTranslations.put(key, jsonObject.getString(key));
                    }
                }
                this.translations.put(alpha3code, countryTranslations);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (translations.containsKey(country)) {
            return new ArrayList<>(translations.get(country).keySet());
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(translations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        if (getCountries().contains(country) && getCountryLanguages(country).contains(language)) {
            return translations.get(country).get(language);
        }
        else {
            return null;
        }
    }
}
