package translation;

import java.util.ArrayList;
import java.util.List;

public class CountryAndLanguagesLists {

    private CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
    private LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
    private JSONTranslator jsonTranslator = new JSONTranslator();

    public List<String> getCountries() {
        List<String> codes = jsonTranslator.getCountryCodes();
        List<String> countryList = new ArrayList<>();
        for (String code : codes) {
            String country = countryCodeConverter.fromCountryCode(code);
            countryList.add(country);
        }
        return countryList;
    }

    public List<String> getLanguages() {
        List<String> languages = jsonTranslator.getLanguageCodes();
        List<String> languagesList = new ArrayList<>();

        for (String code : languages) {
            String language = languageCodeConverter.fromLanguageCode(code);
            languagesList.add(language);
        }
        return languagesList;
    }
}

