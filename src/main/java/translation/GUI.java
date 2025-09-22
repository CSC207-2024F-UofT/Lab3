package translation;

import javax.swing.*;
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
            Translator translator = new JSONTranslator();

            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));
            countryPanel.add(new JLabel("Country:"));

            DefaultListModel<CountryItem> countryModel = new DefaultListModel<>();

            // ADD this block after creating countryModel/countryList:
            for (String code3 : translator.getCountryCodes()) {
                String displayName = translator.translate(code3, "en"); // show English name in the list
                if (displayName == null || displayName.isBlank()) displayName = code3;
                countryModel.addElement(new CountryItem(code3, displayName)); // keep code as-is (JSON uses alpha-3 like "CAN")
            }

            JList<CountryItem> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(10); // adjust how tall it looks

            JScrollPane countryScroll = new JScrollPane(countryList);
            countryPanel.add(countryScroll);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            // adding drop down menu for the languages
            // from ComboBoxDemo.java:
            // create combobox, add country codes into it, and add it to our panel
            JComboBox<String> languageComboBox = new JComboBox<>();
            //

            LanguageCodeConverter langConverter = new LanguageCodeConverter("language-codes.txt");
            System.out.println(langConverter.fromLanguageCode("fr"));

            for (String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCode); // store the code ("en", "fr", ...)
            }
// Show names in the UI while keeping codes internally
            languageComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(
                        JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    String code = (String) value;
                    String name = langConverter.fromLanguageCode(code);
                    setText((name != null && !name.isBlank()) ? name : code);
                    return c;
                }
            });

            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            // make a country code convertor too
            CountryCodeConverter countryConverter = new CountryCodeConverter();

            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CountryItem selected = countryList.getSelectedValue();
                    if (selected == null) {
                        resultLabel.setText("Please select a country.");
                        return;
                    }
                    String country = selected.code(); // e.g., "CAN"

                    String languageCode = (String) languageComboBox.getSelectedItem(); // e.g., "en"

                    // reuse the existing translator instance you created earlier
                    String result = translator.translate(country, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });


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

    static class CountryItem {
        private final String code;   // alpha-3, lowercase (e.g., "can")
        private final String name;   // display name (e.g., "Canada")
        CountryItem(String code, String name) {
            this.code = code;
            this.name = name;
        }
        String code() { return code; }
        @Override public String toString() { return name; } // shown in the JList
    }

}
