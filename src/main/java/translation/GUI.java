package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create converters and translator
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            Translator translator = new JSONTranslator();

            // Get country and language data
            List<String> countryCodes = translator.getCountryCodes();
            List<String> languageCodes = translator.getLanguageCodes();

            // Create main frame and panel
            JFrame frame = new JFrame("Country Name Translator");
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Create country list panel
            JPanel countryPanel = new JPanel(new BorderLayout());
            countryPanel.setBorder(BorderFactory.createTitledBorder("Countries"));

            // Convert country codes to display names
            String[] countryNames = countryCodes.stream()
                    .map(countryCodeConverter::fromCountryCode)
                    .toArray(String[]::new);

            // Create and populate country list
            JList<String> countryList = new JList<>(countryNames);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(10);
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryPanel.add(countryScrollPane, BorderLayout.CENTER);

            // Create language selection panel
            JPanel languagePanel = new JPanel(new BorderLayout(5, 5));
            languagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            // Create language dropdown with display names
            JComboBox<String> languageComboBox = new JComboBox<>();
            DefaultComboBoxModel<String> languageModel = new DefaultComboBoxModel<>();
            for (String langCode : languageCodes) {
                languageModel.addElement(languageCodeConverter.fromLanguageCode(langCode));
            }
            languageComboBox.setModel(languageModel);

            JPanel languageInnerPanel = new JPanel(new BorderLayout());
            languageInnerPanel.setBorder(BorderFactory.createTitledBorder("Select a language"));
            languageInnerPanel.add(languageComboBox, BorderLayout.CENTER);
            languagePanel.add(languageInnerPanel, BorderLayout.NORTH);

            // Create result panel
            JPanel resultPanel = new JPanel(new BorderLayout());
            resultPanel.setBorder(BorderFactory.createTitledBorder("Translation"));
            JLabel resultLabel = new JLabel(" ", SwingConstants.CENTER);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
            resultPanel.add(resultLabel, BorderLayout.CENTER);

            // Add action listeners
            ListSelectionListener selectionListener = e -> {
                if (!e.getValueIsAdjusting() && !countryList.isSelectionEmpty()) {
                    updateTranslation(countryList, languageComboBox, resultLabel, translator, countryCodes, languageCodeConverter);
                }
            };

            countryList.addListSelectionListener(selectionListener);
            languageComboBox.addActionListener(e -> {
                if (!countryList.isSelectionEmpty()) {
                    updateTranslation(countryList, languageComboBox, resultLabel, translator, countryCodes, languageCodeConverter);
                }
            });

            // Add components to main panel
            mainPanel.add(countryPanel, BorderLayout.CENTER);
            mainPanel.add(languagePanel, BorderLayout.NORTH);
            mainPanel.add(resultPanel, BorderLayout.SOUTH);

            // Configure and show the frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Select first country by default
            if (!countryList.isSelectionEmpty()) {
                countryList.setSelectedIndex(0);
            }
        });
    }

    private static void updateTranslation(JList<String> countryList, JComboBox<String> languageComboBox,
                                          JLabel resultLabel, Translator translator, List<String> countryCodes,
                                          LanguageCodeConverter languageCodeConverter) {
        int selectedIndex = countryList.getSelectedIndex();
        if (selectedIndex == -1) return;

        String selectedCountryCode = countryCodes.get(selectedIndex);
        String selectedLanguage = languageCodeConverter.fromLanguage(
                (String) languageComboBox.getSelectedItem()
        );

        if (selectedCountryCode != null && selectedLanguage != null) {
            String translation = translator.translate(selectedCountryCode, selectedLanguage);
            resultLabel.setText(translation != null ? translation : "No translation available");
        }
    }
}
