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
    public void fromLanguageCodeAllLoaded() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals(184, converter.getNumLanguages());
    }

    @Test
    public void fromCodeLanguageENGLISH() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("en", converter.fromLanguage("English"));
    }


}