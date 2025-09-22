package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JPanel countryPanel = new JPanel();
      countryPanel.add(new JLabel("Country:"));
      Translator translator = new JSONTranslator();
      List<String> countryCodes = translator.getCountryCodes();
      CountryCodeConverter converterCountry = new CountryCodeConverter();
      List<String> countryNames = new ArrayList<>();
      for (String code : countryCodes) {
        countryNames.add(converterCountry.fromCountryCode(code.toUpperCase()));
      }
      JList<String> countrySelector = new JList<>(countryNames.toArray(new String[0]));
      JScrollPane scrollPane = new JScrollPane(countrySelector);
      countryPanel.add(scrollPane);

      JPanel languagePanel = new JPanel();
      languagePanel.add(new JLabel("Language:"));
      List<String> languageCodes = translator.getLanguageCodes();
      LanguageCodeConverter converter = new LanguageCodeConverter();
      List<String> languageNames = new ArrayList<>();
      for (String code : languageCodes) {
        languageNames.add(converter.fromLanguageCode(code));
      }
      JComboBox<String> languageDropdown = new JComboBox<>(languageNames.toArray(new String[0]));
      languagePanel.add(languageDropdown);

      JPanel buttonPanel = new JPanel();
      JLabel resultLabelText = new JLabel("Translation:");
      buttonPanel.add(resultLabelText);
      JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
      buttonPanel.add(resultLabel);


      // adding listener for when the user clicks the submit button
      Runnable updateTranslation = () -> {
        String selectedCountry = countrySelector.getSelectedValue();
        String selectedLanguage = (String) languageDropdown.getSelectedItem();
        if (selectedCountry != null && selectedLanguage != null) {
          String language = converter.fromLanguage(selectedLanguage);
          String country = converterCountry.fromCountry(selectedCountry);
          if (country != null && language != null) {
            Translator translator1 = new JSONTranslator();
            String result = translator1.translate(country.toLowerCase(), language);
            if (result == null) {
              result = "no translation found!";
            }
            resultLabel.setText(result);
          } else {
            resultLabel.setText("no translation found!");
          }
        } else {
          resultLabel.setText("");
        }
      };

      countrySelector.addListSelectionListener(e -> {
          if (!e.getValueIsAdjusting()) {
            updateTranslation.run();
          }
        });

      languageDropdown.addActionListener(e -> updateTranslation.run());

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
