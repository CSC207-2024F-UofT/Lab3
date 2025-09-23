package translation;

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
 * An implementation of the Translator interface that reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final List<String> languageCodes = new ArrayList<>();
    // final
        //You cannot reassign the variable to a new object, but you can modify the object itself.
        // always refer to the same list object.
        // myList = new ArrayList<>();  // ❌ ERROR: cannot reassign final variable

    // Program to an interface, not to a specific implementation.
        //if you wanted to change to a LinkedList
            // private final List<String> languageCodes = new LinkedList<>();
        // using List as the type and ArrayList as the concrete object, you get flexibility without being tied to one data structure.

    private final List<String> countryCodes = new ArrayList<>();

    // the key used is "countryCode-languageCode"; the value is the translated country name
    private final Map<String, String> translations = new HashMap<>();
    /**
     * Construct a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Construct a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            //getClass().getClassLoader().getResource(filename)
                // Finds the file in the resources folder. Returns a URL object.
            //.toURI()
                // Converts the URL to a URI, which Paths.get() can use.
            // Paths.get(...)
                // Gets a Path object for the file.
            //Files.readString(...)
                // reads the entire file into a String
            // Now contains a long JSON string like the one you pasted.


            JSONArray jsonArray = new JSONArray(jsonString);
            // This line parses the jsonString into a JSONArray object from the org.json library.
                //parse: Convert a string into structured data.
            // It contains multiple JSONObject elements.
            //Each JSONObject represents a country.
            /*
            [
  { "id": 4, "alpha2": "af", "en": "Afghanistan", "fr": "Afghanistan", "zh": "阿富汗" },
  { "id": 8, "alpha2": "al", "en": "Albania", "fr": "Albanie", "zh": "阿尔巴尼亚" }
]

             */

            /*
            JAVA:
            JSONArray jsonArray = new JSONArray(...);

// First country:
JSONObject obj0 = jsonArray.getJSONObject(0);
obj0.getInt("id");        // 4
obj0.getString("alpha2"); // "af"
obj0.getString("en");     // "Afghanistan"
obj0.getString("zh");     // "阿富汗"

// Second country:
JSONObject obj1 = jsonArray.getJSONObject(1);
obj1.getInt("id");        // 8
obj1.getString("alpha2"); // "al"
obj1.getString("fr");     // "Albanie"

             */

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject countryData = jsonArray.getJSONObject(i);
                String countryCode = countryData.getString("alpha3");

                List<String> languages = new ArrayList<>();

                // TODO Task C: record this countryCode in the correct instance variable
                countryCodes.add(countryCode);

                // iterate through the other keys to get the information that we need
                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        String languageCode = key;
                        // TODO Task C: record this translation in the appropriate instance variable
                        translations.put(countryCode +"-" + languageCode, countryData.getString(key));
                        // coutnryData.get(key) == object
                        // JSON allows any type as a value, So get("alpha3") has to return the most generic type: Object.

                        if (!languageCodes.contains(languageCode)) {
                            languageCodes.add(languageCode);
                        }

                        if (!languages.contains(languageCode)) {
                            languages.add(languageCode);
                        }
                    }
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getLanguageCodes() {
        // TODO Task C: return a copy of the language codes

        return new ArrayList<>(languageCodes);
        // creates a new, separate list that looks the same:
    }

    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String countryCode, String languageCode) {
        // TODO Task C: complete this method using your instance variables as needed
        if (!countryCodes.contains(countryCode)) {
            return "CountryCode does not exist";
        }
        if (!languageCodes.contains(languageCode)) {
            return "languageCode does not exist";
            }
        if (!translations.containsKey(countryCode + "-" + languageCode)) {
            return "Translation does not exist";
        }

        return translations.get(countryCode + "-" + languageCode);

    }
}
