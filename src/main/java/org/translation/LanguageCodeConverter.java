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

    private Map<String, String> codeToLanguage = new HashMap<>();
    private Map<String, String> languageToCode = new HashMap<>();

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

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\t");
                    if (parts.length == 2) {
                        String language = parts[0].trim();
                        String code = parts[1].trim();

                        String[] languageVariations = language.split(",");
                        for (String lang : languageVariations) {
                            String cleanedLanguage = lang.trim();
                            codeToLanguage.put(code, cleanedLanguage);
                            languageToCode.put(cleanedLanguage, code);

                            if (cleanedLanguage.contains("(")) {
                                String withoutParentheses = cleanedLanguage.replaceAll(
                                        "\\s*\\(.*\\)", "").trim();
                                String insideParentheses = cleanedLanguage.replaceAll(
                                        ".*\\((.*)\\).*", "$1").trim();

                                languageToCode.put(withoutParentheses, code);
                                languageToCode.put(insideParentheses, code);
                            }
                        }
                    }
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
        return codeToLanguage.get(code);
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        return languageToCode.get(language);
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return codeToLanguage.size();
    }
}
