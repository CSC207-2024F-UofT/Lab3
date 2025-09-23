package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the converters and translator
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            Translator translator = new JSONTranslator();

            // Get lists of countries and languages
            List<String> countryCodes = translator.getCountryCodes();
            List<String> languageCodes = translator.getLanguageCodes();

            // Convert codes to names for display
            List<String> countryNames = new ArrayList<>();
            for (String code : countryCodes) {
                String name = countryConverter.fromCountryCode(code.toLowerCase());
                if (name != null) {
                    countryNames.add(name);
                }
            }
            Collections.sort(countryNames);

            List<String> languageNames = new ArrayList<>();
            for (String code : languageCodes) {
                String name = languageConverter.fromLanguageCode(code);
                if (name != null) {
                    languageNames.add(name);
                }
            }
            Collections.sort(languageNames);

            // Country panel with JList
            JPanel countryPanel = new JPanel(new BorderLayout());
            countryPanel.add(new JLabel("Country:"), BorderLayout.NORTH);

            JList<String> countryList = new JList<>(countryNames.toArray(new String[0]));
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setSelectedIndex(0); // Select first country by default
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryScrollPane.setPreferredSize(new Dimension(300, 200));
            countryPanel.add(countryScrollPane, BorderLayout.CENTER);

            // Language panel with JComboBox
            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Language:"));

            JComboBox<String> languageComboBox = new JComboBox<>(languageNames.toArray(new String[0]));
            languageComboBox.setSelectedIndex(0); // Select first language by default
            languagePanel.add(languageComboBox);

            // Translation result panel
            JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("");
            resultLabel.setFont(new Font(resultLabel.getFont().getName(), Font.BOLD, 14));
            resultPanel.add(resultLabel);

            // Method to update translation
            Runnable updateTranslation = () -> {
                String selectedCountry = countryList.getSelectedValue();
                String selectedLanguage = (String) languageComboBox.getSelectedItem();

                if (selectedCountry != null && selectedLanguage != null) {
                    // Convert names back to codes
                    String countryCode = countryConverter.fromCountry(selectedCountry);
                    String languageCode = languageConverter.fromLanguage(selectedLanguage);

                    if (countryCode != null && languageCode != null) {
                        // Get translation using lowercase codes
                        String result = translator.translate(countryCode.toLowerCase(), languageCode);
                        if (result != null && !result.isEmpty()) {
                            resultLabel.setText(result);
                        } else {
                            resultLabel.setText("Translation not available");
                        }
                    } else {
                        resultLabel.setText("Error: Could not find codes");
                    }
                }
            };

            // Add listeners to update translation when selection changes
            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateTranslation.run();
                }
            });

            languageComboBox.addActionListener(e -> {
                updateTranslation.run();
            });

            // Initial translation
            updateTranslation.run();

            // Main panel setup
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(languagePanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(resultPanel);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Frame setup
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }
}