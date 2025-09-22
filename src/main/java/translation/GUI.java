package translation;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();

            List<String> countries = translator.getCountryCodes();
            JList<String> countryList = new JList<>(countries.toArray(new String[0]));
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane countryScroll = new JScrollPane(countryList);
            countryScroll.setPreferredSize(new Dimension(150, 200));

            // language dropdown
            List<String> languages = translator.getLanguageCodes();
            JComboBox<String> languageCombo = new JComboBox<>(languages.toArray(new String[0]));

            // result label
            JLabel resultLabel = new JLabel("Translation will appear here");

            // panel setup
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Select Country:"));
            countryPanel.add(countryScroll);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Select Language:"));
            languagePanel.add(languageCombo);

            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultLabel);

            // Event listeners
            ListSelectionListener updateTranslation = e -> {
                String selectedCountry = countryList.getSelectedValue();
                String selectedLanguage = (String) languageCombo.getSelectedItem();
                if (selectedCountry != null && selectedLanguage != null) {
                    String translation = translator.translate(selectedCountry, selectedLanguage);
                    if (translation == null) translation = "No translation found!";
                    resultLabel.setText(translation);
                }
            };

            countryList.addListSelectionListener(updateTranslation);
            languageCombo.addActionListener(e -> updateTranslation.valueChanged(null));

            // Frame setup
            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
