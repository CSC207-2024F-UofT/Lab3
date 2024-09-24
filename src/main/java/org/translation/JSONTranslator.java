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
    private final JSONArray jsonArray;
    private final ArrayList<String> languages;
    private final ArrayList<String> countries;

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
            this.languages = new ArrayList<>();
            this.countries = new ArrayList<>();

            // TODO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

            JSONObject curr;
            for (int i = 0; i < jsonArray.length(); i++) {
                curr = jsonArray.getJSONObject(i);
                this.countries.add(curr.getString("alpha3"));
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object

        int index = 0;
        int beginningIndex = 3;
        // Find the target country (parameter)
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("alpha3").equals(country)) {
                index = i;
                break;
            }
        }

        // Add the language options within this country object

        JSONObject sample = jsonArray.getJSONObject(index);
        Iterator<String> keys = sample.keys();
        keys.forEachRemaining(this.languages::add);

        int j = 0;
        while (j < beginningIndex) {
            this.languages.remove(0);
            j++;
        }
        return this.languages;
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return this.countries;
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        int index;
        index = this.countries.indexOf(country);
        JSONObject countryObj = jsonArray.getJSONObject(index);

        return countryObj.getString(language);
    }
}
