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

    private List<JSONObject> countryList = new ArrayList<>();

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
                JSONObject country = jsonArray.getJSONObject(i);
                countryList.add(country);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> keysList = new ArrayList<>();
        Set<String> ignoredKeys = new HashSet<>();
        ignoredKeys.add("id");
        ignoredKeys.add("alpha2");
        ignoredKeys.add("alpha3");
        for (JSONObject jsonObject : countryList) {
            if (jsonObject.getString("alpha3").equals(country)) {
                Iterator<String> keys = jsonObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    // Only add the key if it is not in the ignoredKeys set
                    if (!ignoredKeys.contains(key)) {
                        keysList.add(key);
                    }

                }

            }

        }
        return keysList;
    }

    @Override
    public List<String> getCountries() {
        List<String> countriesList = new ArrayList<>();
        for (JSONObject jsonObject : countryList) {
            countriesList.add(jsonObject.getString("alpha3"));
        }
        return countriesList;
    }

    @Override
    public String translate(String country, String language) {
        for (JSONObject jsonObject : countryList) {
            if (jsonObject.getString("alpha3").equals(country)) {
                return jsonObject.getString(language);
            }
        }
        return null;
    }
}
