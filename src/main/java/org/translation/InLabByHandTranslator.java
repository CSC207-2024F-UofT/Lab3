package org.translation;

import java.util.ArrayList;
import java.util.List;

// TODO Task: modify this class so that it also supports the Spanish language code "es" and
//            one more language code of your choice. Each member of your group should add
//            support for one additional langauge code on a branch; then push and create a pull request on GitHub.

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
    public static final String CANADA = can;
    private String can = "can";

    @Override
    public List<String> getCountryLanguages(String country) {
        // Checkstyle: The String "can" appears 4 times in the file.
        if (can.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh"));
        }
        return new ArrayList<>();
    }

    // Checkstyle: Static variable definition in wrong order.

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
        // Checkstyle: Return count is 5 (max allowed for non-void methods/ lambdas is 2).
        // Checkstyle: String literal expressions should be on the left side of an equals comparison
        String answer;

        if (!country.equals(can)) {
            answer = null;
        }
        if ("de".equals(language)) {
            answer = "Kanada";
        }
        else if ("en".equals(language)) {
            answer = "Canada";
        }
        else if ("zh".equals(language)) {
            answer = "加拿大";
        }
        else {
            return null;
        }
        return answer;
    }
}
