package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {
    private static String selected_language_code;
    private static String selected_country_code;
    private static JLabel translation;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();
            JPanel countryPanel = new JPanel();
            JPanel translationPanel = new JPanel();

            // Create Translator object that pulls translations data from JSON file
            JSONTranslator translator = new JSONTranslator();
            languagePanel.add(new JLabel("Language:"));


            // Create Language Code Converter object

            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            CountryCodeConverter countryConverter = new CountryCodeConverter();

            selected_country_code = translator.getCountryCodes().get(0);
            selected_language_code = translator.getLanguageCodes().get(0);

            translation =  new JLabel("Translation: " + translator.translate(selected_country_code, selected_language_code));


            // create combo box for displaying language names by adding
            JComboBox<String> languageComboBox = new JComboBox<>();
            loadComboBox(translator, languageConverter, languageComboBox);


            String[] countries = new String[translator.getCountryCodes().size()];
            loadJList(translator, countries, countryConverter);
            JList<String> list = new JList<>(countries);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);


            languagePanel.add(languageComboBox);
            countryPanel.add(scrollPane);
            translationPanel.add(translation);


            languageComboBox.addItemListener(new ItemListener(){

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
                        String selected = languageComboBox.getSelectedItem().toString();
                        selected_language_code = languageConverter.fromLanguage(selected);
                        updateTranslation(selected_language_code, selected_country_code, translator);
                    }
                }

            });

            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    int index = list.getSelectedIndex();
                    String selected = list.getModel().getElementAt(index);
                    selected_country_code = countryConverter.fromCountry(selected);
                    updateTranslation(selected_language_code, selected_country_code, translator);

                }
            });



            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translationPanel);
            mainPanel.add(countryPanel);


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }

    /**
     * Loads all countries (obtained from translator) into a string array
     *
     * @param translator translator object
     * @param countries an array of strings to contain all country names
     * @param countryConverter converter object to for country name/code
     */
    private static void loadJList(JSONTranslator translator, String[] countries, CountryCodeConverter countryConverter) {

        int i = 0;
        for(String countryCode : translator.getCountryCodes()){
            if (i >= countries.length)
                break;


            countries[i++] = countryConverter.fromCountryCode(countryCode);

        }
    }

    private static void loadComboBox(JSONTranslator translator, LanguageCodeConverter languageConverter, JComboBox<String> languageComboBox) {
        for(String languageCode : translator.getLanguageCodes()) {

            String language_name = languageConverter.fromLanguageCode(languageCode);
            languageComboBox.addItem(language_name);

            // For debugging
//            if (language_name == null){
//                System.out.println(languageCode);
//                System.out.println(language_name);
//            }
        }
    }

    private static void updateTranslation(String language_code, String country_code, JSONTranslator translator){
        translation.setText("Translation: " + translator.translate(country_code, language_code));

        // for debugging
        System.out.println(translation.getText());
    }
}
