package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // -------- Data services (back end) --------
            // Uses your Part C translator + Part A/B converters.
            Translator translator = new JSONTranslator("sample.json");
            CountryCodeConverter countryConv = new CountryCodeConverter();
            LanguageCodeConverter langConv = new LanguageCodeConverter();

            // -------- Build language dropdown (show NAMES) --------
            // We’ll preserve a deterministic mapping from displayed name -> code.
            Map<String, String> langNameToCode = new LinkedHashMap<>();
            List<String> langNames = new ArrayList<>();
            for (String code : translator.getLanguageCodes()) {
                String name = langConv.fromLanguageCode(code);
                if (name == null || name.isEmpty()) {
                    name = code; // fallback if Task A not finished yet
                }
                // Avoid accidental duplicates
                if (!langNameToCode.containsKey(name)) {
                    langNameToCode.put(name, code.toLowerCase());
                    langNames.add(name);
                }
            }
            Collections.sort(langNames); // nice UX

            DefaultComboBoxModel<String> langModel = new DefaultComboBoxModel<>();
            for (String name : langNames) {
                langModel.addElement(name);
            }
            JComboBox<String> languageCombo = new JComboBox<>(langModel);

            // -------- Build country list (show NAMES) --------
            Map<String, String> countryNameToCode = new LinkedHashMap<>();
            List<String> countryNames = new ArrayList<>();
            for (String ccode : translator.getCountryCodes()) {
                String cname = countryConv.fromCountryCode(ccode);
                if (cname == null || cname.isEmpty()) {
                    cname = ccode; // fallback if Task B not finished yet
                }
                if (!countryNameToCode.containsKey(cname)) {
                    countryNameToCode.put(cname, ccode.toLowerCase());
                    countryNames.add(cname);
                }
            }
            Collections.sort(countryNames); // matches the example screenshot ordering

            DefaultListModel<String> countryModel = new DefaultListModel<>();
            for (String name : countryNames) {
                countryModel.addElement(name);
            }
            JList<String> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane countryScroll = new JScrollPane(countryList);
            countryScroll.setPreferredSize(new Dimension(320, 260));

            // -------- Result label --------
            JLabel resultLabel = new JLabel("Translation: ");
            resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

            // -------- Action: recompute translation on any selection change --------
            Runnable refresh = () -> {
                String countryName = countryList.getSelectedValue();
                String langName = (String) languageCombo.getSelectedItem();
                if (countryName == null || langName == null) return;

                // Convert display names -> codes
                String countryCode = countryNameToCode.get(countryName);
                String langCode = langNameToCode.get(langName);

                // Defensive fallbacks in case A/B not finished
                if (countryCode == null) countryCode = countryConv.fromCountry(countryName);
                if (langCode == null) langCode = langConv.fromLanguage(langName);
                if (countryCode == null || langCode == null) {
                    resultLabel.setText("Translation: no translation found!");
                    return;
                }

                String out = translator.translate(countryCode.toLowerCase(), langCode.toLowerCase());
                resultLabel.setText("Translation: " + (out != null ? out : "no translation found!"));
            };

            // -------- Listeners (immediate UI updates) --------
            languageCombo.addActionListener(e -> refresh.run());
            countryList.addListSelectionListener((ListSelectionListener) e -> {
                if (!e.getValueIsAdjusting()) refresh.run();
            });

            // -------- Layout (close to the screenshot) --------
            JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
            topRow.add(new JLabel("Language:"));
            topRow.add(languageCombo);

            JPanel main = new JPanel();
            main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
            main.add(topRow);
            main.add(resultLabel);
            main.add(countryScroll);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(main);
            frame.pack();

            // -------- Defaults: select first items & show initial translation --------
            if (!countryModel.isEmpty()) countryList.setSelectedIndex(0);
            if (languageCombo.getItemCount() > 0) languageCombo.setSelectedIndex(0);
            refresh.run();

            frame.setVisible(true);
        });
    }
}
