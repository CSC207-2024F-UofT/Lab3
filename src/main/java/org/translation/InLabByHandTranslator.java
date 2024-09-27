package org.translation;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    // Static variable definition for the country "Canada"
    public static final String CANADA = "can";

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @SuppressWarnings({"checkstyle:TrailingComment", "checkstyle:SuppressWarnings"})
    @Override
    public List<String> getCountryLanguages(String country) {
        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "ko"));
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
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @SuppressWarnings({"checkstyle:ReturnCount", "checkstyle:SuppressWarnings", "checkstyle:TrailingComment"})
    @Override
    public String translate(String country, String language) {
        if (!CANADA.equals(country)) {
            return null;
        }

        switch (language) {
            case "de":
                return "Kanada";
            case "en":
                return "Canada";
            case "zh":
                return "加拿大";
            case "es":
                return "Canadá";
            case "ko":
                return "캐나다";
            default:
                return null;
        }
    }
}
