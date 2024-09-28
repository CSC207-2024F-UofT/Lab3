package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    private ArrayList<String> lang;
    private ArrayList<String> languageCode;

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

            for (String line : lines) {
                String[] parts = line.split("/t");
                if (parts.length == 2) {
                    this.lang.add(parts[0]);
                    this.languageCode.add(parts[1]);
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
    public String fromLanguageCode(String code) {
        for (int i = 0; i < this.languageCode.size(); i++) {
            if (code.equals(languageCode.get(i))) {
                return this.lang.get(i);
            }
        }
        return null;
    }
    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */

    public String fromLanguage(String language) {
        String result = null;
        for (int i = 0; i < this.lang.size(); i++) {
            String[] parts1 = this.lang.get(i).split(",");
            for (String langs : parts1) {
                if (language.equals(langs)) {
                    result = this.languageCode.get(i);
                }
                break;
            }

        }
        return result;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return this.lang.size();
    }
}
