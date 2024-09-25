package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final JSONArray jsonArray;
    private final int keyNumber = 4;

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

            this.jsonArray = new JSONArray(jsonString);

        }

        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String alpha3 = "alpha3";
            String alpha2 = "alpha2";
            String id = "id";
            if (jsonArray.getJSONObject(i).getString(alpha3).equals(country)) {
                for (String key : jsonArray.getJSONObject(i).keySet()) {
                    if (!key.equals(alpha3) && !key.equals(alpha2) && !key.equals(id)) {
                        languages.add(key);
                    }
                }
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        String alpha3 = "alpha3";
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).keySet().size() >= keyNumber) {
                countries.add(jsonArray.getJSONObject(i).getString(alpha3));
            }
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("alpha3").equals(country)) {
                return jsonObject.getString(language.toLowerCase());
            }
        }
        return "Country not found";
    }
}
