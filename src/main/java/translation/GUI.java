package translation;

import javax.swing.*;
import java.awt.event.*;


public class GUI {
    static CountryCodeConverter countryCodeConverter1 = new CountryCodeConverter();
    static LanguageCodeConverter languageCodeConverter1 = new LanguageCodeConverter();
    private static final String[] languages1 = languageCodeConverter1.getLanguages();
    private static final String[] countries1 = countryCodeConverter1.getCountries();

    static JSONTranslator translator1 = new JSONTranslator();
    private static final Map<String, String> translations1 = translator1.getTranslation();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            String[] countryOptions;
            countryOptions = GUI.countries1;
            JComboBox<String> countryBox = new JComboBox<>(countryOptions);
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryBox);

            JPanel languagePanel = new JPanel();
            String[] languageOptions;
            languageOptions = GUI.languages1;
            JComboBox<String> languageBox = new JComboBox<>(languageOptions);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageBox);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel();
            resultPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            ActionListener updateListener = e -> {
                String language = (String) languageBox.getSelectedItem();
                String languageCode = languageCodeConverter1.fromLanguage(language);
                String country = (String) countryBox.getSelectedItem();
                String countryCode = countryCodeConverter1.fromCountry(country);

                // for now, just using our simple translator, but
                // we'll need to use the real JSON version later.
                String result = GUI.translator1.translate(countryCode, languageCode);
                if (result == null) {
                    result = "no translation found!";
                }
                resultLabel.setText(result);

            };
            countryBox.addActionListener(updateListener);
            languageBox.addActionListener(updateListener);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(resultPanel);
            mainPanel.add(languagePanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}