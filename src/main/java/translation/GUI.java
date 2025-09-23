package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();

            LanguageCodeConverter converter = new LanguageCodeConverter();

            languagePanel.add(new JLabel("Language:"));

            Translator translator = new CanadaTranslator();

            String[] items = new String[translator.getLanguageCodes().size()];
            int i = 0;
            for(String languageCode : translator.getLanguageCodes()) {
                items[i++] = converter.fromLanguageCode(languageCode);
            }
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);



            JComboBox<String> languageComboBox = new JComboBox<>();

            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(converter.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);
            languagePanel.setLayout(new BoxLayout(languagePanel, BoxLayout.X_AXIS));

            JLabel resultLabelText = new JLabel("Translation:");
            JPanel resultPanel = new JPanel();
            resultPanel.add(resultLabelText);
            resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryPanel.add(list);

//            countryField.setText("can");
//            countryField.setEditable(false); // we only support the "can" country code for no

            countryPanel.add(countryField);


            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);



            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

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
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
