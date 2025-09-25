package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:")); // label
            String[] items = getCountries(translator);
            JComboBox<String> countryComboBox = new JComboBox<>(items); // dropdown
            countryPanel.add(countryComboBox);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            JComboBox<String> languageComboBox = getLanguages(translator);
            languagePanel.add(languageComboBox);

            // add listener for when an item is selected.
            languageComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String country = languageComboBox.getSelectedItem().toString();
//                        JOptionPane.showMessageDialog(null, "user selected " + country + "!");
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("Translation will appear here.");
            resultPanel.add(resultLabel);
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

// add action listener
            submit.addActionListener(e -> {
                String countryName = (String) countryComboBox.getSelectedItem();
                String languageName = (String) languageComboBox.getSelectedItem();

                CountryCodeConverter countryConverter = new CountryCodeConverter();
                LanguageCodeConverter languageConverter = new LanguageCodeConverter();

                // convert back to codes
                String countryCode = countryConverter.fromCountry(countryName);
                String languageCode = languageConverter.fromLanguage(languageName);

                String result = translator.translate(countryCode, languageCode);
                if (result == null) {
                    result = "No translation found!";
                }

                resultLabel.setText(result); // show result in label
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(resultPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }

    private static JComboBox<String> getLanguages(Translator translator) {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        // create combo box, add country codes into it, and add it to our panel
        JComboBox<String> languageComboBox = new JComboBox<>();
        for(String countryCode : translator.getLanguageCodes()) {
            languageComboBox.addItem(converter.fromLanguageCode(countryCode));
        }
        return languageComboBox;
    }

    private static String[] getCountries(Translator translator) {
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        String[] items = new String[translator.getCountryCodes().size()];

        int i = 0;
        for(String countryCode : translator.getCountryCodes()) {
            items[i++] = countryCodeConverter.fromCountryCode(countryCode);
        }
        return items;
    }
}
