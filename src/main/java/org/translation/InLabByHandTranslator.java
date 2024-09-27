package org.translation;

import java.util.ArrayList;
import java.util.List;

// Task: modify this class so that it also supports the Spanish language code "es" and
//            one more language code of your choice. Each member of your group should add
//            support for one additional langauge code on a branch; then push and create a pull request on GitHub.

// Extra Task: if your group has extra time, you can add support for another country code in this class.

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    public static final String CANADA = "can";
    public static final String JAPAN = "jpn";
    public static final String DE = "de";
    public static final String EN = "en";
    public static final String ZH = "zh";
    public static final String ES = "es";
    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */

    @Override
    public List<String> getCountryLanguages(String country) {
        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of(DE, EN, ZH, ES));
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
        return new ArrayList<>(List.of(CANADA, JAPAN));
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
        String tempString = null;
        if (country.equals(CANADA)) {
            switch (language) {
                case DE:
                    tempString = "Kanada";
                    break;
                case EN:
                    tempString = "Kanada";
                    break;
                case ZH:
                    tempString = "加拿大";
                    break;
                case ES:
                    tempString = "Canadá";
                    break;
                default:
                    break;
            }
        }
        else if (country.equals(JAPAN)) {
            if (DE.equals(language)) {
                tempString = "Japan";
            }
            else if (EN.equals(language)) {
                tempString = "Japan";
            }
            else if (ZH.equals(language)) {
                tempString = "日本";
            }
            else if (ES.equals(language)) {
                tempString = "Japón";
            }
        }
        return tempString;
    }
}
