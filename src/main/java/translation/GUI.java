package translation;

import javax.swing.*;
import java.awt.event.*;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountryCodeConverter countryConv = new CountryCodeConverter();
            LanguageCodeConverter languageConv = new LanguageCodeConverter();
            JSONTranslator translator = new JSONTranslator();

            // Country Panel (JScrollPanel)
            DefaultListModel<String> listModel = new DefaultListModel<>();
            JScrollPane countryPanel = new JScrollPane();
            countryPanel.add(new JLabel("Country:"));
            JList<String> countryList = new JList<>(listModel);
            countryPanel.setViewportView(countryList);
            for (String countryCode : translator.getCountryCodes()) {
                String countryName = countryConv.fromCountryCode(countryCode);
                listModel.addElement(countryName);
            }

            // Language Panel (ComboBox)
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String langCode : translator.getLanguageCodes()) {
                String langName = languageConv.fromLanguageCode(langCode);
                languageComboBox.addItem(langName);
            }
            languagePanel.add(languageComboBox);

            // Submit Button
            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            // Translated Result
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // Action Listener
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String langName = (String) languageComboBox.getSelectedItem();
                    String langCode = languageConv.fromLanguage(langName);
                    String countryName = countryList.getSelectedValue();
                    String countryCode = countryConv.fromCountryCode(countryName);

                    // Use JSONTranslator (not CanadaTranslator) if you want real translations
                    String result = translator.translate(countryCode, langCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}