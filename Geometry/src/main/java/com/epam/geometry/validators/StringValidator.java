package com.epam.geometry.validators;

public class StringValidator {
    public boolean isRowValidToParse(String row) {
        return row.matches("^(\\s*(\\d+\\.\\d+|\\d+)\\s*){24}$");
    }
}
