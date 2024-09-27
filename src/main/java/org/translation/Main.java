package org.translation;

import java.sql.SQLOutput;
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
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        // Translator translator = new JSONTranslator();
        Translator translator = new InLabByHandTranslator();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter("country-codes.txt");
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        runProgram(translator, countryCodeConverter, languageCodeConverter);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     * @param countryCodeConverter converting between country and country code
     * @param languageCodeConverter converting between language and language code
     */
    public static void runProgram(Translator translator,
                                  CountryCodeConverter countryCodeConverter,
                                  LanguageCodeConverter languageCodeConverter) {
        while (true) {
            String country = promptForCountry(translator);
            String quit = "quit";

            if (quit.equals(country)) {
                break;
            }
            String countryCode = countryCodeConverter.fromCountry(country);
            String language = promptForLanguage(translator, countryCode, languageCodeConverter);
            if (quit.equals(language)) {
                break;
            }
            String languageCode = languageCodeConverter.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        List<String> countryNames = new ArrayList<>();

        for (String countryCode : countries) {
            String countryName = translator.translate(countryCode, "en");
            if (countryName != null) {
                countryNames.add(countryName);
            }
        }
        Collections.sort(countryNames);

        for (String name : countryNames) {
            System.out.println(name);
        }
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator,
                                            String country,
                                            LanguageCodeConverter languageCodeConverter) {

        List<String> languageCodes = translator.getCountryLanguages(country);
        List<String> languageNames = new ArrayList<>();

        for (String languageCode : languageCodes) {
            String languageName = languageCodeConverter.fromLanguageCode(languageCode);
            if (languageName != null) {
                languageNames.add(languageName);
            }
        }
        Collections.sort(languageNames);

        for (String name : languageNames) {
            System.out.println(name);
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}

