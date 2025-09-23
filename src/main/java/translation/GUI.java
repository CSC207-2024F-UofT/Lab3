package translation;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import java.util.Locale;
import javax.swing.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();

            // Build language dropdown with human-readable names
            List<String> langCodes = new ArrayList<>(translator.getLanguageCodes());
            Collections.sort(langCodes);
            Map<String, String> codeToDisplay = new HashMap<>();
            for (String lc : langCodes) {
                String display = new Locale(lc).getDisplayLanguage(Locale.ENGLISH);
                if (display == null || display.isEmpty()) display = lc;
                codeToDisplay.put(lc, display);
            }
            String[] displayLanguages = langCodes.stream().map(codeToDisplay::get).toArray(String[]::new);
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageCombo = new JComboBox<>(displayLanguages);
            languagePanel.add(languageCombo);

            // List model to hold translated country names
            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> translationList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(translationList);
            final java.util.List<String> listCodes = new ArrayList<>();

            JPanel translationRow = new JPanel();
            translationRow.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel translationLabel = new JLabel("Translation:");
            JLabel translationValue = new JLabel("");
            translationRow.add(translationLabel);
            translationRow.add(translationValue);

            // Helper to update the list for a given language code
            Runnable refresh = () -> {
                int langIdx = languageCombo.getSelectedIndex();
                String currentLangCode = (langIdx >= 0 && langIdx < langCodes.size()) ? langCodes.get(langIdx) : "en";

                int prevIndex = translationList.getSelectedIndex();
                listModel.clear();
                listCodes.clear();

                List<Map.Entry<String,String>> entries = new ArrayList<>();
                for (String cc : translator.getCountryCodes()) {
                    String english = translator.translate(cc, "en");
                    if (english != null) {
                        entries.add(new AbstractMap.SimpleEntry<>(english, cc));
                    }
                }
                entries.sort((a,b) -> a.getKey().compareToIgnoreCase(b.getKey()));
                for (Map.Entry<String,String> e : entries) {
                    listModel.addElement(e.getKey());
                    listCodes.add(e.getValue());
                }

                int selectIdx = (prevIndex >= 0 && prevIndex < listModel.size()) ? prevIndex : (listModel.isEmpty() ? -1 : 0);
                if (selectIdx >= 0) {
                    translationList.setSelectedIndex(selectIdx);
                    String code = listCodes.get(selectIdx);
                    String translated = translator.translate(code, currentLangCode);
                    translationValue.setText(translated != null ? translated : "no translation found!");
                } else {
                    translationValue.setText("no translation found!");
                }
            };

            // Populate initially and on language change
            languageCombo.addActionListener(e -> refresh.run());
            // initial selection: try to pick English if available
            int initialIndex = langCodes.indexOf("en");
            if (initialIndex >= 0) languageCombo.setSelectedIndex(initialIndex);
            refresh.run();

            translationList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int idx = translationList.getSelectedIndex();
                    if (idx >= 0 && idx < listCodes.size()) {
                        int langIdx = languageCombo.getSelectedIndex();
                        String currentLangCode = (langIdx >= 0 && langIdx < langCodes.size()) ? langCodes.get(langIdx) : "en";
                        String code = listCodes.get(idx);
                        String translated = translator.translate(code, currentLangCode);
                        translationValue.setText(translated != null ? translated : "no translation found!");
                    } else {
                        translationValue.setText("");
                    }
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translationRow);
            mainPanel.add(scrollPane);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
