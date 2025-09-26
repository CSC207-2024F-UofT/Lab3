package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GUI {

    private static class LabeledCode {
        final String label; // shown to user
        final String code;  // passed to Translator.translate(...)
        LabeledCode(String label, String code) { this.label = label; this.code = code; }
        @Override public String toString() { return label; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();                  // uses sample.json
            CountryCodeConverter countryConv = new CountryCodeConverter(); // uses country-codes.txt
            LanguageCodeConverter langConv   = new LanguageCodeConverter();// uses language-codes.txt

            // ---- Language dropdown: ALWAYS show names ----
            List<String> langCodes = translator.getLanguageCodes();
            List<LabeledCode> langItems = new ArrayList<>();
            for (String lc : langCodes) {
                String name = langConv.fromLanguageCode(lc);
                if (name == null || name.isEmpty()) name = langConv.fromLanguageCode(lc.toUpperCase());
                if (name == null || name.isEmpty()) name = langConv.fromLanguageCode(lc.toLowerCase());
                if (name == null || name.isEmpty()) name = lc; // extreme fallback
                langItems.add(new LabeledCode(name, lc));
            }
            langItems.sort(Comparator.comparing(lc -> lc.label.toLowerCase()));
            JComboBox<LabeledCode> languageCombo = new JComboBox<>(langItems.toArray(new LabeledCode[0]));

            JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageCombo);

            // ---- Country list: ALWAYS show names ----
            DefaultListModel<LabeledCode> countryModel = new DefaultListModel<>();
            List<String> countryCodes = translator.getCountryCodes();
            List<LabeledCode> countryItems = new ArrayList<>();
            for (String cc : countryCodes) {
                String name = countryConv.fromCountryCode(cc);
                // Fallback: try English translation from translator (works once JSONTranslator is fully implemented)
                if ((name == null || name.isEmpty())) {
                    name = "null";
                }
                countryItems.add(new LabeledCode(name, cc));
            }
            countryItems.sort(Comparator.comparing(c -> c.label.toLowerCase()));
            for (LabeledCode item : countryItems) countryModel.addElement(item);

            JList<LabeledCode> countryList = new JList<>(countryModel);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            countryList.setVisibleRowCount(12);
            JScrollPane countryScroll = new JScrollPane(countryList);
            countryScroll.setPreferredSize(new Dimension(260, 260));

            JPanel countryPanel = new JPanel(new BorderLayout(6, 6));
            countryPanel.add(countryScroll, BorderLayout.CENTER);

            // ---- Result area ----
            JLabel resultTitle = new JLabel("Translation:");
            JLabel resultValue = new JLabel(" ");
            resultValue.setFont(resultValue.getFont().deriveFont(Font.BOLD, 14f));
            JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            resultPanel.add(resultTitle);
            resultPanel.add(resultValue);

            // ---- Update result ----
            Runnable updateResult = () -> {
                LabeledCode lang = (LabeledCode) languageCombo.getSelectedItem();
                LabeledCode ctry = countryList.getSelectedValue();
                if (lang == null || ctry == null) { resultValue.setText("—"); return; }

                String countryCode = ctry.code == null ? null : ctry.code.toLowerCase();
                String langCode    = lang.code  == null ? null : lang.code.toLowerCase();

                String text = translator.translate(countryCode, langCode);
                resultValue.setText((text == null || text.isEmpty()) ? "(no translation found)" : text);
            };

            languageCombo.addActionListener(e -> updateResult.run());
            countryList.addListSelectionListener((ListSelectionEvent e) -> {
                if (!e.getValueIsAdjusting()) updateResult.run();
            });

            // ---- Defaults ----
            int englishIndex = -1;
            for (int i = 0; i < languageCombo.getItemCount(); i++) {
                if (Objects.equals(languageCombo.getItemAt(i).code, "en")) { englishIndex = i; break; }
            }
            if (englishIndex >= 0) languageCombo.setSelectedIndex(englishIndex);

            int canadaIndex = -1;
            for (int i = 0; i < countryModel.size(); i++) {
                if (Objects.equals(countryModel.get(i).code.toLowerCase(), CanadaTranslator.CANADA)) { canadaIndex = i; break; }
            }
            if (canadaIndex >= 0) countryList.setSelectedIndex(canadaIndex);
            else if (!countryModel.isEmpty()) countryList.setSelectedIndex(0);

            updateResult.run();

            // ---- Layout ----
            JPanel main = new JPanel();
            main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
            main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            main.add(languagePanel);
            main.add(Box.createVerticalStrut(8));
            main.add(countryPanel);
            main.add(Box.createVerticalStrut(12));
            main.add(resultPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(main);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }
}
