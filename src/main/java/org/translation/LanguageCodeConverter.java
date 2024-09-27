package org.translation;

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
    private static Map<String, String> dictionary = new HashMap<>() {
    };

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
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
            lines.remove(0);

            for (String line : lines) {
                String[] items = line.split("\t");

                if (items.length == 2) {
                    dictionary.put(items[1], items[0]);
                }
                else {
                    int i = 1;
                    String countries = items[0];
                    while (i < items.length - 1) {
                        countries += " " + items[i];
                        i += 1;
                    }
                    dictionary.put(items[items.length - 1], countries);
                }
            }
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
    public static String fromLanguageCode(String code) {
        String lang = dictionary.get(code);
        if (lang != null) {
            return lang;
        }
        return "Language not found.";
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     * */
    public static String fromLanguage(String language) {
        for (String key : dictionary.keySet()) {
            if (dictionary.get(key).equals(language)) {
                return key;
            }
        }
        return "Language code not found.";
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return dictionary.size();
    }
}
