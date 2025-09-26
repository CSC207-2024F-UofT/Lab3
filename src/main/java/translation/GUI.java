package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        JSONTranslator jsonTranslator = new JSONTranslator();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            List<String> languageCodes = jsonTranslator.getLanguageCodes();
            JComboBox<String> comboBox = new JComboBox<>();
            for (String languageCode : languageCodes)
                comboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            countryPanel.add(new JLabel("Language:"));
            countryPanel.add(comboBox);

            JPanel languagePanel = new JPanel();
            List<String> countryCodes = jsonTranslator.getCountryCodes();
            List<String> countries = new ArrayList<>();
            for (String countryCode: countryCodes)
                countries.add(countryCodeConverter.fromCountryCode(countryCode));
            JList<String> jlist = new JList<>(countries.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(jlist);
            languagePanel.add(new JLabel("Country:"));
            languagePanel.add(scrollPane);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) comboBox.getSelectedItem();
                    String country = jlist.getSelectedValue();
                    String result = jsonTranslator.translate(
                            countryCodeConverter.fromCountry(country),
                            languageCodeConverter.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
