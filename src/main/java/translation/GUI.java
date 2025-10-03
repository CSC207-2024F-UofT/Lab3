package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            List<String> countryCodes = translator.getCountryCodes();
            List<String> countries = new ArrayList<>();
            for (String countryCode : countryCodes) {
                countries.add(countryCodeConverter.fromCountryCode(countryCode));
            }
            JList<String> list = new JList<>(countries.toArray(new String[0]));
            countryPanel.add(new JLabel());
            JScrollPane scrollList = new JScrollPane(list);
            countryPanel.add(scrollList);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            List<String> languageCodes = translator.getLanguageCodes();
            List<String> languages = new ArrayList<>();
            LanguageCodeConverter converter = new LanguageCodeConverter();
            for (String code : languageCodes) {
                languages.add(converter.fromLanguageCode(code));
            }
            JComboBox<String> languageComboBox = new JComboBox<>(languages.toArray(new String[0]));
            languagePanel.add(languageComboBox);


            JLabel resultLabel = new JLabel("Translation:");
            JPanel resultPanel = new JPanel();
            resultPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            list.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateTranslation(list, languageComboBox, resultLabel, translator, countryCodeConverter, converter);
                }
            });

            languageComboBox.addActionListener(e -> {
                updateTranslation(list, languageComboBox, resultLabel, translator, countryCodeConverter, converter);
            });



        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(languagePanel);
        mainPanel.add(resultPanel);
        mainPanel.add(countryPanel);

        JFrame frame = new JFrame("Country Name Translator");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    });
}

        static void updateTranslation(JList<String> list, JComboBox<String> languageComboBox,
                                          JLabel resultLabel, Translator translator,
                                          CountryCodeConverter countryCodeConverter,
                                          LanguageCodeConverter converter) {
            String language = (String) languageComboBox.getSelectedItem();
            String country = list.getSelectedValue();

            // for now, just using our simple translator, but
            // we'll need to use the real JSON version later.
            String countryCode = countryCodeConverter.fromCountry(country);
            String languageCode = converter.fromLanguage(language);
            String result = translator.translate(countryCode, languageCode);

            if (result == null) {
                result = "no translation found!";
            }
            resultLabel.setText("Translation: " + result);
        }
}

