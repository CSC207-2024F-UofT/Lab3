package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {
    /**
     * This fucntions updates the translation, a function was used since this code was rquired to be applied
     * for both the language dropdown and country list
     * @param languageComboBox: dropdown for languages
     * @param languageCodeConverter: the converter to find the languages codes
     * @param countryList: the list of the countries
     * @param countryConverter: the converter to find the country codes
     * @param resultLabel: the label the provides the translation
     */
    private static void updateTranslation(JComboBox<String> languageComboBox,
                                          LanguageCodeConverter languageCodeConverter,
                                          JList<String> countryList, CountryCodeConverter countryConverter,
                                          JLabel resultLabel, Translator translator) {
        String language;
        String languageCode = "";
        if (languageComboBox.getSelectedItem() != null) {
            language = languageComboBox.getSelectedItem().toString();
            languageCode = languageCodeConverter.fromLanguage(language);
        }

        // Marcus - Get selected country from the list
        String countryCode = "";
        if (countryList.getSelectedValue() !=  null){
            String selectedCountry = countryList.getSelectedValue();
            countryCode = countryConverter.fromCountry(selectedCountry);
        }


        String result = translator.translate(countryCode, languageCode);

        if (result == null) {
            result = "no translation found!";
        }
        resultLabel.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            String[] languages = languageCodeConverter.getLanguages(translator.getLanguageCodes());
            Arrays.sort(languages);



            // language panel and dropdown
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>(languages);
            languagePanel.add(languageComboBox);



            JPanel countryPanel = new JPanel();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            String[] countries = countryConverter.getCountries(translator.getCountryCodes());
            Arrays.sort(countries);
            JList<String> countryList = new JList<>(countries);
            countryList.setVisibleRowCount(8);
            JScrollPane countryListScrollPane = new JScrollPane(countryList);
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryListScrollPane);

            JPanel translationPanel = new JPanel();


            JLabel resultLabelText = new JLabel("Translation:");
            translationPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            translationPanel.add(resultLabel);


            /**
             *
             * Action listener - receives and reacts to the changed language in the language dropdown
             * Uses the updateTranslation function to update the translation accordingly
             * Takes the language dropdown, the language code converter, the country list, the country converter,
             * and the result label to update accordingly
             */

            languageComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTranslation(languageComboBox, languageCodeConverter, countryList, countryConverter,
                            resultLabel, translator);
                }
            });

            /**
             *
             * ListSelectionListener - receives and reacts to the changed country in the country list
             * Uses the updateTranslation function to update the translation accordingly
             * Takes the language dropdown, the language code converter, the country list, the country converter,
             * and the result label to update accordingly
             */
            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    updateTranslation(languageComboBox, languageCodeConverter, countryList, countryConverter,
                            resultLabel, translator);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translationPanel);
            mainPanel.add(countryPanel);


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
