package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(false); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            //add json translator
            Translator jsonTranslator = new JSONTranslator(); 
            List<String> languageCodes = jsonTranslator.getLanguageCodes(); 
            List<String> languageNames = new ArrayList<>();
            Map<String, String> languageMap = new LinkedHashMap<>();
            languageMap.put("ar", "Arabic");
            languageMap.put("bg", "Bulgarian");
            languageMap.put("cs", "Czech");
            languageMap.put("da", "Danish");
            languageMap.put("de", "German");
            languageMap.put("el", "Greek");
            languageMap.put("en", "English");
            languageMap.put("eo", "Esperanto");
            languageMap.put("es", "Spanish");
            languageMap.put("et", "Estonian");
            languageMap.put("eu", "Basque");
            languageMap.put("fa", "Persian");
            languageMap.put("fi", "Finnish");
            languageMap.put("fr", "French");
            languageMap.put("hr", "Croatian");
            languageMap.put("hu", "Hungarian");
            languageMap.put("hy", "Armenian");
            languageMap.put("it", "Italian");
            languageMap.put("ja", "Japanese");
            languageMap.put("ko", "Korean");
            languageMap.put("lt", "Lithuanian");
            languageMap.put("nl", "Dutch");
            languageMap.put("no", "Norwegian");
            languageMap.put("pl", "Polish");
            languageMap.put("pt", "Portuguese");
            languageMap.put("ro", "Romanian");
            languageMap.put("ru", "Russian");
            languageMap.put("sk", "Slovak");
            languageMap.put("sl", "Slovenian");
            languageMap.put("sr", "Serbian");
            languageMap.put("sv", "Swedish");
            languageMap.put("th", "Thai");
            languageMap.put("uk", "Ukrainian");
            languageMap.put("zh", "Chinese (Simplified)");
            languageMap.put("zh-tw", "Chinese (Traditional)");
            for (String languageCode : languageCodes) {
                languageNames.add(languageMap.get(languageCode));
            }
            String[] languagesArray = languageNames.toArray(new String[0]); 
            JComboBox<String> languageComboBox = new JComboBox<>(languagesArray); 

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString();
                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new CanadaTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

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
