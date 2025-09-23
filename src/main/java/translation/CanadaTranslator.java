package translation;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Translator interface that translates
 * the country code "can" to several languages.
 */
public class CanadaTranslator implements Translator {

    public static final String CANADA = "can";
    public static final String SPAIN = "esp";
    public static final String IRAN = "irn";
    /**
     * Return the language code for all languages whose translations are
     * available for translating "can".
     *
     * @return list of language codes that are available for translating
     */
    @Override
    public List<String> getLanguageCodes() {

        return new ArrayList<>(List.of("de", "en", "zh", "es", "ar"));
        // List.of() creates an immutable (unmodifiable) list
        // fixed size of 3
        // ["de", "en", "zh"]
        // ArrayList<>
        // wraps the immutable list into a new mutable ArrayList (resizable array)

        // Q: why don't we just create an array list?
        // Option A: new ArrayList<>(Arrays.asList(...))
            // returns a fixed-size list backed by the array, so you can’t add/remove elements (only replace)
        // Option B:
        // List<String> codes = new ArrayList<>();
        //codes.add("de");
        //codes.add("en");
        //codes.add("zh");
        //return codes;
    }

    /**
     * Return the country codes for all countries whose translations are
     * available from this translator.
     *
     * @return list of country codes for which we have translations available
     */
    @Override
    public List<String> getCountryCodes() {

        return new ArrayList<>(List.of(CANADA, SPAIN, IRAN));
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
        if (languageCode.equals("de")) {
            return "Kanada";
        }
        else if (languageCode.equals("en")) {
            return "Canada";
        }
        else if (languageCode.equals("zh")) {
            return "加拿大";
        }
        else if (languageCode.equals("es")) {
            return "Canadá";
        }
        else if (languageCode.equals("ar")) {
            return "كندا";
        }
        else {
            return null;
        }
    }
}
