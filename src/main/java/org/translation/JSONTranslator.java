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

    // maybe did: pick appropriate instance variables for this class
    public static Map<String,Map<String, String>> country_name_translations = new HashMap<>();

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
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String alpha3 = jsonObject.getString("alpha3");
                HashMap<String, String> innerMap = new HashMap<>();

                // Populate the inner map with key-value pairs
                for (String key : jsonObject.keySet()) {
                    if (!key.equals("alpha3") && !key.equals("id") && !key.equals("alpha2")) {
                        innerMap.put(key, jsonObject.getString(key));
                    }
                }
                // Add the inner map to the outer map
                country_name_translations.put(alpha3, innerMap);
            }


        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        HashMap<String, String> innerMap = (HashMap<String, String>) country_name_translations.get(country);
        List<String> translationsList = new ArrayList<>();

        for (String key : innerMap.keySet()) {
            translationsList.add(key + ": " + innerMap.get(key));
        }

        return translationsList;

    }

    @Override
    public List<String> getCountries() {
        // basically we grab all the keys from the hashmap, then cast them to an arraylist.
        var temp1 = country_name_translations.keySet();

        return new ArrayList<>(temp1);
    }

    @Override
    public String translate(String country, String language) {
        for (String key : country_name_translations.keySet()) {
            if(key.equals(country)){
                return country_name_translations.get(key).get(language);
            }
        }
        return null;
    }
}
