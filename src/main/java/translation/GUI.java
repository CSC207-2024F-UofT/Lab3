package translation;

import javax.swing.*;
import java.awt.*;
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
            countryPanel.add(new JLabel("Country:"));
            Translator translator = new JSONTranslator();
            List<String> countryCodes = translator.getCountryCodes();
            CountryCodeConverter converterCountry = new CountryCodeConverter();
            List<String> countryNames = new ArrayList<>();
            for (String code : countryCodes) {
              countryNames.add(converterCountry.fromCountryCode(code));
            }
            JList<String> countrySelector = new JList<>(countryNames.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(countrySelector);
            countryPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            List<String> languageCodes = translator.getLanguageCodes();
            LanguageCodeConverter converter = new LanguageCodeConverter();
            List<String> languageNames = new ArrayList<>();
            for (String code : languageCodes) {
              languageNames.add(converter.fromLanguageCode(code));
            }
            JComboBox<String> languageComboBox = new JComboBox<>(languageNames.toArray(new String[0]));
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageField.getText();
                    String country = countrySelector.getSelectedValue();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new CanadaTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
