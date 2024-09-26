package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    // (Implemented) Private Attributes
    private Map<String, String> languageCode;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources' folder.
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

            this.languageCode = new HashMap<>();

            // Tip using lines.iterator()

            Iterator<String> linesIterator = lines.iterator();
            linesIterator.next();
            while (linesIterator.hasNext()) {
                String oneRow = linesIterator.next();
                String[] words = oneRow.split("\t");
                this.languageCode.put(words[0], words[1]);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
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
        for (Map.Entry<String, String> entry : this.languageCode.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();
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
        return this.languageCode.get(language);
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return this.languageCode.size();
    }
}
