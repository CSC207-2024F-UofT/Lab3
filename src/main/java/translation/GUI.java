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

            // Use the full JSON translator instead of CanadaTranslator
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            // Create main panel with vertical layout
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Language selection panel with JComboBox
            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Select Language:"));

            JComboBox<String> languageComboBox = new JComboBox<>();
            List<String> langCodes = translator.getLanguageCodes();
            for (String langCode : langCodes) {
                String languageName = languageConverter.fromLanguageCode(langCode);
                if (languageName != null) {
                    languageComboBox.addItem(languageName);
                }
            }
            languagePanel.add(languageComboBox);

            // Country selection panel with JList in scroll pane
            JPanel countryPanel = new JPanel(new BorderLayout());
            countryPanel.add(new JLabel("Select Country:"), BorderLayout.NORTH);

            DefaultListModel<String> listModel = new DefaultListModel<>();
            List<String> countryCodes = translator.getCountryCodes();
            for (String countryCode : countryCodes) {
                String countryName = countryConverter.fromCountryCode(countryCode);
                if (countryName != null) {
                    listModel.addElement(countryName);
                }
            }

            JList<String> countryList = new JList<>(listModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(8);
            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane, BorderLayout.CENTER);

            // Translation result panel
            JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel resultLabelText = new JLabel("Translation:");
            JLabel resultLabel = new JLabel("Select a country and language");
            resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 14f));
            resultPanel.add(resultLabelText);
            resultPanel.add(resultLabel);

            // Update translation when selections change
            Runnable updateTranslation = () -> {
                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                String selectedCountry = countryList.getSelectedValue();

                if (selectedLanguage != null && selectedCountry != null) {
                    String languageCode = languageConverter.fromLanguage(selectedLanguage);
                    String countryCode = countryConverter.fromCountry(selectedCountry);

                    if (languageCode != null && countryCode != null) {
                        String translation = translator.translate(countryCode, languageCode);
                        if (translation != null) {
                            resultLabel.setText(translation);
                        } else {
                            resultLabel.setText("Translation not available");
                        }
                    }
                } else {
                    resultLabel.setText("Select a country and language");
                }
            };

            // Add listeners for selection changes
            languageComboBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateTranslation.run();
                }
            });

            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        updateTranslation.run();
                    }
                }
            });

            // Add all panels to main panel
            mainPanel.add(languagePanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(countryPanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(resultPanel);

            // Create and setup the main window
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center on screen
            frame.setResizable(true);
            frame.setVisible(true);
        });
    }
}