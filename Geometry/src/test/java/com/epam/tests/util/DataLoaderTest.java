package com.epam.tests.util;

import com.epam.geometry.exception.FileAccessException;
import com.epam.geometry.idgenerator.IdGenerator;
import com.epam.geometry.util.DataLoader;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoaderTest {
    @Test
    public void shouldSuccessfullyReadFile() throws FileAccessException {
        List<String> correctValue = new ArrayList<String>();
        correctValue.add("1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3 1 2 3");
        correctValue.add("3 4 5 3 4 5 3 4 5 3 4 5 3 4 5 3 4 5 3 4 5 3 4 5");
        DataLoader testLoader = new DataLoader();
        String testFilePath = "src/test/resources/test.txt";
        List<String> result= testLoader.readFile(testFilePath);
        Assert.assertEquals(correctValue, result);
    }
    @Test
    public void shouldSkipInvalidRow() throws FileAccessException {
        List<String> correctValue = new ArrayList<String>();
        correctValue.add("1.3 2.3 3.3 1.3 2.3 3.3 1.3 2.3 3.3 1.3 2.3 3.3 1.3 " +
                "2.3 3.3 1.3 2.3 3.3 1.3 2.3 3.3 1.3 2.3 3.3");
        correctValue.add("3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 " +
                "4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0 3.0 4.0 5.0");
        DataLoader testLoader = new DataLoader();
        String testFilePath = "src/test/resources/errorTest.txt";
        List<String> result= testLoader.readFile(testFilePath);
        Assert.assertEquals(correctValue, result);
    }
    @Test(expected = FileAccessException.class)
    public void shouldThrowFileAccessException() throws FileAccessException {
        DataLoader testObject = new DataLoader();
        testObject.readFile("src/test/resources/some abstract file name.txt");
    }

    @AfterClass
    public static void endTest() {
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }
}
