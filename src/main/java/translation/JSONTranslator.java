package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
                countryCodes.add(countryCode);

                // iterate through the other keys to get the information that we need
                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {

                        String languageCode = key;
                        int numLanguageCodes = languageCodes.size();
                        if (numLanguageCodes < 35) {
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
    }

    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String countryCode, String languageCode) {
        // TODO Task C: complete this method using your instance variables as needed
        int numCountryCodes = countryCodes.size();

        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("sample.json").toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            String translation = null;
            for (int i = 0; i < numCountryCodes; i++) {
                if (Objects.equals(countryCode, countryCodes.get(i))) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    translation = jsonObject.getString(languageCode);
                }
            }
            return translation;
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}

/*
choose the country
[afg, alb, dza ... ]

Choose the language
[ar, bg, cs ...]
/*
 */