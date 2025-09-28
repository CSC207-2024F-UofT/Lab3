package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {

        // Read JSON file and obtain lists of countries and languages
        JSONTranslator JSONreader = new JSONTranslator("sample.json");
        CountryCodeConverter CounCodeTranslator = new CountryCodeConverter();
        LanguageCodeConverter LanCodeTranslator = new LanguageCodeConverter();
        List<String> countries = JSONreader.getCountryCodes();
        List<String> languages = JSONreader.getLanguageCodes();

        // create combobox, add country codes into it, and add it to our panel
        JComboBox<String> countriesComboBox = new JComboBox<>();
        for(String countryCode : countries) {
            countriesComboBox.addItem(CounCodeTranslator.fromCountryCode(countryCode));
        }

        // create combobox, add language codes into it, and add it to our panel
        JComboBox<String> languagesComboBox = new JComboBox<>();
        for(String languageCode : languages) {
            languagesComboBox.addItem(LanCodeTranslator.fromLanguageCode(languageCode));
        }

        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
        //    JTextField countryField = new JTextField(10);
        //    countryField.setText("can");
        //    countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countriesComboBox);

            JPanel languagePanel = new JPanel();
        //    JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languagesComboBox);

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
                    String language = LanCodeTranslator.fromLanguage((String) languagesComboBox.getSelectedItem());
                    String country = CounCodeTranslator.fromCountry((String) countriesComboBox.getSelectedItem());

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = JSONreader;

                    String result = translator.translate(country, language);
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
}
