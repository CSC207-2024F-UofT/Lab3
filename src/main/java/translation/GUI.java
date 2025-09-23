package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new CanadaTranslator();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();


            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String langCode: translator.getLanguageCodes()) {
                languageComboBox.addItem(langCode);
            }
            languagePanel.add(languageComboBox);


            JPanel countryCodePanel = new JPanel(new BorderLayout());
            countryCodePanel.add(new JLabel("Country:"), 0);
            DefaultListModel<String> countryListModel = new DefaultListModel<>();
            List<String> countryCodes = translator.getCountryCodes();
            List<String> countryNames = new ArrayList<>();
            for(String countryCode : countryCodes){
                String countryName = countryCodeConverter.fromCountryCode(countryCode);
                if(countryName != null){
                    countryNames.add(countryName);
                }
            }

            for(String countryCode : countryNames){
                countryListModel.addElement(countryCode);
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
                    String selectedLanguageCode = (String) languageComboBox.getSelectedItem();
                    String selectedCountryName = countryList.getSelectedValue();
                    if(selectedLanguageCode == null || selectedCountryName == null) {
                        resultLabel.setText("Please select a country and a language");
                        return;
                    }
                    String countryCode = countryCodeConverter.fromCountry(selectedCountryName);
                    if (countryCode == null) {
                        resultLabel.setText("Country code not found for: " + selectedCountryName);
                        return;
                    }
                    String result = translator.translate(countryCode, selectedLanguageCode);
                    resultLabel.setText(result == null ? "No translation found for " + selectedCountryName: result);
                }
            });

            countryList.addListSelectionListener(e ->{
                if(!e.getValueIsAdjusting()){
                    String selectedLanguageCode = (String) languageComboBox.getSelectedItem();
                    String selectedCountryName = countryList.getSelectedValue();
                    if(selectedLanguageCode == null || selectedCountryName == null) {
                        resultLabel.setText("Please select a country and a language");
                        return;
                    }
                    String countryCode = countryCodeConverter.fromCountry(selectedCountryName);
                    if (countryCode == null) {
                        resultLabel.setText("Country code not found for: " + selectedCountryName);
                        return;
                    }
                    String result = translator.translate(countryCode, selectedLanguageCode);
                    resultLabel.setText(result == null ? "No translation found for " + selectedCountryName: result);
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
