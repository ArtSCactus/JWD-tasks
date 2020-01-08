package com.epam.xml.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tariffs")
public class Tariffs {
    @XmlElement(name = "tariff")
    private ArrayList<Tariff> tariffs = new ArrayList<>();

    public Tariffs() {
        super();
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(ArrayList<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public void add(Tariff e) {
        tariffs.add(e);
    }

    @Override
    public String toString() {
        return "Tariffs{" +
                "tariffs=" + tariffs +
                '}';
    }
}
