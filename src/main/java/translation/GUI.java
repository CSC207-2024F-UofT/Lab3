package translation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize the JSONTranslator
            Translator translator = new JSONTranslator();

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"), 0);

            // Read JSON file to get English country names
            Map<String, String> countryCodeToName = new HashMap<>();
            try {
                String jsonString = Files.readString(Paths.get(GUI.class.getClassLoader().getResource("sample.json").toURI()));
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject countryData = jsonArray.getJSONObject(i);
                    String countryCode = countryData.getString("alpha3");
                    String englishName = countryData.getString("en");
                    countryCodeToName.put(englishName, countryCode);
                }
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }

            // Create JList for countries with English names
            String[] countryNames = countryCodeToName.keySet().toArray(new String[0]);
            Arrays.sort(countryNames);
            JList<String> countryList = new JList<>(countryNames);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryPanel.add(countryScrollPane, 1);

            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageField);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(e -> {
                String selectedCountryName = countryList.getSelectedValue();
                String language = languageField.getText();

                if (selectedCountryName != null && !language.trim().isEmpty()) {
                    String countryCode = countryCodeToName.get(selectedCountryName);
                    String result = translator.translate(countryCode, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                } else {
                    resultLabel.setText("Please select a country and enter a language");
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
