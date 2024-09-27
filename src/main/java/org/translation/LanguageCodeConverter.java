package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private static int numLanguages = 0;
    private static ArrayList<String> langname = new ArrayList<>();
    private static ArrayList<String> langcode = new ArrayList<>();


    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

//            Iterator<String> line = lines.iterator();
//            numLanguages --;
//            while(line.hasNext()){
//
//                List<String> lists = List.of(line.toString().split("\t"));
//                langname.add(lists.get(0));
//                langcode.add(lists.get(1));
//                line.next();
//            }
            lines.remove(0);
            numLanguages = lines.size();

            for (String line:lines) {
                List<String> lists = List.of(line.split("\t"));
                langname.add(lists.get(0));
                langcode.add(lists.get(1));

            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        return langname.get(langcode.indexOf(code));
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        return langcode.get(langname.indexOf(language));
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return numLanguages;
    }
}
