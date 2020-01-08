package com.epam.xml.parsers;

import com.epam.xml.entity.Tariff;
import com.epam.xml.entity.Tariffs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JAXBReader implements Parser<Tariff> {
    @Override
    public List<Tariff> parse(String path) {
        Tariffs tariffs = new Tariffs();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Tariffs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            FileReader reader = new FileReader(path);
            tariffs = (Tariffs) unmarshaller.unmarshal(reader);
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        return tariffs.getTariffs();
    }

    @Override
    public List<Tariff> parse(File xmlFile) {
        return parse(xmlFile.getPath());
    }
}
