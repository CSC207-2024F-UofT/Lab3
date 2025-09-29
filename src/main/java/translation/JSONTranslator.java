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
    private final Map<String, String> translations = new HashMap<>();

    public JSONTranslator() {
        this("sample.json");
    }

    public JSONTranslator(String filename) {
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);

                // *** THIS IS THE FIX ***
                // The country code from the JSON file is uppercase ("DZA").
                // We must convert it to lowercase to match the other converters.
                String countryCode = countryData.getString("alpha3").toLowerCase();

                if (!countryCodes.contains(countryCode)) {
                    countryCodes.add(countryCode);
                }

                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        String languageCode = key;
                        String translatedName = countryData.getString(key);

                        // Now the key is created as "dza-af", which will match the lookup key.
                        translations.put(countryCode + "-" + languageCode, translatedName);

                        if (!languageCodes.contains(languageCode)) {
                            languageCodes.add(languageCode);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    // No changes needed to the methods below

    @Override
    public List<String> getLanguageCodes() {
        return new ArrayList<>(languageCodes);
    }

    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String countryCode, String languageCode) {
        if (countryCode == null || languageCode == null) return null;
        // This lookup will now succeed because the keys in the map are also lowercase.
        return translations.get(countryCode.toLowerCase() + "-" + languageCode.toLowerCase());
    }
}