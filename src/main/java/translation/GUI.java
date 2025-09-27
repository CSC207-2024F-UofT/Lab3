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

    // record selected language and country
    static String selectedLanguage = "German";
    static String selectedCountry = "Canada";
    static String translatedText = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // initialize var
            JSONTranslator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();


            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String languageCode : translator.getLanguageCodes())
            {
                String languageName = languageConverter.fromLanguageCode(languageCode);
                languageComboBox.addItem(languageName);
            }
//            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);
//            languagePanel.add(languageField);

            JPanel translationPanel = new JPanel();
//            JButton submit = new JButton("Submit");
//            translationPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            translationPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            translationPanel.add(resultLabel);

            JPanel countryPanel = new JPanel();
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryConverter.fromCountryCode(countryCode);
            }
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);
//            JTextField countryField = new JTextField(10);
//            countryField.setText("can");
//            countryField.setEditable(false); // we only support the "can" country code for now
//            countryPanel.add(new JLabel("Country:"));
//            countryPanel.add(countryField);


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
            // adding listener to combo box
            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        selectedLanguage = languageComboBox.getSelectedItem().toString();
                        translatedText = translator.translate(
                                countryConverter.fromCountry(selectedCountry),
                                languageConverter.fromLanguage(selectedLanguage)
                        );
                        resultLabel.setText(translatedText);
//                        JOptionPane.showMessageDialog(null, "user selected " + translatedText + "!");
                    }
                }
            });
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    int[] indices = list.getSelectedIndices();
                    String[] items = new String[indices.length];
                    for (int i = 0; i < indices.length; i++) {
                        items[i] = list.getModel().getElementAt(indices[i]);
                    }

                    String selectedStr = items[0];
                    selectedCountry = selectedStr;
                    translatedText = translator.translate(
                            countryConverter.fromCountry(selectedCountry),
                            languageConverter.fromLanguage(selectedLanguage)
                    );
                    resultLabel.setText(translatedText);

//                    JOptionPane.showMessageDialog(null, "User selected:" +
//                            System.lineSeparator() + selectedStr);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            mainPanel.add(languagePanel);
            mainPanel.add(translationPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
