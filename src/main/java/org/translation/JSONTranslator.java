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

    private final Map<String, Map<String, String>> countries;

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
            this.countries = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                Map<String, String> codeTable = new HashMap<>();
                for (Map.Entry<String, Object> entry : country.toMap().entrySet()) {
                    // Yes this saves "alpha2" and "alpha3" as countries technically
                    // No I don't care at all
                    if (entry.getValue() instanceof String && !entry.getKey().equals("alpha2") && !entry.getKey()
                            .equals("alpha3")) {
                        codeTable.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                this.countries.put(country.getString("alpha3"), codeTable);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        return new ArrayList<>(countries.get(country.toLowerCase()).keySet());
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countries.keySet());
    }

    @Override
    public String translate(String country, String language) {
        return countries.get(country.toLowerCase()).get(language);
    }
}