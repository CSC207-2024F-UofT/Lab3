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
public class JSONTranslator extends CountryCodeConverter implements Translator {
    private ArrayList<String> countires;

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
                this.countires.add(jsonArray.getString(i));
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> countrieslang = new ArrayList<>();
        String countrycode = this.fromCountryCode(country);
        for (int i = 0; i < this.countires.size(); i++) {
            JSONObject countrylanguage = new JSONObject(this.countires.get(i));
            if (countrylanguage.getString("alpha3").equals(countrycode)) {
                Iterator<String> keys = countrylanguage.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    countrieslang.add(key);
                }
            }
        }
        for (String lang : countrieslang) {
            if ("id".equals(lang) || "alpha2".equals(lang) || "alpha3".equals(lang)) {
                countrieslang.remove(lang);
            }
        }
        return countrieslang;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>();
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        return null;
    }

    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:SuppressWarnings"})
    public ArrayList<String> getCountires() {
        return countires;
    }
}

