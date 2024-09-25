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
    public void fromCountryCodeAfghanistan() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("Afghanistan", converter.fromCountryCode("Afg"));
    }

    @Test
    public void fromCountryCodeÅlandIslands() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("Åland Islands", converter.fromCountryCode("ALa"));
    }

    @Test
    public void fromCountryNewZealand() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("NZL", converter.fromCountry("New Zealand"));
    }

    @Test
    public void fromCountryCodeAllLoaded() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals(249, converter.getNumCountries());
    }
}