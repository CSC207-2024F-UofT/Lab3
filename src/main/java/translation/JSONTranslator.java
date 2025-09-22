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

public class JSONTranslator implements Translator {

    private final List<String> languageCodes = new ArrayList<>();
    private final List<String> countryCodes = new ArrayList<>();
    private final Map<String, String> translations = new HashMap<>();

    public JSONTranslator() {
        this("sample.json");
    }

    public JSONTranslator(String filename) {
        try {
            String jsonString = Files.readString(
                    Paths.get(getClass().getClassLoader().getResource(filename).toURI())
            );

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);
                String countryCode = countryData.getString("alpha3");

                // record country code
                if (!countryCodes.contains(countryCode)) {
                    countryCodes.add(countryCode);
                }

                // loop through keys for languages
                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        String languageCode = key.toLowerCase();
                        String translatedName = countryData.getString(key);

                        // record translation
                        String mapKey = countryCode + "-" + languageCode;
                        translations.put(mapKey, translatedName);

                        // record language code
                        if (!languageCodes.contains(languageCode)) {
                            languageCodes.add(languageCode);
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
        return new ArrayList<>(languageCodes);
    }

    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String countryCode, String languageCode) {
        String key = countryCode.toLowerCase() + "-" + languageCode.toLowerCase();
        return translations.get(key);
    }
}
