package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class

    private static ArrayList<String> countryCodes = new ArrayList<String>();
    private static ArrayList<String> languageCodes = new ArrayList<String>();
    private static JSONArray jsonData = new JSONArray();

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

            jsonData = new JSONArray(jsonString);

            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject jsonObj = jsonData.getJSONObject(i);
                countryCodes.add(jsonObj.getString("alpha3"));
            }

            JSONObject allKeys = jsonData.getJSONObject(0);
            Iterator<String> languageOptions = allKeys.keys();

            languageOptions.next();
            languageOptions.next();
            languageOptions.next();

            while (languageOptions.hasNext()) {
                languageCodes.add(languageOptions.next());
            }

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return (ArrayList) languageCodes.clone();
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        System.out.println(jsonData.length());
        return (ArrayList) countryCodes.clone();
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed

        System.out.println(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            // Find a country code, see if it's equal to the country code the user specified
            JSONObject obj = jsonData.getJSONObject(i);
            String countryCode = obj.getString("alpha3");
            System.out.println(country + " : " + countryCode);

            if (country.equals(countryCode)) {
                System.out.println("Yes");
                return obj.getString(language);
            }
        }
        return null;
    }
}
