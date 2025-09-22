package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            languagePanel.add(new JLabel("Language:"));

            // Read language-codes.txt to get full language names
            Map<String, String> codeToLanguageName = new HashMap<>();
            Map<String, String> languageNameToCode = new HashMap<>();
            try {
                String languageCodesContent = Files.readString(Paths.get(GUI.class.getClassLoader().getResource("language-codes.txt").toURI()));
                String[] lines = languageCodesContent.split("\n");

                for (int i = 1; i < lines.length; i++) { // Skip header line
                    String line = lines[i].trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split("\t");
                        if (parts.length >= 2) {
                            String languageName = parts[0];
                            String languageCode = parts[1];
                            codeToLanguageName.put(languageCode, languageName);
                            languageNameToCode.put(languageName, languageCode);
                        }
                    }
                }
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }

            // Get available language codes from JSONTranslator and map to names
            java.util.List<String> availableLanguageCodes = translator.getLanguageCodes();
            java.util.List<String> availableLanguageNames = new ArrayList<>();

            for (String code : availableLanguageCodes) {
                String name = codeToLanguageName.get(code);
                if (name != null) {
                    availableLanguageNames.add(name);
                } else {
                    // Fallback to code if name not found
                    availableLanguageNames.add(code);
                }
            }

            String[] languageNames = new String[availableLanguageNames.size()];
            for (int i = 0; i < availableLanguageNames.size(); i++) {
                languageNames[i] = availableLanguageNames.get(i);
            }
            Arrays.sort(languageNames);
            JComboBox<String> languageComboBox = new JComboBox<>(languageNames);
            languagePanel.add(languageComboBox);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("Select a country and language");
            resultPanel.add(resultLabel);

            // Method to update translation
            Runnable updateTranslation = () -> {
                String selectedCountryName = countryList.getSelectedValue();
                String selectedLanguageName = (String) languageComboBox.getSelectedItem();

                if (selectedCountryName != null && selectedLanguageName != null) {
                    String countryCode = countryCodeToName.get(selectedCountryName);
                    String languageCode = languageNameToCode.get(selectedLanguageName);
                    if (languageCode == null) {
                        languageCode = selectedLanguageName; // Fallback if name not found
                    }
                    String result = translator.translate(countryCode, languageCode);
                    if (result == null || result.equals("We don't support this language")) {
                        result = "Translation not available";
                    }
                    resultLabel.setText(result);
                } else {
                    resultLabel.setText("Select a country and language");
                }
            };

            // Add listeners for automatic translation updates
            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        updateTranslation.run();
                    }
                }
            });

            languageComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTranslation.run();
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
