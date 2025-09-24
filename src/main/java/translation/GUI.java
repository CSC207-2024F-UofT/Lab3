package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import translation.JSONTranslator;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            JPanel countryPanel = new JPanel();
//            JTextField countryField = new JTextField(10);
//            countryField.setText("can");
//            countryField.setEditable(false); // we only support the "can" country code for now
//            countryPanel.add(new JLabel("Country:"));
//            countryPanel.add(countryField);


            // 2) translation panel + *translated country name*
            JPanel translationPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            translationPanel.add(resultLabelText);
//            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
//            buttonPanel.add(resultLabel);
            translationPanel.add(resultLabel);

            // 1) language panel JComboBox
                // select the language we want the country name to be translated into

            JPanel languagePanel = new JPanel();
//            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
//            CountryPanel.add(languageField);

            Translator translator = new JSONTranslator();

            // create combobox, add country codes into it, and add it to our panel
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(countryCode);
            }
            languagePanel.add(languageComboBox);



//            JPanel buttonPanel = new JPanel();
//            JButton submit = new JButton("Submit");
//            buttonPanel.add(submit);




            // adding listener for when the user clicks the submit button
//            submit.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String language = languageField.getText();
////                    String country = countryField.getText();
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

            // 3) country selection panel JList
                // select country names to be translated
                // once selected, can translate
            JPanel CountryPanel = new JPanel();
            CountryPanel.setLayout(new GridLayout(0, 2));
            CountryPanel.add(new JLabel("Language:"), 0);

            String[] items = new String[translator.getCountryCodes().size()];

//            JComboBox<String> countryComboBox = new JComboBox<>();
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCode;
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            CountryPanel.add(scrollPane, 1);


            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    int[] indices = list.getSelectedIndices();
                    String[] items = new String[indices.length];
                    for (int i = 0; i < indices.length; i++) {
                        items[i] = list.getModel().getElementAt(indices[i]);
                    }

//                    JOptionPane.showMessageDialog(null, "User selected:" +
//                            System.lineSeparator() + Arrays.toString(items));
//                    String language = languageField.getText();
//                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
//                    Translator translator = new CanadaTranslator();
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = items[0];

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

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
                        String country = list.getSelectedValue();
                        String language = languageComboBox.getSelectedItem().toString();

                        String result = translator.translate(country, language);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);
                    }
                }


            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//            mainPanel.add(countryPanel);
            mainPanel.setPreferredSize(new Dimension(500, 300));
            mainPanel.add(languagePanel);
            mainPanel.add(translationPanel);
            mainPanel.add(CountryPanel);
//            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
