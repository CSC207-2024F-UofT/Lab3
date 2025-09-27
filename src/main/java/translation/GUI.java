package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();


            System.out.println("Available countries: " + translator.getCountryCodes());
            System.out.println("Available languages: " + translator.getLanguageCodes());

            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String langCode: translator.getLanguageCodes()) {
                String languageName = languageConverter.fromLanguageCode(langCode);
                if (languageName != null) {
                    languageComboBox.addItem(languageName);
                } else {
                    languageComboBox.addItem(langCode);
                }
            }
            languagePanel.add(languageComboBox);

            JPanel countryCodePanel = new JPanel(new BorderLayout());
            countryCodePanel.add(new JLabel("Country:"), BorderLayout.NORTH);
            DefaultListModel<String> countryListModel = new DefaultListModel<>();
            List<String> countryCodes = translator.getCountryCodes();
            List<String> countryNames = new ArrayList<>();
            for(String countryCode : countryCodes){
                String countryName = countryCodeConverter.fromCountryCode(countryCode);
                if(countryName != null){
                    countryNames.add(countryName);
                }
            }

            for(String countryName : countryNames){
                countryListModel.addElement(countryName);
            }
            JList<String> countryList = new JList<>(countryListModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane countryListScrollPane = new JScrollPane(countryList);
            countryListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
            countryCodePanel.add(countryListScrollPane, BorderLayout.CENTER);

            JLabel resultLabel = new JLabel("Translation Result");
            JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            resultPanel.add(new JLabel("Translation: "));
            resultPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            languageComboBox.addItemListener(e -> {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String selectedLanguageName = (String) languageComboBox.getSelectedItem();
                    String selectedCountryName = countryList.getSelectedValue();
                    if(selectedLanguageName == null || selectedCountryName == null) return;
                    String langCode = languageConverter.fromLanguage(selectedLanguageName);
                    if (langCode == null) langCode = selectedLanguageName;
                    String countryCode = countryCodeConverter.fromCountry(selectedCountryName);
                    if (countryCode == null) return;
                    String result = translator.translate(countryCode, langCode);
                    resultLabel.setText(result == null ? "No translation found." : result);
                }
            });

            countryList.addListSelectionListener(e ->{
                if(!e.getValueIsAdjusting()){
                    String selectedLanguageName = (String) languageComboBox.getSelectedItem();
                    String selectedCountryName = countryList.getSelectedValue();
                    if(selectedLanguageName == null || selectedCountryName == null) return;
                    String langCode = languageConverter.fromLanguage(selectedLanguageName);
                    if (langCode == null) langCode = selectedLanguageName;
                    String countryCode = countryCodeConverter.fromCountry(selectedCountryName);
                    if (countryCode == null) return;
                    String result = translator.translate(countryCode, langCode);
                    resultLabel.setText(result == null ? "No translation found." : result);
                }
            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel, BorderLayout.NORTH);
            mainPanel.add(countryCodePanel, BorderLayout.CENTER);
            mainPanel.add(resultPanel, BorderLayout.SOUTH);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            languageComboBox.setSelectedIndex(0);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
