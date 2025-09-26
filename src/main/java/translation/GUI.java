package translation;

import javax.swing.*;
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
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JTextField languageField = new JTextField(10);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            // translator to fetch supported codes
            Translator translator = new CanadaTranslator();
            String[] items = translator.getLanguageCodes().toArray(new String[0]);

            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new java.awt.Dimension(120, 80));
            languagePanel.add(scrollPane);



            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            submit.addActionListener(e -> {
                java.util.List<String> selected = list.getSelectedValuesList();
                String country = countryField.getText();

                Translator innerTranslator = new CanadaTranslator();

                StringBuilder results = new StringBuilder();
                for (String language : selected) {
                    String result = innerTranslator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    results.append(language).append(": ").append(result).append("   ");
                }

                resultLabel.setText(results.toString());
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
