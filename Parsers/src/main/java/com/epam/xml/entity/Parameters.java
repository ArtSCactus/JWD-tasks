package com.epam.xml.entity;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Parameters", propOrder = {
        "favouriteNumber",
        "tariffType",
        "connectionPrice"
})
public class Parameters {
    @XmlElement()
    private List<String> favouriteNumber;
    @XmlElement(name = "tariffType", required = true)
    private TariffType tariffType;
    @XmlElement(required = true)
    private double connectionPrice;

    public Parameters(TariffType tariffType, double connectionPrice) {
        this.tariffType = tariffType;
        this.connectionPrice = connectionPrice;
        favouriteNumber = new ArrayList<>();
    }

    public Parameters(List<String> favouriteNumber, TariffType tariffType, double connectionPrice) {
        this.favouriteNumber = favouriteNumber;
        this.tariffType = tariffType;
        this.connectionPrice = connectionPrice;
    }

    public Parameters() {
        favouriteNumber = new ArrayList<>();
    }

    public List<String> getFavouriteNumber() {
        return favouriteNumber;
    }


    public TariffType getTariffType() {
        return tariffType;
    }

    public double getConnectionPrice() {
        return connectionPrice;
    }


    public void addFavouriteNumber(String number) {
        favouriteNumber.add(number);
    }

    public void removeFavouriteNumber(int index) {
        favouriteNumber.remove(index);
    }

    public void removeFavouriteNumber(String number) {
        favouriteNumber.remove(number);
    }

    public static class Builder {
        private Parameters newParameters;

        public Builder() {
            newParameters = new Parameters();
        }

        public Builder withFavouriteNumbers(List<String> numbers) {
            newParameters.favouriteNumber = numbers;
            return this;
        }

        public Builder addFavouriteNumber(String number) {
            newParameters.addFavouriteNumber(number);
            return this;
        }

        public Builder withConnectionPrice(double price) {
            newParameters.connectionPrice = price;
            return this;
        }

        public Builder withType(TariffType type) {
            newParameters.tariffType = type;
            return this;
        }

        public Parameters build() {
            return newParameters;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameters)) return false;
        Parameters that = (Parameters) o;
        return Double.compare(that.getConnectionPrice(), getConnectionPrice()) == 0 &&
                getFavouriteNumber().equals(that.getFavouriteNumber()) &&
                getTariffType() == that.getTariffType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFavouriteNumber(), getTariffType(), getConnectionPrice());
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "favouriteNumbers=" + favouriteNumber +
                ", type=" + tariffType +
                ", connectionPrice=" + connectionPrice +
                '}';
    }
}
