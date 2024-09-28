package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    public static final String QUIT = "quit";
    private static CountryCodeConverter countryCodeConverter;
    private static LanguageCodeConverter languageCodeConverter;
    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        countryCodeConverter = new CountryCodeConverter();
        languageCodeConverter = new LanguageCodeConverter();
        Translator translator = new JSONTranslator(null);
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        if (countryCodeConverter == null) {
            countryCodeConverter = new CountryCodeConverter();
        }
        if (languageCodeConverter == null) {
            languageCodeConverter = new LanguageCodeConverter();
        }
        while (true) {
            String country = promptForCountry(translator);
            if (QUIT.equals(country)) {
                break;
            }
            String languageCode = promptForLanguage(translator, country);
            if (QUIT.equals(languageCode)) {
                break;
            }

            String countryCode = countryCodeConverter.fromCountry(country);
            if (languageCode == null || countryCode == null) {
                System.out.println("Invalid input");
            }
            else {
                String languageName = languageCodeConverter.fromLanguageCode(languageCode);
                System.out.println(country + " in " + languageName + " is " + translator.translate(country,
                        languageCode));

            }

            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        // TODO Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // TODO Task: convert the country codes to the actual country names before sorting
        // System.out.println(countries);
        if (countryCodeConverter == null) {
            countryCodeConverter = new CountryCodeConverter();
        }
        List<String> countryCode = translator.getCountries();

        for (int i = 0; i < countryCode.size(); i++) {
            String code = countryCode.get(i);
            String countryName = countryCodeConverter.fromCountryCode(code);
            countryCode.set(i, countryName);
        }
        countryCode.sort(String::compareTo);

        for (String country : countryCode) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        Map<String, String> languageConverter = new HashMap<>();

        for (String language : languages) {
            String languageName = languageCodeConverter.fromLanguageCode(language);
            languageConverter.put(languageName, language);
        }

        List<String> languageNames = new ArrayList<>(languageConverter.keySet());
        Collections.sort(languageNames);

        for (String language : languageNames) {
            System.out.println(language);
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        String selectedlanguage = s.nextLine();
        return languageConverter.get(selectedlanguage);
    }
}
