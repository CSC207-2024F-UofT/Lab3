package org.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {

    // Moved static variable definition to the correct order
    public static final String CANADA = "can";
    public static final String USA = "usa";

    private static final Map<String, Map<String, String>> TRANSLATIONS = new HashMap<>();

    static {
        // Initialize translations for Canada
        Map<String, String> canadaTranslations = new HashMap<>();
        canadaTranslations.put("de", "Kanada");
        canadaTranslations.put("en", "Canada");
        canadaTranslations.put("es", "Canadá");
        canadaTranslations.put("fr", "Canada");
        canadaTranslations.put("zh", "加拿大");

        // Initialize translations for USA (extra task)
        Map<String, String> usaTranslations = new HashMap<>();
        usaTranslations.put("de", "Vereinigte Staaten");
        usaTranslations.put("en", "United States");
        usaTranslations.put("es", "Estados Unidos");
        usaTranslations.put("fr", "États-Unis");
        usaTranslations.put("zh", "美国");

        TRANSLATIONS.put(CANADA, canadaTranslations);
        TRANSLATIONS.put(USA, usaTranslations);
    }

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        if (TRANSLATIONS.containsKey(country)) {
            return new ArrayList<>(TRANSLATIONS.get(country).keySet());
        }
        return new ArrayList<>();
    }

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(TRANSLATIONS.keySet());
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        String translation = null;
        if (TRANSLATIONS.containsKey(country)) {
            translation = TRANSLATIONS.get(country).get(language);
        }
        return translation;
    }
}
