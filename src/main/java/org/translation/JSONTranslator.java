package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.HTMLDocument;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private ArrayList<String> countryCodes = new ArrayList<String>();
    private Map<String, ArrayList<String>> countryTolanguages = new HashMap<String, ArrayList<String>>();
    private ArrayList<Integer> ids = new ArrayList<Integer>();

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

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ids.add(jsonObject.getInt("id"));
                String c = jsonObject.getString("alpha3");
                countryCodes.add(c);
                Set<String> allKeys = jsonObject.keySet();
                allKeys.remove("id");
                allKeys.remove("alpha2");
                allKeys.remove("alpha3");
                ArrayList<String> lang = new ArrayList<String>();
                for (String key : allKeys) {
                    if (jsonObject.has(key)) {
                        lang.add(key);
                    }
                }
                this.countryTolanguages.put(c, lang);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // Done return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> allLanguages = this.countryTolanguages.get(country);

        return allLanguages;
    }

    @Override
    public List<String> getCountries() {
        // Done return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> allCountries = new ArrayList<String>();
        for (String code : this.countryCodes) {
            allCountries.add(new String(code));
        }
        return allCountries;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed

        return null;
    }
}
