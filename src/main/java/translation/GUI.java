package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new CanadaTranslator();

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(countryCode);
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            JPanel countryPanel = new JPanel();
            String[] items = countryCodeConverter.getCountries();
            JList countryField = new JList<>(items);
            JScrollPane countryScrollPane = new JScrollPane(countryField);
            countryPanel.add(countryScrollPane);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            countryField.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryField.getSelectedValue().toString();

                    country = countryCodeConverter.fromCountry(country);
                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new CanadaTranslator();

                    String result = translator.translate(country, language);
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
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
