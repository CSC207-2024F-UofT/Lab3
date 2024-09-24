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
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator("sample.json");

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
            String quit = "quit";
            if (country.equals(quit)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            String countryConv = countryConverter.fromCountry(country).toLowerCase();
            String language = promptForLanguage(translator, country);

            LanguageCodeConverter langConverter = new LanguageCodeConverter();
            String languageConv = langConverter.fromLanguage(language);
            if (language.equals(quit)) {
                break;
            }
            // TODO Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            System.out.println(country + " in " + language + " is " + translator.translate(countryConv, languageConv));
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
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        // TODO Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // TODO Task: convert the country codes to the actual country names before sorting
        ArrayList<String> countriesConv = new ArrayList<>();

        for (int i = 0; i < countries.size(); i++) {
            countriesConv.add(countryConverter.fromCountryCode(countries.get(i)));
        }
        Collections.sort(countriesConv);
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countriesConv.get(i));
        }
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> countryLanguages = translator.getCountryLanguages(country);
        LanguageCodeConverter langConverter = new LanguageCodeConverter();
        // TODO Task: replace the line below so that we sort the languages alphabetically and print them out;
        //  one per line
        // TODO Task: convert the language codes to the actual language names before sorting
        ArrayList<String> languagesConv = new ArrayList<>();

        for (int i = 0; i < countryLanguages.size(); i++) {
            languagesConv.add(langConverter.fromLanguageCode(countryLanguages.get(i)));
        }
        System.out.println("BLOCK!");
        Collections.sort(languagesConv);
        for (int i = 0; i < countryLanguages.size(); i++) {
            System.out.println(languagesConv.get(i));
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
