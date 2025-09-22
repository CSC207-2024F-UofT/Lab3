package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            JSONTranslator translator = new JSONTranslator();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            String[] countryNames = new String[translator.getCountryCodes().size()];
            int idx = 0;
            for (String code : translator.getCountryCodes()) {
                countryNames[idx++] = countryConverter.fromCountryCode(code);
            }
            JList<String> countryList = new JList<>(countryNames);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(countryList);
            scrollPane.setPreferredSize(new Dimension(250, 120));
            countryPanel.add(scrollPane);

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String langCode : translator.getLanguageCodes()) {
                String languageName = languageConverter.fromLanguageCode(langCode);
                languageComboBox.addItem(languageName);
            }
            languagePanel.add(languageComboBox);

            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("Select a country and language...");
            resultPanel.add(new JLabel("Translation:"));
            resultPanel.add(resultLabel);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);

            Runnable updateTranslation = () -> {
                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                String selectedCountry = countryList.getSelectedValue();

                if (selectedLanguage == null || selectedCountry == null) return;

                String langCode = languageConverter.fromLanguage(selectedLanguage);
                String countryCode = countryConverter.fromCountry(selectedCountry);

                String translation = translator.translate(countryCode, langCode);
                if (translation == null) {
                    translation = "No translation found!";
                }
                resultLabel.setText(translation);
            };

            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        updateTranslation.run();
                    }
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

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        });
    }
}
