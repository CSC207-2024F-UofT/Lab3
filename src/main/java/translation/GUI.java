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
			final LanguageCodeConverter langConverter = new LanguageCodeConverter();

            // Country dropdown
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            JComboBox<String> countryBox = new JComboBox<>(
                    converter.getNumCountries() > 0
                            ? converter.countryToCountryCode.keySet().toArray(new String[0])
                            : new String[]{});
            countryPanel.add(countryBox);

			// Language dropdown (show full language names)
			JPanel languagePanel = new JPanel();
			languagePanel.add(new JLabel("Language:"));
			List<String> languageCodes = translator.getLanguageCodes();
			String[] languageNames = new String[languageCodes.size()];
			for (int i = 0; i < languageCodes.size(); i++) {
				String code = languageCodes.get(i);
				languageNames[i] = langConverter.fromLanguageCode(code);
			}
			JComboBox<String> languageBox = new JComboBox<>(languageNames);
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
					String languageName = (String) languageBox.getSelectedItem();

					String countryCode = converter.fromCountry(countryName);
					String langCode = langConverter.fromLanguage(languageName);
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