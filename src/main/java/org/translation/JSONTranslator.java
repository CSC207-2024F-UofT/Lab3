package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // (Implemented): pick appropriate instance variables for this class
    private LanguageCodeConverter myLanguageCodeConverter;

    // Collection of countries
    private String[] countryIds;

    // Collection of Entry
    private JSONObject[] localizedCountryName;

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

            // (Implemented): use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

            // Instantiate the use of other classes
            this.myLanguageCodeConverter = new LanguageCodeConverter();
            this.countryIds = new String[jsonArray.length()];

            this.localizedCountryName = new JSONObject[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                countryIds[i] = jsonObject.getString("alpha3");

                // CountryEntries can only have languages inside
                JSONObject newEntry = new JSONObject();
                List<String> currentJsonObject = new ArrayList<>(jsonObject.keySet());
                for (String keyJson : currentJsonObject) {
                    Object value = jsonObject.get(keyJson);
                    if (value instanceof String && this.myLanguageCodeConverter.fromLanguageCode(keyJson) != null) {
                        newEntry.put(keyJson, jsonObject.getString(keyJson));
                    }
                }

                localizedCountryName[i] = newEntry;
            }
        }

        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // (Implemented): return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countryLanguages = new ArrayList<>();

        int checkCountryId = -1;
        for (int i = 0; i < countryIds.length; i++) {
            if (countryIds[i].equals(country)) {
                checkCountryId = i;
                break;
            }
        }

        if (checkCountryId != -1) {
            JSONObject currentEntry = this.localizedCountryName[checkCountryId];
            List<String> currentEntryKeys = new ArrayList<>(currentEntry.keySet());

            countryLanguages.addAll(currentEntryKeys);
        }

        return countryLanguages;
    }

    @Override
    public List<String> getCountries() {
        // (Implemented) return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countriesList = new ArrayList<>();
        Collections.addAll(countriesList, countryIds);
        return countriesList;
    }

    @Override
    public String translate(String country, String language) {
        // (Implemented) complete this method using your instance variables as needed
        String translatedText = null;

        int checkCountryId = -1;
        for (int i = 0; i < countryIds.length; i++) {
            if (countryIds[i].equals(country)) {
                checkCountryId = i;
                break;
            }
        }

        JSONObject mainEntry = this.localizedCountryName[checkCountryId];
        translatedText = mainEntry.getString(language);

        return translatedText;
    }
}
