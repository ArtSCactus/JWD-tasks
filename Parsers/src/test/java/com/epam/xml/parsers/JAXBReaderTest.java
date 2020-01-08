package com.epam.xml.parsers;

import com.epam.xml.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JAXBReaderTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test case.xml";
    private static final String INVALID_ELEMENT_FILE_PATH = "src/test/resources/invalid element test.xml";
    private List<Tariff> correctTariffs;

    @Before
    public void init() {
        correctTariffs = new ArrayList<>();
        CallPrices prices = new CallPrices.Builder()
                .withInsideNWPayroll(1)
                .withOutsideNWPayroll(2)
                .withLandlinePayroll(0.5)
                .withSMSPayroll(1).build();
        Parameters parameters = new Parameters.Builder()
                .withType(TariffType.MINUTE)
                .withConnectionPrice(10)
                .addFavouriteNumber("+375447807615")
                .addFavouriteNumber("+375291112233")
                .build();
        correctTariffs.add( new Tariff.Builder()
                .withCallPrices(prices)
                .withParameters(parameters)
                .withName("Lemon X")
                .withOperator("A1")
                .withPayroll(15).build());
        prices = new CallPrices.Builder()
                .withInsideNWPayroll(2)
                .withOutsideNWPayroll(3)
                .withLandlinePayroll(0.7)
                .withSMSPayroll(1.5).build();
        parameters = new Parameters.Builder()
                .withType(TariffType.TWELVE_SEC)
                .withConnectionPrice(15)
                .build();
        correctTariffs.add( new Tariff.Builder()
                .withCallPrices(prices)
                .withParameters(parameters)
                .withName("Comfort")
                .withOperator("A1")
                .withPayroll(11).build());

    }
    @Test
    public void parseByStringPath() throws JAXBException, FileNotFoundException {
        Parser<Tariff> parser = new JAXBReader();
        List<Tariff> parsedTariffs = parser.parse(TEST_FILE_PATH);
        Assert.assertEquals(correctTariffs, parsedTariffs);
    }

    @Test
    public void parseByFileObj() throws JAXBException, FileNotFoundException {
        Parser<Tariff> parser = new JAXBReader();
        List<Tariff> parsedTariffs = parser.parse(new File(TEST_FILE_PATH));
        Assert.assertEquals(correctTariffs, parsedTariffs);
    }

    // Here should be test on incorrect values, but in JAXB i think validation should be going through annotations
    // and validation-api. I just hadn't time to learn and write it.

}