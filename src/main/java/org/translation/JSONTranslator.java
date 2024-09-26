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

    private Map<String, Map<String, String>> countryToLanguages = new HashMap<>();
    private List<String> countries = new ArrayList<>();

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
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);

                String countryCode = countryObject.getString("alpha3");
                countries.add(countryCode);

                Map<String, String> translations = new HashMap<>();
                for (String key : countryObject.keySet()) {
                    if (!"alpha2".equals(key) && !"alpha3".equals(key) && !"id".equals(key)) {
                        translations.put(key, countryObject.getString(key));
                    }
                }
                countryToLanguages.put(countryCode, translations);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the list of language codes for a given country.
     *
     * @param country the country code (e.g., "af" for Afghanistan)
     * @return a list of language codes for that country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        Map<String, String> languages = countryToLanguages.get(country.toLowerCase());
        if (languages == null) {
            return new ArrayList<>();
        }
        else {
            return new ArrayList<>(languages.keySet());
        }
    }

    /**
     * Returns the list of country codes.
     *
     * @return a list of country codes
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countries);
    }

    /**
     * Translates the country name into the specified language.
     *
     * @param country  the country code (e.g., "af" for Afghanistan)
     * @param language the language code (e.g., "en" for English)
     * @return the translated name of the country, or null if not found
     */
    @Override
    public String translate(String country, String language) {
        final String c = country.toLowerCase();
        final String l = language.toLowerCase();
        Map<String, String> languages = countryToLanguages.get(c);
        if (languages != null && languages.containsKey(l)) {
            return languages.get(l);
        }
        return null;
    }
}
