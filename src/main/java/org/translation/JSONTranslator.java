package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private ArrayList<String> countryCodes = new ArrayList<>();
    private Map<String, ArrayList<String>> countryToLanguages = new HashMap<>();
    private Map<String, Map<String, String>> countryToLangToTransl = new HashMap<>();
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
                String c = jsonObject.getString("alpha3");
                countryCodes.add(c);
                Set<String> allKeys = jsonObject.keySet();
                allKeys.remove("id");
                allKeys.remove("alpha2");
                allKeys.remove("alpha3");
                ArrayList<String> lang = new ArrayList<String>();
                Map<String, String> a = new HashMap<String, String>();
                for (String key : allKeys) {
                    if (jsonObject.has(key)) {
                        lang.add(key);
                        a.put(key, jsonObject.getString(key));

                    }
                }
                this.countryToLanguages.put(c, lang);
                this.countryToLangToTransl.put(c, a);
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
        return this.countryToLanguages.get(country);
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
        // Done complete this method using your instance variables as needed
        if (this.countryToLangToTransl.containsKey(country)) {
            if (this.countryToLangToTransl.get(country).containsKey(language)) {
                return this.countryToLangToTransl.get(country).get(language);
            }
        }
        return null;
    }
}
