package com.epam.xml.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tariffType")
@XmlEnum
public enum TariffType {
    @XmlEnumValue("TWELVE_SEC") TWELVE_SEC("TWELVE_SEC"),
    @XmlEnumValue("MINUTE") MINUTE("MINUTE");

    TariffType(String value) {
        this.value = value;
    }

    String value;

    public String value() {
        return value;
    }

    public static TariffType fromValue(String v) {
        for (TariffType temp : TariffType.values()) {
            if (temp.value.equals(v)) {
                return temp;
            }
        }
        return null;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
