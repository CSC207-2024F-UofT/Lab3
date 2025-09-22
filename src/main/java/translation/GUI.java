package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Translator translator = new CanadaTranslator();
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(countryCode);
            }
            languagePanel.add(languageComboBox);

            JPanel countryPanel = new JPanel();
            JComboBox<String> countryComboBox = new JComboBox<>();
            countryPanel.setLayout(new GridLayout(0, 2));
            countryPanel.add(new JLabel("Country:"), 0);

            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCode;
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

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
                        String country = languageComboBox.getSelectedItem().toString();
                        JOptionPane.showMessageDialog(null, "user selected " + country + "!");
                    }
                }
            });

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) languageComboBox.getSelectedItem();
                    List<String> selectedCountries = list.getSelectedValuesList();

                    if (language == null || selectedCountries.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please select a language and at least one country.");
                        return;
                    }

                    Translator translator = new CanadaTranslator();
                    StringBuilder resultBuilder = new StringBuilder();

                    for (String country : selectedCountries) {
                        String result = translator.translate(country, language);
                        if (result == null) {
                            result = "No translation found for " + country;
                        }
                        resultBuilder.append(country).append(" → ").append(result).append("  |  ");
                    }

                    resultLabel.setText(resultBuilder.toString());
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
