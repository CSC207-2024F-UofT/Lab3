package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // Indexes where country name and language are stored on each line
    private static final int COUNTRY_NAME_INDEX = 0;
    private static final int LANGUAGE_CODE_INDEX = 1;

    // HashMap storing all country-code key-value pairs in the converter
    private Map<String, String> codeMap = new HashMap<>();

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
            // Start at index 1 to skip the header
            for (String line : lines.subList(1, lines.size())) {
                String[] values = line.split("\t");
                this.codeMap.put(values[COUNTRY_NAME_INDEX], values[LANGUAGE_CODE_INDEX]);
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
    public String fromLanguageCode(String code) {
        for (Map.Entry<String, String> entry : codeMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(code)) {
                return entry.getKey();
            }
        }
        return "Language Code Not Found";
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        for (Map.Entry<String, String> entry : codeMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(language)) {
                return entry.getValue();
            }
        }
        return "Language Not Found";
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return codeMap.size();
    }
}
