package org.translation;

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

    private static final String QUIT_KEYWORD = "quit";

    private static final CountryCodeConverter COUNTRY_CODE_CONVERTER = new CountryCodeConverter();
    private static final LanguageCodeConverter LANGUAGE_CODE_CONVERTER = new LanguageCodeConverter();

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // Translator translator = new InLabByHandTranslator();
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
            var country = promptForCountry(translator);
            String countryCode = COUNTRY_CODE_CONVERTER.fromCountry(country);
            if (QUIT_KEYWORD.equals(country)) {
                break;
            }
            var language = promptForLanguage(translator, countryCode);
            String languageCode = LANGUAGE_CODE_CONVERTER.fromLanguage(language);
            if (language.equals(QUIT_KEYWORD)) {
                break;
            }
            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT_KEYWORD.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        translator.getCountries().stream().map(COUNTRY_CODE_CONVERTER::fromCountryCode).sorted()
                .forEach(System.out::println);
        System.out.println("select a country from above:");

        return new Scanner(System.in).nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        translator.getCountryLanguages(country).stream().map(LANGUAGE_CODE_CONVERTER::fromLanguageCode).sorted()
                .forEach(System.out::println);
        System.out.println("select a language from above:");

        return new Scanner(System.in).nextLine();
    }
}
