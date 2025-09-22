package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Task D: GUI that uses JSONTranslator + CountryCodeConverter
 * to let the user pick country and language from dropdown menus.
 */
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Load translators
            Translator translator = new JSONTranslator();
            CountryCodeConverter converter = new CountryCodeConverter();

            // Country dropdown
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            JComboBox<String> countryBox = new JComboBox<>(
                    converter.getNumCountries() > 0
                            ? converter.countryToCountryCode.keySet().toArray(new String[0])
                            : new String[]{});
            countryPanel.add(countryBox);

            // Language dropdown
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageBox = new JComboBox<>(
                    translator.getLanguageCodes().toArray(new String[0]));
            languagePanel.add(languageBox);

            // Button + result label
            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Translate");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel(" ");
            resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            buttonPanel.add(resultLabel);

            // Action listener
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String countryName = (String) countryBox.getSelectedItem();
                    String langCode = (String) languageBox.getSelectedItem();

                    String countryCode = converter.fromCountry(countryName);
                    String result = translator.translate(countryCode, langCode);

                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            // Main panel
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