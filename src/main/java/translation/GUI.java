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
            countryPanel.add(new JLabel("Country:"));

            DefaultListModel<CountryItem> countryModel = new DefaultListModel<>();
            JList<CountryItem> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(10); // adjust how tall it looks

            JScrollPane countryScroll = new JScrollPane(countryList);
            countryPanel.add(countryScroll);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            // adding drop down menu for the languages
            // from ComboBoxDemo.java:
            // create combobox, add country codes into it, and add it to our panel
            JComboBox<String> languageComboBox = new JComboBox<>();
            //
            Translator translator = new JSONTranslator();

            LanguageCodeConverter langConverter = new LanguageCodeConverter("language-codes.txt");
            System.out.println(langConverter.fromLanguageCode("fr"));

            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(langConverter.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // make a country code convertor too
            CountryCodeConverter countryConverter = new CountryCodeConverter();

            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryList.getText();

                    Translator translator = new JSONTranslator();

                    String languageCode = langConverter.fromLanguage(language);
                    //String countryCode = countryConverter.fromCountry(country);
                    String result = translator.translate(country, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }

    static class CountryItem {
        private final String code;   // alpha-3, lowercase (e.g., "can")
        private final String name;   // display name (e.g., "Canada")
        CountryItem(String code, String name) {
            this.code = code;
            this.name = name;
        }
        String code() { return code; }
        @Override public String toString() { return name; } // shown in the JList
    }

}
