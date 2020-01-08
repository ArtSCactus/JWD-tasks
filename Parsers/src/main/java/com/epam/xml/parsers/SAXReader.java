package com.epam.xml.parsers;

import com.epam.xml.entity.CallPrices;
import com.epam.xml.entity.Parameters;
import com.epam.xml.entity.Tariff;
import com.epam.xml.entity.TariffType;
import com.epam.xml.validators.NodeValueValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXReader implements Parser<Tariff> {
    private static final Logger LOGGER = LogManager.getLogger(SAXReader.class);
    private static final double DEFAULT_VALUE = 1.0d;
    private static final TariffType DEFAULT_TARIFF_TYPE = TariffType.MINUTE;

    @Override
    public List<Tariff> parse(String path) {
        final List<Tariff> tariffList = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean tariffs = false;
                boolean tariff = false;
                boolean insideNetwork = false;
                boolean outsideNetwork = false;
                boolean callPricesStarted = false;
                boolean landLinePhone = false;
                boolean smsPrice = false;
                boolean parameters = false;
                boolean favouriteNumber = false;
                boolean type = false;
                boolean connectionPrice = false;
                boolean thisTariffElementIsCorrect = false;
                Tariff.Builder tariffBuilder;

                CallPrices.Builder pricesBuilder;
                Parameters.Builder parametersBuilder;

                private void leaveCurrentTariffAndResetBuilders(){
                    thisTariffElementIsCorrect=false;
                    tariff = false;
                    insideNetwork = false;
                    outsideNetwork = false;
                    callPricesStarted = false;
                    landLinePhone = false;
                    smsPrice = false;
                    parameters = false;
                    favouriteNumber = false;
                    type = false;
                    connectionPrice = false;
                    tariffBuilder = new Tariff.Builder();
                    pricesBuilder = new CallPrices.Builder();
                    parametersBuilder = new Parameters.Builder();
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("tariffs")) {
                        tariffs = true;
                    }
                    if (qName.equalsIgnoreCase("tariff")) {
                        tariff = true;
                        thisTariffElementIsCorrect =true;
                        tariffBuilder = new Tariff.Builder();
                    }
                    if (qName.equalsIgnoreCase("CallPrices")) {
                        callPricesStarted = true;
                        pricesBuilder = new CallPrices.Builder();
                    }
                    if (qName.equalsIgnoreCase("insideNetwork")) {
                        insideNetwork = true;
                    }
                    if (qName.equalsIgnoreCase("outsideNetwork")) {
                        outsideNetwork = true;
                    }
                    if (qName.equalsIgnoreCase("landline")) {
                        landLinePhone = true;
                    }
                    if (qName.equalsIgnoreCase("sms")) {
                        smsPrice = true;
                    }
                    if (qName.equalsIgnoreCase("Parameters")) {
                        parameters = true;
                        parametersBuilder = new Parameters.Builder();
                    }
                    if (qName.equalsIgnoreCase("FavouriteNumber")) {
                        favouriteNumber = true;
                    }
                    if (qName.equalsIgnoreCase("TariffType")) {
                        type = true;
                    }
                    if (qName.equalsIgnoreCase("ConnectionPrice")) {
                        connectionPrice = true;
                    }
                    for (int index = 0; index < attributes.getLength(); index++) {
                        String attrQName = attributes.getQName(index);
                        String attrValue = attributes.getValue(index);
                        if (attrQName.equalsIgnoreCase("name")) {
                            tariffBuilder = tariffBuilder.withName(attrValue);
                        }
                        if (attrQName.equalsIgnoreCase("operator")) {
                            tariffBuilder = tariffBuilder.withOperator(attrValue);
                        }
                        if (attrQName.equalsIgnoreCase("payroll")) {
                            tariffBuilder = tariffBuilder.withPayroll(Double.parseDouble(attrValue));
                        }
                    }

                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("tariffs")) {
                        tariffs = false;
                    }
                    if (qName.equalsIgnoreCase("tariff")) {
                        tariff = false;
                        if (thisTariffElementIsCorrect) {
                            tariffList.add(tariffBuilder
                                    .withCallPrices(pricesBuilder.build())
                                    .withParameters(parametersBuilder.build())
                                    .build());
                        }
                    }
                    if (qName.equalsIgnoreCase("CallPrices")) {
                        callPricesStarted = false;
                    }
                    if (qName.equalsIgnoreCase("InsideNetwork")) {
                        insideNetwork = false;
                    }
                    if (qName.equalsIgnoreCase("OutsideNetwork")) {
                        outsideNetwork = false;
                    }
                    if (qName.equalsIgnoreCase("landline")) {
                        landLinePhone = false;
                    }
                    if (qName.equalsIgnoreCase("SMS")) {
                        smsPrice = false;
                    }
                    if (qName.equalsIgnoreCase("Parameters")) {
                        parameters = false;
                    }
                    if (qName.equalsIgnoreCase("FavouriteNumber")) {
                        favouriteNumber = false;
                    }
                    if (qName.equalsIgnoreCase("TariffType")) {
                        type = false;
                    }
                    if (qName.equalsIgnoreCase("ConnectionPrice")) {
                        connectionPrice = false;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (insideNetwork & thisTariffElementIsCorrect) {
                        if (NodeValueValidator.isStringValidForDouble(new String(ch, start, length))) {
                            double payroll = Double.parseDouble(new String(ch, start, length));
                            pricesBuilder.withInsideNWPayroll(payroll);
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                            leaveCurrentTariffAndResetBuilders();
                        }
                    }
                    if (outsideNetwork) {
                        if (NodeValueValidator.isStringValidForDouble(new String(ch, start, length))) {
                            double payroll = Double.parseDouble(new String(ch, start, length));
                            pricesBuilder.withOutsideNWPayroll(payroll);
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                            leaveCurrentTariffAndResetBuilders();
                        }
                    }
                    if (landLinePhone) {
                        if (NodeValueValidator.isStringValidForDouble(new String(ch, start, length))) {
                            double payroll = Double.parseDouble(new String(ch, start, length));
                            pricesBuilder.withLandlinePayroll(payroll);
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                           leaveCurrentTariffAndResetBuilders();
                        }
                    }
                    if (smsPrice) {
                        if (NodeValueValidator.isStringValidForDouble(new String(ch, start, length))) {
                            double payroll = Double.parseDouble(new String(ch, start, length));
                            pricesBuilder.withSMSPayroll(payroll);
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                            leaveCurrentTariffAndResetBuilders();
                        }
                    }
                    if (favouriteNumber) {
                        parametersBuilder.addFavouriteNumber(new String(ch, start, length));
                    }
                    if (type) {
                        if (NodeValueValidator.validateEnumObj(new String(ch, start, length))) {
                            parametersBuilder.withType(TariffType.valueOf(new String(ch, start, length)));
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                            leaveCurrentTariffAndResetBuilders();
                        }
                    }
                    if (connectionPrice) {
                        if (NodeValueValidator.isStringValidForDouble(new String(ch, start, length))) {
                            double price = Double.parseDouble(new String(ch, start, length));
                            parametersBuilder.withConnectionPrice(price);
                        } else {
                            LOGGER.error("An incorrect node value was met." +
                                    " Skipping current tariff element. Invalid row: " +new String(ch, start, length));
                            leaveCurrentTariffAndResetBuilders();
                        }
                    }
                }

            };
            parser.parse(path, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return tariffList;
    }

    @Override
    public List<Tariff> parse(File xmlFile) {
        return parse(xmlFile.getPath());
    }
}
