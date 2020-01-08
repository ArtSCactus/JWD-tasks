package com.epam.xml.parsers;

import com.epam.xml.entity.CallPrices;
import com.epam.xml.entity.Parameters;
import com.epam.xml.entity.Tariff;
import com.epam.xml.entity.TariffType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOMReaderTest {
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
        correctTariffs.add(new Tariff.Builder()
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
        correctTariffs.add(new Tariff.Builder()
                .withCallPrices(prices)
                .withParameters(parameters)
                .withName("Comfort")
                .withOperator("A1")
                .withPayroll(11).build());
    }

    @Test
    public void parseByStringPath() {
        Parser parser = new DOMReader();
        List<Tariff> parsedTariffs = parser.parse(TEST_FILE_PATH);
        Assert.assertEquals(correctTariffs, parsedTariffs);
    }

    @Test
    public void parseByFileObj() {
        Parser parser = new DOMReader();
        List<Tariff> parsedTariffs = parser.parse(new File(TEST_FILE_PATH));
        Assert.assertEquals(correctTariffs, parsedTariffs);
    }

    @Test
    public void shouldSkipInvalidElements(){
        Parser parser = new DOMReader();
        List<Tariff> parsedTariffs = parser.parse(new File(INVALID_ELEMENT_FILE_PATH));
        if (parsedTariffs.size()>1){
            System.out.println("The parser should not have taken all the elements.");
            Assert.fail();
        }
        Assert.assertEquals(correctTariffs.get(0), parsedTariffs.get(0));
    }
}