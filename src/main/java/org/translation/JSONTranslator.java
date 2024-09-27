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

    private static JSONTranslator instance; // Singleton instance
    private ArrayList<JSONObject> countryArray = new ArrayList<>(); // Instance variable

    // private static ArrayList<JSONObject> countryArray = new ArrayList<>();

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
                countryArray.add(jsonArray.getJSONObject(i));
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> countryLanguages = new ArrayList<>();
        for (int j = 0; j < countryArray.size(); j++) {
            JSONObject countryObject = countryArray.get(j);
            if (countryObject.getString("alpha3").equals(country)) {
                JSONArray keys = countryObject.names();
                for (int i = 0; i < keys.length(); i++) {
                    String key = keys.getString(i);
                    countryLanguages.add(key);
                }
                break;
            }
        }
        countryLanguages.remove("alpha2");
        countryLanguages.remove("alpha3");
        countryLanguages.remove("id");
        return countryLanguages;
    }

    @Override
    public List<String> getCountries() {
        List<String> countryCodes = new ArrayList<>();
        for (JSONObject country : countryArray) {
            String countryCode = country.getString("alpha3");
            countryCodes.add(countryCode);
        }
        return countryCodes;
    }

    @Override
    public String translate(String country, String language) {
        for (JSONObject obj : countryArray) {
            if (country.equals(obj.getString("alpha3"))) {
                return obj.getString(language);
            }
        }
        return null;
    }
}
