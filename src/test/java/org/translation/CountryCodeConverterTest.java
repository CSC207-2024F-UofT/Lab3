package org.translation;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountryCodeConverterTest {

    @Test
    public void fromCountryCodeUSA() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("United States of America (the)", converter.fromCountryCode("usa"));
    }

    @Test
    public void fromCountryCodeAllLoaded() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals(249, converter.getNumCountries());
    }

    @Test
    public void fromNameToCode() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("840", converter.fromCountry("United States of America (the)"));
    }

    @Test
    public void specialCharacterInName() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("Côte d'Ivoire", converter.fromCountryCode("CIV"));
    }
}