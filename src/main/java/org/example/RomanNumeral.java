package org.example;

import java.util.Map;

/**
 * Represents a roman numeral
 */
public class RomanNumeral {
    public final static Map<Integer, String> NUM_TO_ROMAN_SYMBOL = Map.of(
            1, "I",
            5, "V",
            10, "X",
            50, "L",
            100, "C",
            500, "D",
            1000, "M"
    );
    public final int LOWER_BOUND = 1;
    public final int UPPER_BOUND = 3999;

    static class ValueOutOfBoundsException extends RuntimeException {
        ValueOutOfBoundsException(String message) {
            super(message);
        }
    }

    final int value;
    final String romanNumeral;

    RomanNumeral(int value) throws ValueOutOfBoundsException {
        if(value < LOWER_BOUND || value > UPPER_BOUND) {
            throw new ValueOutOfBoundsException("Value: " + value + " is out of range");
        }

        this.value = value;

        // Build roman numeral string for every digit in the value
        StringBuilder romanNumeralBuilder = new StringBuilder();
        int unit = 1;
        int digit;
        while (value != 0) {
            digit = value % 10;
            String unitSymbol = NUM_TO_ROMAN_SYMBOL.get(unit);
            String midSymbol = NUM_TO_ROMAN_SYMBOL.get(5 * unit);
            String maxSymbol = NUM_TO_ROMAN_SYMBOL.get(10 * unit);

            String digitRomanNumeral = getRomanNumeralFromDigit(digit, unitSymbol, midSymbol, maxSymbol);
            romanNumeralBuilder.insert(0, digitRomanNumeral);

            value = value / 10;
            unit = unit * 10;
        }

        this.romanNumeral = romanNumeralBuilder.toString();
    }

    /**
     * Builds Roman numeral for the digit
     * @param digit - value of the digit. must be 0-9
     * @param unitSymbol - roman symbol representing the unit-value. eg: I, X
     * @param midSymbol - roman symbol representing the 5 * unit-value. eg: V, L
     * @param maxSymbol - roman symbol representing the 10 * unit-value. eg: X, C
     * @return Roman numeral string
     */
    private String getRomanNumeralFromDigit(int digit, String unitSymbol, String midSymbol, String maxSymbol) {
        switch (digit) {
            case 0:
                return "";
            case 1:
                return unitSymbol;
            case 2:
                return unitSymbol + unitSymbol;
            case 3:
                return unitSymbol + unitSymbol + unitSymbol;
            case 4:
                return unitSymbol + midSymbol;
            case 5:
                return midSymbol;
            case 6:
                return midSymbol + unitSymbol;
            case 7:
                return midSymbol + unitSymbol + unitSymbol;
            case 8:
                return midSymbol + unitSymbol + unitSymbol + unitSymbol;
            case 9:
                return unitSymbol + maxSymbol;
        }
        // Should never reach here
        return "";
    }
}
