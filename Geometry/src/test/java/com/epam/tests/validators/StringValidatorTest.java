package com.epam.tests.validators;

import com.epam.geometry.validators.StringValidator;
import org.junit.Assert;
import org.junit.Test;

public class StringValidatorTest {

    @Test
    public void shouldReturnTrueInt(){
        StringValidator validator = new StringValidator();
        String testRow = "1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3";
        Assert.assertTrue(validator.isRowValidToParse(testRow));
    }
    @Test
    public void shouldReturnTrueDouble(){
        StringValidator validator = new StringValidator();
        String testRow = "3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0";
        Assert.assertTrue(validator.isRowValidToParse(testRow));
    }

    @Test
    public void shouldReturnFalseWord(){
        StringValidator validator = new StringValidator();
        String testRow = "3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 very small bee 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0";
        Assert.assertFalse(validator.isRowValidToParse(testRow));
    }
    @Test
    public void shouldReturnFalseSymbol(){
        StringValidator validator = new StringValidator();
        String testRow = "1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4 1,5 2,3 3,4";
        Assert.assertFalse(validator.isRowValidToParse(testRow));
    }

}
