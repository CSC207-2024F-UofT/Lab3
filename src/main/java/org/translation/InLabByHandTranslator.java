package org.translation;

import java.util.ArrayList;
import java.util.List;

// Extra Task: if your group has extra time, you can add support for another country code in this class.

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    private static String can = "can";
    public static final String CANADA = can;

    @Override
    public List<String> getCountryLanguages(String country) {
        // Done: The String "can" appears 4 times in the file.
        if (can.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "et", "ja", "fa", "ko"));
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
        return new ArrayList<>(List.of(can));
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

        // Done String literal expressions should be on the left side of an equals comparison
        String value = null;
        if (!can.equals(country)) {
            return null;
        }
        if ("de".equals(language)) {
            value = "Kanada";
        }
        else if ("en".equals(language)) {
            value = "Canada";
        }
        else if ("zh".equals(language)) {
            value = "加拿大";
        }
        else if ("es".equals(language)) {
            value = "Canadá";
        }
        else if ("et".equals(language)) {
            value = "Kanada";
        }
        else if ("ko".equals(language)) {
            value = "캐나다";
        }
        else if ("fa".equals(language)) {
            value = "کانادا";
        }
        else if ("ja".equals(language)) {
            value = "カナダ";
        }

        return value;

    }

}

