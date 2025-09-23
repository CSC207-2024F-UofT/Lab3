package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JSONTranslator translator = new JSONTranslator();
            CountryCodeConverter countryConv = new CountryCodeConverter();
            LanguageCodeConverter languageConv = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel();
            List<String> countryCodes = translator.getCountryCodes();
            DefaultListModel countryListModel = new DefaultListModel();
                    for (int i = 0; i < countryCodes.size(); i++) {
                        countryListModel.addElement(countryConv.fromCountryCode(countryCodes.get(i)));
                    }
            JList countryList = new JList(countryListModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setLayoutOrientation(JList.VERTICAL);
            countryList.setSelectedIndex(0);
//            countryList.addListSelectionListener(a -> {});
            countryList.setVisibleRowCount(8);
            JScrollPane listScroller = new JScrollPane(countryList);
            countryPanel.add(listScroller);

//            JComboBox countryBox = new JComboBox();
//            for (int i = 0; i < countryCodes.size(); i++){
//                countryBox.addItem(countryConv.fromCountryCode(countryCodes.get(i)));
//            }
//            countryPanel.add(new JLabel("Country:"));
//            countryPanel.add(countryBox);

            JPanel languagePanel = new JPanel();
            JComboBox languageBox = new JComboBox();
            List<String> languageCodes = translator.getLanguageCodes();
            for (int i = 0; i < languageCodes.size(); i++){
                languageBox.addItem(languageConv.fromLanguageCode(languageCodes.get(i)));
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageBox);

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
                    String language = languageConv.fromLanguage((String) languageBox.getSelectedItem());
                    //String country = countryConv.fromCountry((String) countryBox.getSelectedItem());
                    //String country = CountryConv.fromCountry();
                    String country = countryConv.fromCountry((String) countryList.getSelectedValue());

                    String result = translator.translate(country.toLowerCase(), language);

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
/*  Tired to implement a function that allowed the translator to update based on
    when it was changed, but I couldn't find the right documentation to get it to
    work, so here it lies.
*/
//        public void valueChanged(ListSelectionEvent a) {
//            String language = languageConv.fromLanguage((String) languageBox.getSelectedItem());
//            String country = countryConv.fromCountry((String) countryList.getSelectedValue());
//
//            String result = translator.translate(country.toLowerCase(), language);
//
//            if (result == null) {
//                result = "no translation found!";
//            }
//
//            resultLabel.setText(result);
//        }