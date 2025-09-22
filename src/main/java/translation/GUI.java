package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;


public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            String[] items = new String[translator.getCountryCodes().size()];
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane, 1);
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);


            JPanel buttonPanel = new JPanel();


            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            languageComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageCodeConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = countryCodeConverter.fromCountry(list.getSelectedValue().toString()).toLowerCase();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String language = languageCodeConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = countryCodeConverter.fromCountry(list.getSelectedValue().toString()).toLowerCase();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later

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
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
