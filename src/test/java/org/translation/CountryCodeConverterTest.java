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
    public void fromCountryCodeCanada() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("Canada", converter.fromCountryCode("can"));
    }

    @Test
    public void fromCountry() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("CAN", converter.fromCountry("Canada"));
    }

    @Test
    public void fromCountryCodeAllLoaded() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals(249, converter.getNumCountries());
    }
}