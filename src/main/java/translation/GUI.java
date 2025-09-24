package translation;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Task D GUI (uses your implemented methods directly)
 * - JComboBox for languages from Translator.getLanguageCodes()
 * - JList for country names from CountryCodeConverter.getAllCountries()
 * - Uses CountryCodeConverter.getAlpha2ByCountry(name) to map back to code
 * - Calls Translator.translate(countryCode, languageCode)
 */
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::createAndShow);
    }

    private static void createAndShow() {
        Translator translator = new JSONTranslator();
        CountryCodeConverter countryConv = new CountryCodeConverter();
        LanguageCodeConverter languageConv = new LanguageCodeConverter();

        // Frame
        JFrame frame = new JFrame("Country Name Translator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // TOP: Language dropdown
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        languagePanel.add(new JLabel("Language:"));
        java.util.List<String> languageCodes = new ArrayList<>(translator.getLanguageCodes());
        java.util.List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            // convert code to name using your converter
            String name = languageConv.fromLanguageCode(code);
            languageNames.add(name);
        }
        Collections.sort(languageNames, String.CASE_INSENSITIVE_ORDER);
        JComboBox<String> languageCombo = new JComboBox<>(languageNames.toArray(new String[0]));
        languageCombo.setPrototypeDisplayValue("zz-PrototypeValue");
        languagePanel.add(languageCombo);
        mainPanel.add(languagePanel);

        // CENTER: Translation output
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JLabel resultLabelText = new JLabel("Translation:");
        JLabel resultLabel = new JLabel("");
        center.add(resultLabelText);
        center.add(resultLabel);
        mainPanel.add(center);

        // BOTTOM: Country list
        java.util.List<String> countryNames = new ArrayList<>(countryConv.getAllCountries());
        Collections.sort(countryNames, String.CASE_INSENSITIVE_ORDER);

        JList<String> countryList = new JList<>(countryNames.toArray(new String[0]));
        countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane countryScroll = new JScrollPane(countryList);
        countryScroll.setPreferredSize(new Dimension(260, 360));

        JPanel countryPanel = new JPanel(new BorderLayout(8, 8));
        countryPanel.add(new JLabel("Country:"), BorderLayout.NORTH);
        countryPanel.add(countryScroll, BorderLayout.CENTER);
        mainPanel.add(countryPanel);

        Runnable update = () -> {
            String countryName = countryList.getSelectedValue();
            Object langObj = languageCombo.getSelectedItem();

            if (countryName == null || langObj == null) return;

            String languageCode = languageConv.fromLanguage(langObj.toString());
            String countryCode = countryConv.fromCountry(countryName);

            String translated = translator.translate(countryCode, languageCode);
            if (translated == null) translated = "no translation found!";
            resultLabel.setText(translated);
        };

        languageCombo.addActionListener(e -> update.run());
        countryList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) update.run(); });


        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
