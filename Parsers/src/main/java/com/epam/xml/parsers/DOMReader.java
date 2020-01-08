package com.epam.xml.parsers;

import com.epam.xml.entity.CallPrices;
import com.epam.xml.entity.Parameters;
import com.epam.xml.entity.Tariff;
import com.epam.xml.entity.TariffType;
import com.epam.xml.validators.NodeValueValidator;
import com.sun.java.browser.plugin2.DOM;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMReader implements Parser<Tariff> {
    private static final Logger LOGGER = LogManager.getLogger(DOMReader.class);
    private Tariff.Builder tariffBuilder;
    private CallPrices.Builder pricesBuilder;
    private Parameters.Builder parametersBuilder;

    @Override
    public List<Tariff> parse(String path) {
        List<Tariff> tariffObjList = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(path);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList tariffsNodeList = document.getElementsByTagName("tariffs");
        for (int tariffsNodeIndex = 0; tariffsNodeIndex < tariffsNodeList.getLength(); tariffsNodeIndex++) {
            Element currentTariffs = (Element) tariffsNodeList.item(tariffsNodeIndex);
            NodeList tariffNodesList = currentTariffs.getElementsByTagName("tariff");
            for (int tariffNodeIndex = 0; tariffNodeIndex < tariffNodesList.getLength(); tariffNodeIndex++) {
                Tariff parsedTariff = buildTariff((Element) tariffNodesList.item(tariffNodeIndex));
                if (parsedTariff == null){
                    LOGGER.error("Parsed tariff contains incorrect values. Cannot be added to list.");
                } else {
                    tariffObjList.add(parsedTariff);
                }
            }
        }

        return tariffObjList;
    }

    @Override
    public List<Tariff> parse(File xmlFile) {
        return parse(xmlFile.getPath());
    }

    private Tariff buildTariff(Element currentTariff) {
        pricesBuilder = new CallPrices.Builder();
        parametersBuilder = new Parameters.Builder();
        tariffBuilder = new Tariff.Builder();
        NamedNodeMap attributes = currentTariff.getAttributes();
        for (int attrIndex = 0; attrIndex < attributes.getLength(); attrIndex++) {
            Node currentAttribute = attributes.item(attrIndex);

            switch (attributes.item(attrIndex).getNodeName().toLowerCase()) { // lower case to take
                case ("name"):                                                 // off register dependency
                    tariffBuilder = tariffBuilder.withName(currentAttribute.getNodeValue());
                    break;
                case ("operator"):
                    tariffBuilder = tariffBuilder.withOperator(currentAttribute.getNodeValue());
                    break;
                case ("payroll"):

                    if (NodeValueValidator.isStringValidForDouble(currentAttribute.getNodeValue())){
                        tariffBuilder = tariffBuilder.withPayroll(Double.parseDouble(currentAttribute.getNodeValue()));
                    } else {
                        LOGGER.error("An incorrect value was met. Returning null. Invalid row: "+
                                currentAttribute.getNodeValue());
                        return null;
                    }
                    break;
            }
        }
        String textContent;
        Element prices = (Element) currentTariff.getElementsByTagName("callPrices").item(0);
        textContent = getElementTextContent(prices, "insideNetwork");
        if (NodeValueValidator.isStringValidForDouble(textContent)){
            double insideNWValue = parseDouble(getElementTextContent(prices, "insideNetwork"));
            pricesBuilder = pricesBuilder.withInsideNWPayroll(insideNWValue);
        } else {
            LOGGER.error("An incorrect value was met. Returning null. Invalid row: "+
            textContent);
            return null;
        }
        textContent = getElementTextContent(prices, "outsideNetwork");
        if (NodeValueValidator.isStringValidForDouble(textContent)){
            double outsideNWValue = parseDouble(getElementTextContent(prices, "outsideNetwork"));
            pricesBuilder = pricesBuilder.withOutsideNWPayroll(outsideNWValue);
        } else {
            LOGGER.error("An incorrect value was met. Returning null. Invalid row: "+
                    textContent);
            return null;
        }
        textContent = getElementTextContent(prices, "landLine");
        if (NodeValueValidator.isStringValidForDouble(textContent)){
            double landlineValue = parseDouble(getElementTextContent(prices, "landLine"));
            pricesBuilder = pricesBuilder.withLandlinePayroll(landlineValue);
        } else {
            LOGGER.error("An incorrect value was met. Returning null. Invalid row: "+
                    textContent);
            return null;
        }
        textContent = getElementTextContent(prices, "sms");
        if (NodeValueValidator.isStringValidForDouble(textContent)){
            double smsValue = parseDouble(getElementTextContent(prices, "sms"));
            pricesBuilder = pricesBuilder.withSMSPayroll(smsValue);
        } else {
            LOGGER.error("An incorrect value was met. Returning null. Invalid row: "+
                    textContent);
            return null;
        }

        Element parametersNode = (Element) currentTariff.getElementsByTagName("parameters").item(0);
        NodeList favouriteNumbers = parametersNode.getElementsByTagName("favouriteNumber");
        for (int index = 0; index < favouriteNumbers.getLength(); index++) {
            parametersBuilder = parametersBuilder.addFavouriteNumber(favouriteNumbers.item(index).getFirstChild().getNodeValue());
        }

        Node type = parametersNode.getElementsByTagName("tariffType").item(0);
        textContent = type.getFirstChild().getNodeValue();
        if (NodeValueValidator.validateEnumObj(textContent)){
            parametersBuilder = parametersBuilder.withType(TariffType.valueOf(type.getFirstChild().getNodeValue()));
        } else {
            LOGGER.error("An incorrect value as met. Returning null. Invalid row: "+
                    textContent);
            return null;
        }
        Node connectionPrice = parametersNode.getElementsByTagName("connectionPrice").item(0);
        textContent = connectionPrice.getFirstChild().getNodeValue();
        if (NodeValueValidator.isStringValidForDouble(textContent)){
            parametersBuilder = parametersBuilder.withConnectionPrice(parseDouble(
                    connectionPrice.getFirstChild().getNodeValue()));
        } else {
            LOGGER.error("An incorrect value as met. Returning null. Invalid row: "+
                    textContent);
            return null;
        }
        tariffBuilder = tariffBuilder.withCallPrices(pricesBuilder.build()).withParameters(parametersBuilder.build());
        return tariffBuilder.build();
    }

    private double parseDouble(String value) {
        return Double.parseDouble(value);
    }

    private String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }

}
