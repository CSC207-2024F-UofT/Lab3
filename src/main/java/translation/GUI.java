package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));

            Translator translator = new JSONTranslator();
            LanguageCodeConverter converter = new LanguageCodeConverter();


            JComboBox<String> languageComboBox = new JComboBox<>();
            Map<String, String> langNameToCode = new HashMap<>();
            for (String languageCode : translator.getLanguageCodes()) {
                String languageName = converter.fromLanguageCode(languageCode);
                if (languageName == null || languageName.isEmpty()) {
                    languageName = languageCode; // fallback
                }
                languageComboBox.addItem(languageName);
                langNameToCode.put(languageName, languageCode);
            }
            languagePanel.add(languageComboBox);


            JPanel countryPanel = new JPanel();

            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            String[] countryNames = new String[translator.getCountryCodes().size()];
            Map<String, String> countryNameToCode = new HashMap<>();
            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                String name = countryCodeConverter.fromCountryCode(countryCode);
                if (name == null || name.isEmpty()) {
                    name = countryCode;
                }
                countryNames[i++] = name;
                countryNameToCode.put(name, countryCode);
            }
            JList<String> countryList = new JList<>(countryNames);

            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedLanguageName = (String) languageComboBox.getSelectedItem();
                    String selectedCountryName = countryList.getSelectedValue();

                    if (selectedLanguageName == null || selectedCountryName == null) {
                        resultLabel.setText("please select both country and language");
                        return;
                    }

                    String languageCode = langNameToCode.getOrDefault(selectedLanguageName, selectedLanguageName);
                    String countryCode = countryNameToCode.getOrDefault(selectedCountryName, selectedCountryName);

                    Translator t = new JSONTranslator();
                    String result = t.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
