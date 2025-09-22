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

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject countryData = jsonArray.getJSONObject(i);
                String countryCode = countryData.getString("alpha3");

                List<String> languages = new ArrayList<>();

                // TODO Task C: record this countryCode in the correct instance variable

                // iterate through the other keys to get the information that we need
                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        String languageCode = key;
                        // TODO Task C: record this translation in the appropriate instance variable

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
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String countryCode, String languageCode) {
        if (countryCode == null || languageCode == null){
            return null;
        }
        String key = countryCode + "-" + languageCode;
        // Task C: complete this method using your instance variables as needed
        return "JSONTranslator's translate method is not implemented!";
    }
}
