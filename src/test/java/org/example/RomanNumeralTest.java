package org.example;

import org.junit.Assert;
import org.junit.Test;

public class RomanNumeralTest {
    @Test
    public void romanNumeralValues() {
        // Assert Roman Numeral Values for digits
        assertRomanNumeral(1, "I");
        assertRomanNumeral(2, "II");
        assertRomanNumeral(3, "III");
        assertRomanNumeral(4, "IV");
        assertRomanNumeral(5, "V");
        assertRomanNumeral(6, "VI");
        assertRomanNumeral(7, "VII");
        assertRomanNumeral(8, "VIII");
        assertRomanNumeral(9, "IX");
        assertRomanNumeral(10, "X");

        // Assert Roman Numeral values for higher symbols
        assertRomanNumeral(50, "L");
        assertRomanNumeral(100, "C");

        // Assert Roman numeral values for random numbers
        assertRomanNumeral(27, "XXVII");
        assertRomanNumeral(49, "XLIX");
        assertRomanNumeral(123, "CXXIII");
        assertRomanNumeral(192, "CXCII");
    }

    @Test
    public void romanNumeralOutOfBound() {
        Assert.assertThrows(RomanNumeral.ValueOutOfBoundsException.class, () -> new RomanNumeral(0));
        Assert.assertThrows(RomanNumeral.ValueOutOfBoundsException.class, () -> new RomanNumeral(256));
    }

    void assertRomanNumeral(int val, String romanNumeral) {
        RomanNumeral rn = new RomanNumeral(val);
        Assert.assertEquals(rn.romanNumeral, romanNumeral);
    }
}
