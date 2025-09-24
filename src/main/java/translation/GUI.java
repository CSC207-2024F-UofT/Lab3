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
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

//            JPanel languagePanel = new JPanel();
//            JTextField languageField = new JTextField(10);
//            languagePanel.add(new JLabel("Language:"));
//            languagePanel.add(languageField);

            JPanel languagePanel = new JPanel();
            final JLabel languageLabel = new JLabel("Language:");
            LanguageCodeConverter languageObject = new LanguageCodeConverter();
            String[] languages = languageObject.languages();
            final JComboBox<String> lang = new JComboBox<>(languages);

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : languages) {
                languageComboBox.addItem(countryCode);
            };

            /*
            JSONTranslator jsonTranslator = new JSONTranslator();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : jsonTranslator.getLanguageCodes()) {
                String newLanguage = languageCodeConverter.fromLanguageCode(countryCode);
                languageComboBox.addItem(newLanguage);
            }
            */

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            // adding listener for when the user clicks the submit button
            languageComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryField.getText();


                    Translator translator = new JSONTranslator();
                    LanguageCodeConverter languageObject = new LanguageCodeConverter();
                    String result = translator.translate(country, languageObject.fromLanguage(language));
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
