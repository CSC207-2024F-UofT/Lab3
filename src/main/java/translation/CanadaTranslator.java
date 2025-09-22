package translation;

import java.util.ArrayList;
import java.util.List;

// Task 1: as a team, update this class so that it also supports the Spanish language code "es" and
//              one more language code of your team's choice. Submit a PR once the code is working and
//              make sure everyone has a local working copy of the code.

/**
 * An implementation of the Translator interface that translates
 * the country code "can" to several languages.
 */
public class CanadaTranslator implements Translator {

    public static final String CANADA = "can";
    /**
     * Return the language code for all languages whose translations are
     * available for translating "can".
     *
     * @return list of language codes that are available for translating
     */
    @Override
    public List<String> getLanguageCodes() {
        return new ArrayList<>(List.of("de", "en", "zh"));
    }

    /**
     * Return the country codes for all countries whose translations are
     * available from this translator.
     *
     * @return list of country codes for which we have translations available
     */
    @Override
    public List<String> getCountryCodes() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Return the name of the country based on the specified country code and language code.
     *
     * @param countryCode  the 3-letter country code
     * @param languageCode the 2-letter language code
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String countryCode, String languageCode) {
        if (!countryCode.equals(CANADA)) {
            return null;
        }
        switch (languageCode) {
            case "de":
                return "Kanada";
            case "en":
                return "Canada";
            case "zh":
                return "加拿大";
            case "es":
                return "Canadá";
            case "fr":
                return "Canada";
            default:
                return null;
        }
    }
}
