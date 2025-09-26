package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
            languagePanel.add(scrollPane);

            JPanel translationPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            translationPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            translationPanel.add(resultLabel);

            comboBox.addActionListener(new ActionListener() {
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

            jlist.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
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
            mainPanel.add(translationPanel);
            mainPanel.add(languagePanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
