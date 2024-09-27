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

    private final List<String> codes = new ArrayList<>();
    private final List<String[]> translations = new ArrayList<>();
    private final List<String> languageCodes = new ArrayList<>();
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

            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                String countrycode = country.getString("alpha3");
                codes.add(countrycode);

                if (languageCodes.isEmpty()) {
                    for (String key : country.keySet()) {
                        if (!"alpha2".equals(key) && !"alpha3".equals(key) && !"id".equals(key)) {
                            languageCodes.add(key);
                        }
                    }
                }

                String[] ntranslations = new String[languageCodes.size()];
                for (int j = 0; j < languageCodes.size(); j++) {
                    String langCode = languageCodes.get(j);
                    ntranslations[j] = country.optString(langCode, null);
                }

                translations.add(ntranslations);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        int countryIndex = codes.indexOf(country);
        if (countryIndex == -1) {
            return new ArrayList<>();
        }
        return new ArrayList<>(languageCodes);
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(codes);
    }

    @Override
    public String translate(String country, String language) {
        String ntranslation = null;

        int countryIndex = codes.indexOf(country);
        int languageIndex = languageCodes.indexOf(language);
        if (countryIndex != -1 && languageIndex != -1) {
            ntranslation = translations.get(countryIndex)[languageIndex];
        }

        return ntranslation;
    }
}
