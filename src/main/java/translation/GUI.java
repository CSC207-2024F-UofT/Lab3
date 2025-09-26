package translation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    static JSONTranslator jsonTranslator = new JSONTranslator();
    static LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
    static CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            List<String> languageCodes = jsonTranslator.getLanguageCodes();
            JComboBox<String> comboBox = new JComboBox<>();
            for (String languageCode : languageCodes)
                comboBox.addItem(
                        languageCodeConverter.fromLanguageCode(languageCode));
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

            comboBox.addActionListener(e -> getResults(
                    comboBox, jlist, resultLabel));

            jlist.addListSelectionListener(e -> getResults(
                    comboBox, jlist, resultLabel));

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

    private static void getResults(JComboBox<String> comboBox,
                                   JList<String> jlist,
                                   JLabel resultLabel) {
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
}
