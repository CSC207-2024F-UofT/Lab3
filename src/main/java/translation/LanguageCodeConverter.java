package translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class provides the services of: <br/>
 * - converting language codes to their names <br/>
 * - converting language names to their codes
 */
public class LanguageCodeConverter {

    private final Map<String, String> languageCodeToLanguage = new HashMap<>();
    private final Map<String, String> languageToLanguageCode = new HashMap<>();

    /**
     * Default constructor that loads the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor that allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resources file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            Iterator<String> iterator = lines.iterator();
            iterator.next(); // skip the first line
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.isBlank()) continue; // Defensive check for empty lines

                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String languageName = parts[0].trim();
                    String languageCode = parts[1].trim();

                    // *** THIS IS THE FIX ***
                    // Now, we populate BOTH maps for two-way conversion.
                    languageCodeToLanguage.put(languageCode, languageName);
                    languageToLanguageCode.put(languageName, languageCode);
                }
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * NEW METHOD: This is required for the GUI to populate its dropdown menu.
     * Returns a sorted list of all language names.
     * @return A sorted List of language names.
     */
    public List<String> getLanguageNames() {
        List<String> sortedNames = new ArrayList<>(languageToLanguageCode.keySet());
        Collections.sort(sortedNames);
        return sortedNames;
    }

    /**
     * Return the name of the language for the given language code.
     * @param code the 2-letter language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        return languageCodeToLanguage.get(code);
    }

    /**
     * Return the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // This method will now work correctly because its map is no longer empty.
        return languageToLanguageCode.get(language);
    }

    /**
     * Return how many languages are included in this language code converter.
     * @return how many languages are included in this language code converter.
     */
    public int getNumLanguages() {
        return languageCodeToLanguage.size();
    }
}