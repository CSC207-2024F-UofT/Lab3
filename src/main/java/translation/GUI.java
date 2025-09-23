package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));



            Translator translator = new CanadaTranslator();

            String[] items = new String[translator.getLanguageCodes().size()];



            // create combobox, add country codes into it, and add it to our panel
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String LanguageCode : translator.getLanguageCodes()) {

                languageComboBox.addItem(LanguageCodeConverter.fromLanguageCode(LanguageCode));
            }
            languagePanel.add(languageComboBox);


//            Translator translator = new CanadaTranslator();
//
//            String[] items = new String[translator.getLanguageCodes().size()];
//
//            JComboBox<String> languageComboBox = new JComboBox<>();
//            int i = 0;
//            for(String langaugeCode : translator.getLanguageCodes()) {
//                items[i++] = translator.translate(langaugeCode,countryCode);
//            }
//
//            // create the JList with the array of strings and set it to allow multiple
//            // items to be selected at once.
//            JList<String> list = new JList<>(items);
//            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//
//            // place the JList in a scroll pane so that it is scrollable in the UI
//            JScrollPane scrollPane = new JScrollPane(list);
//            languagePanel.add(scrollPane, 1);



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
                    String language = languageField.getText();
                    String country = countryField.getText();

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
