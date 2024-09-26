package org.translation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LanguageCodeConverterTest {

    @Test
    public void fromLanguageCodeEN() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("English", converter.fromLanguageCode("en"));
    }

    @Test
    public void fromLanguageCodeAB() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("Abkhazian", converter.fromLanguageCode("Ab"));
    }

    @Test
    public void fromLanguageCodeZU() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("Zulu", converter.fromLanguageCode("zU"));
    }

    @Test
    public void fromLanguageUkrainian() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("uk", converter.fromLanguage("Ukrainian"));
    }

    @Test
    public void fromLanguageCodeAllLoaded() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals(184, converter.getNumLanguages());
    }
}