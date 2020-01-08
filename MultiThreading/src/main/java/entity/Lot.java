package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Lot {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("ID")
    private long id;
    @JsonProperty("Price")
    private double price;

    public Lot(String name, long id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    Lot() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increasePrice() {
        price += price * 0.05d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot)) return false;
        Lot lot = (Lot) o;
        return getId() == lot.getId() &&
                Double.compare(lot.getPrice(), getPrice()) == 0 &&
                Objects.equals(getName(), lot.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getPrice());
    }

    @Override
    public String toString() {
        return "Lot{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
