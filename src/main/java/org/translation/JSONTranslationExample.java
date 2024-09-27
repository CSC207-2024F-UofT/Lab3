package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A minimal example of reading and using the JSON data from resources/sample.json.
 */
public class JSONTranslationExample {

    public static final int CANADA_INDEX = 30;
    public static final int AUSTRALIA_INDEX = 8;
    private final JSONArray jsonArray;

    // Note: CheckStyle is configured so that we are allowed to omit javadoc for constructors
    public JSONTranslationExample() {
        try {
            // this next line of code reads in a file from the resources folder as a String,
            // which we then create a new JSONArray object from.
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader()
                    .getResource("sample.json").toURI()));
            this.jsonArray = new JSONArray(jsonString);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the Spanish translation of Canada.
     * @return the Spanish translation of Canada
     */
    public String getCanadaCountryNameSpanishTranslation() {

        JSONObject canada = jsonArray.getJSONObject(CANADA_INDEX);
        return canada.getString("es");
    }

    /**
     * Returns the Japanese translation of Australia.
     * @return the Japanese translation of Australia
     */
    public String getAustraliaCountryNameJapaneseTranslation() {

        JSONObject australia = jsonArray.getJSONObject(AUSTRALIA_INDEX);
        return australia.getString("ja");
    }

    /**
     * Returns the name of the country based on the provided country and language codes.
     * @param countryCode the country, as its three-letter code.
     * @param languageCode the language to translate to, as its two-letter code.
     * @return the translation of country to the given language or "Country not found" if there is no translation.
     */
    public String getCountryNameTranslation(String countryCode, String languageCode) {

        JSONObject country = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("alpha3").equals(countryCode)) {
                country = jsonArray.getJSONObject(i);
            }
        }
        if (country == null || !country.has(languageCode)) {
            return "Country not found";
        }
        else {
            return country.getString(languageCode);
        }
    }

    /**
     * Prints the Spanish translation of Canada.
     * @param args not used
     */
    public static void main(String[] args) {
        JSONTranslationExample jsonTranslationExample = new JSONTranslationExample();

        System.out.println(jsonTranslationExample.getCanadaCountryNameSpanishTranslation());
        String translation = jsonTranslationExample.getCountryNameTranslation("can", "es");
        System.out.println(translation);

        System.out.println(jsonTranslationExample.getAustraliaCountryNameJapaneseTranslation());
        String translation2 = jsonTranslationExample.getCountryNameTranslation("aus", "ja");
        System.out.println(translation2);
    }
}
