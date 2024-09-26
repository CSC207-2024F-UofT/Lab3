package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Index where alpha3 code is stored
    private static final int ALPHA3_INDEX = 2;

    // HashMap storing all country info in the converter
    private Map<String, List<Map>> translator = new HashMap<>();

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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                List<Map> tempList = new ArrayList<>();
                String[] keys = JSONObject.getNames(jsonObject);
                JSONArray values = getValues(jsonObject);
                String alpha3Code = new String();

                // int j = ALPHA3_INDEX + 1
                for (int j = 0; j < jsonObject.length(); j++) {
                    if ("id".equals(keys[j]) || "alpha2".equals(keys[j]) || "alpha3".equals(keys[j])) {
                        if ("alpha3".equals(keys[j])) {
                            alpha3Code = values.getString(j);
                        }
                        continue;
                    }
                    Map<String, String> tempMap = new HashMap<>();
                    Object value = values.get(j);
                    if (value instanceof String) {
                        tempMap.put(keys[j], (String) value);
                    }
                    else {
                        tempMap.put(keys[j], value.toString());
                    }
                    // tempMap.put(keys[j], values.getString(j));
                    tempList.add(tempMap);
                }
                this.translator.put(alpha3Code, tempList);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static JSONArray getValues(JSONObject jsonObject) {
        JSONArray values = new JSONArray();
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            values.put(jsonObject.get(key));
        }
        return values;
    }

    /**
     * Returns the language codes for all languages whose translations are
     * available for the given country.
     * @param country the alpha3 code of the country
     * @return list of language codes which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        List<Map> translations = translator.get(country);
        List<String> languages = new ArrayList<>();
        for (Map map : translations) {
            languages.add(map.keySet().iterator().next().toString());
        }
        return languages;
    }

    /**
     * Returns the country codes for all countries whose translations are
     * available from this Translator.
     * @return list of country codes for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        translator.keySet().forEach(key -> countries.add(key));
        return countries;
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     * @param country the country code
     * @param language the language code
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        List<Map> translations = translator.get(country);
        for (Map map : translations) {
            if (map.containsKey(language)) {
                return map.get(language).toString();
            }
        }
        return null;
    }
}
