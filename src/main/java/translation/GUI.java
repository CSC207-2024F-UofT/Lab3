package translation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Core Objects
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConv = new CountryCodeConverter();
            LanguageCodeConverter languageConv = new LanguageCodeConverter();

            // 2. Create Language JComboBox
            List<String> languageDisplayNames = new ArrayList<>();
            for (String langCode : translator.getLanguageCodes()) {
                // Call the existing fromLanguageCode method from your file
                languageDisplayNames.add(languageConv.fromLanguageCode(langCode));
            }
            JComboBox<String> languageComboBox = new JComboBox<>(languageDisplayNames.toArray(new String[0]));
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            // 3. Create Country JList
            List<String> countryDisplayNames = new ArrayList<>();
            for (String countryCode : translator.getCountryCodes()) {
                // Call the existing fromCountryCode method from your file
                countryDisplayNames.add(countryConv.fromCountryCode(countryCode));
            }
            JList<String> countryList = new JList<>(countryDisplayNames.toArray(new String[0]));
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryScrollPane.setPreferredSize(new Dimension(250, 150));

            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            countryPanel.add(countryScrollPane);

            // 4. Create Result Label
            JLabel resultLabel = new JLabel("Translation will appear here.");
            resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 5. Add Event Listeners
            languageComboBox.addActionListener(e -> updateTranslation(languageComboBox, countryList, translator, languageConv, countryConv, resultLabel));
            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateTranslation(languageComboBox, countryList, translator, languageConv, countryConv, resultLabel);
                }
            });

            // 6. Assemble Main Panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mainPanel.add(languagePanel);
            mainPanel.add(resultLabel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(countryPanel);

            // 7. Create and Display Frame
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Helper method to update the translation result.
     * This method is called whenever the language or country selection changes.
     */
    private static void updateTranslation(JComboBox<String> langComboBox, JList<String> countryList,
                                          Translator translator, LanguageCodeConverter langConv,
                                          CountryCodeConverter countryConv, JLabel resultLabel) {

        String langName = (String) langComboBox.getSelectedItem();
        String countryName = countryList.getSelectedValue();

        if (langName == null || countryName == null) {
            return;
        }

        // Call the existing fromLanguage and fromCountry methods from your files
        String langCode = langConv.fromLanguage(langName);
        String countryCode = countryConv.fromCountry(countryName);

        // If the retrieved code is null (conversion might have failed), exit early.
        if (langCode == null || countryCode == null) {
            resultLabel.setText("Translation: error in code conversion!");
            return;
        }

        // --- Key Fix: Standardize case here ---
        // Convert the country code to lower case to match the format in sample.json
        String result = translator.translate(countryCode.toLowerCase(), langCode);

        if (result == null) {
            result = "no translation found!";
        }
        resultLabel.setText("Translation: " + result);
    }
}