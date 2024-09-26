package org.translation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JSONTranslatorTest {

    JSONTranslator jsonTranslator = new JSONTranslator();

    @Test
    public void getCountryLanguagesCAN() {
       List<String> countryLanguages = jsonTranslator.getCountryLanguages("can");
       assertEquals("There should be 35 languages but got " + countryLanguages.size(), 35, countryLanguages.size());
    }

    @Test
    public void getCountryLanguagesAFG() {
        List<String> countryLanguages = jsonTranslator.getCountryLanguages("afg");
        assertEquals("There should be 35 languages but got " + countryLanguages.size(), 35, countryLanguages.size());
    }

    @Test
    public void getCountries() {
        List<String> languages = jsonTranslator.getCountries();
        assertEquals("There should be 193 countries but got " + languages.size(), 193, languages.size());

    }

    @Test
    public void translateCAN_EN() {
        assertEquals("Canada", jsonTranslator.translate("can", "en"));
    }

    @Test
    public void translateAFG_PT() {
        assertEquals("Afeganistão", jsonTranslator.translate("afg", "pt"));
    }

    @Test
    public void translateZWE_AR() {
        assertEquals("زيمبابوي", jsonTranslator.translate("zwe", "ar"));
    }
}
