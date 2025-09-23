package translation;

import javax.swing.*;
import java.awt.*;
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
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            Translator translator = new JSONTranslator();

            List<String> countryNames = new ArrayList<>();
            for (String code : translator.getCountryCodes()) {
                String name = countryConverter.fromCountryCode(code.toLowerCase());
                if (name != null) countryNames.add(name);
            }
            Collections.sort(countryNames);

            List<String> languageNames = new ArrayList<>();
            for (String code : translator.getLanguageCodes()) {
                String name = languageConverter.fromLanguageCode(code);
                if (name != null) languageNames.add(name);
            }
            Collections.sort(languageNames);

            JPanel countryPanel = new JPanel(new BorderLayout());
            countryPanel.add(new JLabel("Country:"), BorderLayout.NORTH);

            JList<String> countryList = new JList<>(countryNames.toArray(new String[0]));
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setSelectedIndex(0);
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryScrollPane.setPreferredSize(new Dimension(300, 200));
            countryPanel.add(countryScrollPane, BorderLayout.CENTER);

            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>(languageNames.toArray(new String[0]));
            languageComboBox.setSelectedIndex(0);
            languagePanel.add(languageComboBox);

            JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            resultPanel.add(new JLabel("Translation:"));
            JLabel resultLabel = new JLabel("");
            resultLabel.setFont(new Font(resultLabel.getFont().getName(), Font.BOLD, 14));
            resultPanel.add(resultLabel);

            Runnable updateTranslation = () -> {
                String selectedCountry = countryList.getSelectedValue();
                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                if (selectedCountry != null && selectedLanguage != null) {
                    String countryCode = countryConverter.fromCountry(selectedCountry);
                    String languageCode = languageConverter.fromLanguage(selectedLanguage);
                    if (countryCode != null && languageCode != null) {
                        String result = translator.translate(countryCode.toLowerCase(), languageCode);
                        resultLabel.setText(result != null && !result.isEmpty() ? result : "Translation not available");
                    } else {
                        resultLabel.setText("Error: Could not find codes");
                    }
                }
            };

            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) updateTranslation.run();
            });
            languageComboBox.addActionListener(e -> updateTranslation.run());
            updateTranslation.run();

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(languagePanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(resultPanel);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}