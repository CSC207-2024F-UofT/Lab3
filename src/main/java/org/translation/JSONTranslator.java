package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class
    private final JSONArray jsonArray;
    private final Dictionary<String, ArrayList<String>> countryLanguages;
    private final ArrayList<String> totalCountries;

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
        final String alpha3 = "alpha3";
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);
            this.countryLanguages = new Hashtable<>();
            this.totalCountries = new ArrayList<>();
            // Code to populate these instance variables

            // Code to populate countryLanguages and totalCountries
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject country = jsonArray.getJSONObject(i);
                String countryCode = country.getString(alpha3);

                if (!" ".equals(countryCode)) {
                    this.totalCountries.add(countryCode);
                }

                ArrayList<String> languages = new ArrayList<>();

                for (String key : country.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !alpha3.equals(key)) {
                        languages.add(key);
                    }
                }
                this.countryLanguages.put(countryCode, languages);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        //  Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return this.countryLanguages.get(country);
    }

    @Override
    public List<String> getCountries() {
        //  Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return this.totalCountries;
    }

    @Override
    public String translate(String country, String language) {
        //  Task: complete this method using your instance variables as needed
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("alpha3").equals(country)) {
                return jsonObject.getString(language);
            }
        }
        return "Country not found";
    }
}
