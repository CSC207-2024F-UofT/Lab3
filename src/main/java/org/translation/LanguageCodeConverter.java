package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // task 1: pick appropriate instance variables to store the data necessary for this class.

    // We'll store the code of the language as the key of the map, and the name of the country as the value.

    private Map<String, String> directory = new HashMap<>();

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

            // Task 2: use lines to populate the instance variable
            // you might find it convenient to create an iterator using lines.iterator()

            Iterator<String> iterate = lines.iterator();
            iterate.next();
            while (iterate.hasNext()) {
                String line = iterate.next();
                int lastIndex = line.lastIndexOf("\t");
                this.directory.put(line.substring(lastIndex + 1), line.substring(0, lastIndex));
            }
        }
        // The } needs to be alone in this line due to the custom configuration.

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
        // Task 3: update this code to use your instance variable to return the correct value
        return this.directory.get(code);
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */

    public String fromLanguage(String language) {
        // Task 4: update this code to use your instance variable to return the correct value
        String countryCode = "";
        for (Map.Entry<String, String> entry : this.directory.entrySet()) {
            if (entry.getValue().equals(language)) {
                countryCode = entry.getKey();
                break;
            }
        }
        return countryCode;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return this.directory.size();
    }
}
