package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator jsonTranslator = new JSONTranslator();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : jsonTranslator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel countryPanel = new JPanel();
            String[] items = jsonTranslator.getCountryCodes().toArray(new String[0]);
            for(int i = 0; i < items.length; i++) {
                items[i] = countryCodeConverter.fromCountryCode(items[i]);
            }
            JList countryField = new JList<>(items);
            JScrollPane countryScrollPane = new JScrollPane(countryField);
            countryPanel.add(countryScrollPane);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            countryField.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryField.getSelectedValue().toString();

                    country = countryCodeConverter.fromCountry(country);
                    language = languageCodeConverter.fromLanguage(language);
                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

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
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
