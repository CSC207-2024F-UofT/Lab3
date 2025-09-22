package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            //country panel (Below)
            JPanel countryPanel = new JPanel();
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }
            JList<String> list = new JList<>(items);
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);

            //Language panel (above)
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);



            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            //listener to check for selections within list
            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    String countrySelected = list.getSelectedValue();
                    //Ignore error for toString, if u remove the toString the code tweaks out
                    String languageSelected = languageComboBox.getSelectedItem().toString();

                    //convert from country to country code
                    String countryCode = countryCodeConverter.fromCountry(countrySelected);
                    String languageCode = languageCodeConverter.fromLanguage(languageSelected);

                    //test case
                    //System.out.println(countryCode + " " + languageCode);
                    resultLabel.setText(translator.translate(countryCode, languageCode));

                }
            });

            // add listener for when an item is selected.
            languageComboBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String countrySelected = list.getSelectedValue();
                        //Ignore error for toString, if u remove the toString the code tweaks out
                        String languageSelected = languageComboBox.getSelectedItem().toString();

                        //convert from country to country code
                        String countryCode = countryCodeConverter.fromCountry(countrySelected);
                        String languageCode = languageCodeConverter.fromLanguage(languageSelected);

                        //test case
                        //System.out.println(countryCode + " " + languageCode);
                        resultLabel.setText(
                                translator.translate(countryCode, languageCode));
                    }
                }


            });
            // adding listener for when the user clicks the submit button
//            submit.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String language = languageField.getText();
//                    String country = countryField.getText();
//
//                    // for now, just using our simple translator, but
//                    // we'll need to use the real JSON version later.
//                    Translator translator = new CanadaTranslator();
//
//                    String result = translator.translate(country, language);
//                    if (result == null) {
//                        result = "no translation found!";
//                    }
//                    resultLabel.setText(result);
//
//                }
//
//            });

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
