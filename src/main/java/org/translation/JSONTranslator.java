package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    private Map<String, String> maps = new HashMap<>();
    private Map<String, Map<String, String>> maps1 = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     *
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> mapss = new HashMap<>();
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if ("id".equals(key) || "alpha2".equals(key) || "alpha3".equals(key)) {
                        continue;
                    }
                    String value = jsonObject.getString(key);
                    mapss.put(key, value);
                }
                String countrycode = jsonObject.getString("alpha3");
                maps1.put(countrycode, mapss);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> keyList = new ArrayList<>();
        if (maps1.containsKey(country)) {
            Map<String, String> languagemap = maps1.get(country);
            if (languagemap != null) {
                keyList.addAll(languagemap.keySet());
            }
        }
        return keyList;
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(maps1.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // Check if the country exists in the maps
        if (maps1.containsKey(country)) {
            // Get the language map for the specified country
            Map<String, String> languageMap = maps1.get(country);
            // Check if the language exists in the language map for the given country
            if (languageMap != null && languageMap.containsKey(language)) {
                // Return the translation for the country in the specified language
                return languageMap.get(language);
            }
        }
        // Return null if no translation is available
        return null;
    }
}

