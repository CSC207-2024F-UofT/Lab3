package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            LanguageCodeConverter langConv = new LanguageCodeConverter();
            CountryCodeConverter countryConv = new CountryCodeConverter();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            java.util.List<String> alpha3List = new ArrayList<>(translator.getCountryCodes());
            Collections.sort(alpha3List);
            DefaultListModel<String> countryModel = new DefaultListModel<>();
            Map<String, String> countryNameToA3 = new HashMap<>();
            for (String a3 : alpha3List) {
                String name = countryConv.fromCountryCode(a3);
                if (name != null){
                    countryModel.addElement(name);
                    countryNameToA3.put(name, a3);
                }
            }
            JList<String> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(5);
            if (!countryModel.isEmpty()) countryList.setSelectedIndex(0);

            JScrollPane countryScroll = new JScrollPane(countryList);
            countryScroll.setPreferredSize(new Dimension(200, 100));
            countryPanel.add(countryScroll);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            java.util.List<String> langCodes = new ArrayList<>(translator.getLanguageCodes());
            Collections.sort(langCodes);
            Map<String, String> langNameToCode = new HashMap<>();
            java.util.List<String> langNames = new ArrayList<>();
            for (String code : langCodes){
                String name = langConv.fromLanguageCode(code);
                if (name == null) name = code;
                langNames.add(name);
                langNameToCode.put(name, code);
            }

            JComboBox<String> languageBox = new JComboBox<>(langNames.toArray(new String[0]));
            languagePanel.add(languageBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel(" ");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String countryName = countryList.getSelectedValue();
                    String langName = (String) languageBox.getSelectedItem();
                    if (countryName == null || langName == null) {
                        resultLabel.setText(" ");
                        return;
                    }
                    String a3 = countryNameToA3.get(countryName);
                    String langCode = langNameToCode.get(langName);

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = translator.translate(a3.toLowerCase(), langCode.toLowerCase());
                    if (result == null) result = "no translation found!";
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
}