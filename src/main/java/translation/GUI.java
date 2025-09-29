package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {
    private abstract static class TranslationUIComponent {
        protected String selection = null;
        protected JPanel panel = new JPanel();

        protected final TranslationUI translationUI;

        protected abstract void makePanel(Translator translator);
        protected abstract void addListener();

        protected TranslationUIComponent(TranslationUI translationUIIn) {
            translationUI = translationUIIn;
        }

        protected void createUI() {
            makePanel(translationUI.getTranslator());
            addListener();
        }

        public boolean isSelected() {
            return selection != null;
        }

        public String getSelected() {
            return selection;
        }

        public JPanel getPanel() {
            return panel;
        }
    }

    private static class CountryList extends TranslationUIComponent {
        private JList<String> countryList;

        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

        public CountryList(TranslationUI translationUI) {
            super(translationUI);
            createUI();
        }

        public CountryCodeConverter getCountryCodeConverter() {
            return countryCodeConverter;
        }

        @Override
        protected void makePanel(Translator translator) {
            panel.setLayout(new GridLayout(0, 1));

            List<String> countryCodes = translator.getCountryCodes();
            String[] countries = new String[countryCodes.size()];
            for (int i = 0; i < countryCodes.size(); i++) {
                countries[i] = countryCodeConverter.fromCountryCode(countryCodes.get(i));
            }

            countryList = new JList<>(countries);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(countryList);
            panel.add(scrollPane);
        }

        @Override
        protected void addListener() {
            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (countryList.isSelectionEmpty()) {
                        selection = null;
                    } else {
                        selection = countryList.getSelectedValue();
                    }

                    translationUI.updateTranslation();
                }
            });
        }
    }

    private static class LanguageDropDown extends TranslationUIComponent {
        private JComboBox<String> languageComboBox;

        LanguageCodeConverter languageCodeConverter =  new LanguageCodeConverter();

        public LanguageDropDown(TranslationUI translationUI) {
            super(translationUI);
            createUI();
        }

        public LanguageCodeConverter getLanguageCodeConverter() {
            return languageCodeConverter;
        }

        @Override
        protected void makePanel(Translator translator) {
            // adds the label
            panel.add(new JLabel("Language:"));

            languageComboBox = new JComboBox<>();
            for(String languageCode : translator.getLanguageCodes()) {
                String language = languageCodeConverter.fromLanguageCode(languageCode);
                languageComboBox.addItem(language);
            }
            panel.add(languageComboBox);
        }

        @Override
        protected void addListener() {
            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        selection = languageComboBox.getSelectedItem().toString();
                    } else {
                        selection = null;
                    }

                    translationUI.updateTranslation();
                }
            });
        }
    }

    private static class TranslationUI {
        private final CountryList countryList;
        private final LanguageDropDown languageDropDown;

        private final Translator translator;

        private JPanel translationPanel;
        private JLabel translationText;

        public TranslationUI(Translator translatorIn) {
            translator = translatorIn;

            countryList = new CountryList(this);
            languageDropDown = new LanguageDropDown(this);

            makePanel();
        }

        public Translator getTranslator() {
            return translator;
        }

        public JPanel getTranslationPanel() {
            return translationPanel;
        }

        public JPanel getLanguagePanel() {
            return languageDropDown.getPanel();
        }

        public JPanel getCountryPanel() {
            return countryList.getPanel();
        }

        public void updateTranslation() {
            if (countryList.isSelected() && languageDropDown.isSelected()) {
                String country = countryList.getSelected();
                String countryCode = countryList.getCountryCodeConverter().fromCountry(country);

                String language = languageDropDown.getSelected();
                String languageCode = languageDropDown.getLanguageCodeConverter().fromLanguage(language);

                String translation = translator.translate(countryCode, languageCode);
                translationText.setText(translation);
            }
        }

        private void makePanel() {
            translationPanel = new JPanel();
            translationPanel.add(new JLabel("Translation:"));
            translationText = new JLabel("\t\t\t\t\t\t\t");
            translationPanel.add(translationText);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Translator translator = new JSONTranslator();

            TranslationUI translationUI = new TranslationUI(translator);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(translationUI.getLanguagePanel());
            mainPanel.add(translationUI.getTranslationPanel());
            mainPanel.add(translationUI.getCountryPanel());

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
