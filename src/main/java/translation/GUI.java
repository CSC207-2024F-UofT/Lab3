package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator translator = new JSONTranslator();
            LanguageCodeConverter langConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            // DROPDOWN PANEL
            List<String> languageCodes = translator.getLanguageCodes();
            List<String> languageNames = new ArrayList<>();

            for (String code: languageCodes){
                languageNames.add(langConverter.fromLanguageCode(code));
            }
            //String[] languageNamesArr = new String[languageNames.size()];
            //languageNames.toArray(languageNamesArr);
            String[] languageNamesArr = {"1", "2", "3"};
            JComboBox<String> dropdown = new JComboBox<String>(languageNamesArr);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(dropdown);


            // Reults panel
            JPanel resultPanel = new JPanel();
            JLabel resultLabel = new JLabel("");
            resultPanel.add(new JLabel("Translation:"));
            resultPanel.add(resultLabel);

            // scroll select panel
            JPanel scrollPanel = new JPanel();

            List<String> countryCodes = translator.getCountryCodes();
            List<String> countryNames = new ArrayList<>();

            for (String code: countryCodes){
                countryNames.add(countryCodeConverter.fromCountryCode(code));
            }

            // String[] countryNamesArr = new String[countryNames.size()];
            // countryNames.toArray(countryNamesArr);
            String[] countryNamesArr = {"1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3",
                    "1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3",};
            JList<String> countryList = new JList<>(countryNamesArr);
            JScrollPane scrollPane = new JScrollPane(countryList);
            scrollPane.setPreferredSize(new Dimension(200, 150));
            scrollPanel.add(scrollPane);


            // adding listener for when the user clicks the submit button
            dropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = "TOOO";
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(scrollPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
