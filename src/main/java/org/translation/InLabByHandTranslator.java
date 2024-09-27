package org.translation;

import java.util.ArrayList;
import java.util.List;

// TODO Task: modify this class so that it also supports the Spanish language code "es" and
//            one more language code of your choice. Each member of your group should add
//            (I added Japanese and Spanish already)
//            support for one additional language code on a branch; then push and create a pull request on GitHub.

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
    private final String canada = "can";

    @Override
    public List<String> getCountryLanguages(String country) {
        if (canada.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "ja"));
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
        return new ArrayList<>(List.of(canada));
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
        String name = "";

        //        if (!country.equals(Canada12)) {
        //            return null;
        //        }
        if ("de".equals(language)) {
            name = "Kanada";
        }
        if ("es".equals(language)) {
            name = "Canadá";
        }
        if ("ja".equals(language)) {
            name = "カナダ";
        }
        else if ("en".equals(language)) {
            name = "Canada";
        }
        else if ("zh".equals(language)) {
            name = "加拿大";
        }
        //        else {
        //            return null;
        //        }
        return name;
    }
}