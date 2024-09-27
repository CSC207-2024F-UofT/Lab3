package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    /**
     * Command used to quit the program.
     */
    public static final String QUIT = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        Translator translator = new InLabByHandTranslator();
        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            if (QUIT.equals(country)) {
                break;
            }
            CountryCodeConverter converter = new CountryCodeConverter();
            String language = promptForLanguage(translator, converter.fromCountry(country));
            if (QUIT.equals(language)) {
                break;
            }
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            String translatedCountry = translator.translate(country, languageConverter.fromLanguage(language));
            System.out.println(country + " in " + language + " is " + translatedCountry);
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        CountryCodeConverter converter = new CountryCodeConverter();
        List<String> countryNames = new ArrayList<>();
        for (String code: countries) {
            countryNames.add(converter.fromCountryCode(code));
        }

        Collections.sort(countryNames);
        for (String country: countryNames) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    private static String promptForLanguage(Translator translator, String country) {

        LanguageCodeConverter converter = new LanguageCodeConverter();
        List<String> languages = new ArrayList<>();
        for (String language: translator.getCountryLanguages(country)) {
            languages.add(converter.fromLanguageCode(language));
        }
        Collections.sort(languages);
        for (String language: languages) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
