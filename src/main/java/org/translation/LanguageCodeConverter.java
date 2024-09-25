package org.translation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    /*
    // Used for testing file
    public static void main(String[] args) {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        System.out.println(converter.fromLanguageCode("el"));
        System.out.println(converter.fromLanguage("Albanian"));
    }
    */

    private final JSONArray jsonArray;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resource folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // Removes un-necessary first line
            lines.remove(0);
            String jsonData = "[{";

            for (String line : lines) {
                int charBreak = line.indexOf('\t');
                String key = line.substring(0, charBreak);
                String value = line.substring(charBreak + 1);
                jsonData += "\"" + key + "\" : \"" + value + "\", ";
            }

            // Removes ' ,' at the end of jsonData
            jsonData = jsonData.substring(0, jsonData.length() - 2);
            jsonData += "}]";

            // Puts the data in the JSONArray
            jsonArray = new JSONArray(jsonData);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task: update this code to use your instance variable to return the correct value
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        for (String key : jsonObject.keySet()) {
            if (jsonObject.getString(key).equals(code)) {
                return key;
            }
        }
        return "Code does not exist";
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TODO Task: update this code to use your instance variable to return the correct value
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        for (String key : jsonObject.keySet()) {
            if (key.equals(language)) {
                return jsonObject.getString(key);
            }
        }
        return "Language does not exist";
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // TODO Task: update this code to use your instance variable to return the correct value
        return jsonArray.length();
    }
}
