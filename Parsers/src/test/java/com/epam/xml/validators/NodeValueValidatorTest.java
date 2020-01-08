package com.epam.xml.validators;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.soap.Node;

public class NodeValueValidatorTest {
    private static final String CORRECT_DOUBLE_VALUE_FRACTIONAL = "0.3";
    private static final String CORRECT_DOUBLE_VALUE_INTEGER = "3";
    private static final String CORRECT_DOUBLE_VALUE_WITH_TYPE = "3d";
    private static final String INVALID_DOUBLE_VALUE_COMMA = "0,3";
    private static final String INVALID_DOUBLE_VALUE_SYMBOLS = "ABC";
    private static final String EXISTS_ENUM_VALUE = "MINUTE";
    private static final String NOT_EXISTS_ENUM_VALUE = "ten";

    @Test
    public void shouldReturnTrueOnFractionalDoubleValue() {
        boolean methodResult = NodeValueValidator.isStringValidForDouble(CORRECT_DOUBLE_VALUE_FRACTIONAL);
        Assert.assertTrue(methodResult);
    }

    @Test
    public void shouldReturnTrueOnIntegerTypeDoubleValue(){
        boolean methodResult = NodeValueValidator.isStringValidForDouble(CORRECT_DOUBLE_VALUE_INTEGER);
        Assert.assertTrue(methodResult);
    }

    @Test
    public void shouldReturnTrueOnDoubleValueWithType(){
        boolean methodResult = NodeValueValidator.isStringValidForDouble(CORRECT_DOUBLE_VALUE_WITH_TYPE);
        Assert.assertTrue(methodResult);
    }

    @Test
    public void shouldReturnFalseOnDoubleValueWithComma(){
        boolean methodResult = NodeValueValidator.isStringValidForDouble(INVALID_DOUBLE_VALUE_COMMA);
        Assert.assertFalse(methodResult);
    }

    @Test
    public void shouldReturnFalseOnDoubleValueWithSymbols(){
        boolean methodResult = NodeValueValidator.isStringValidForDouble(INVALID_DOUBLE_VALUE_SYMBOLS);
        Assert.assertFalse(methodResult);
    }

    @Test
    public void shouldReturnTrueOnExistsEnumObj() {
        boolean methodResult = NodeValueValidator.validateEnumObj(EXISTS_ENUM_VALUE);
        Assert.assertTrue(methodResult);
    }

    @Test
    public void shouldReturnFalseOnNotExistsEnumObj(){
        boolean methodResult = NodeValueValidator.validateEnumObj(NOT_EXISTS_ENUM_VALUE);
        Assert.assertFalse(methodResult);
    }

}