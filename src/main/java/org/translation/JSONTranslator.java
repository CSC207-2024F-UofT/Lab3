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

    private static final List<String> SKIP_KEYS = List.of("id", "alpha2", "alpha3");

    private final Map<String, Map<String, String>> countryToLanguageTranslationMap = new HashMap<>();

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
            for (var object : jsonArray) {
                if (object instanceof JSONObject jsonObject) {
                    var country = jsonObject.getString("alpha3");
                    if (country == null) {
                        continue;
                    }
                    var map = new HashMap<String, String>();
                    for (var key : jsonObject.keySet()) {
                        if (SKIP_KEYS.contains(key)) {
                            continue;
                        }
                        map.put(key, jsonObject.getString(key));
                    }
                    countryToLanguageTranslationMap.put(country, map);
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        return countryToLanguageTranslationMap.get(country).keySet().stream().toList();
    }

    @Override
    public List<String> getCountries() {
        return countryToLanguageTranslationMap.keySet().stream().toList();
    }

    @Override
    public String translate(String country, String language) {
        return countryToLanguageTranslationMap.get(country).get(language);
    }
}
