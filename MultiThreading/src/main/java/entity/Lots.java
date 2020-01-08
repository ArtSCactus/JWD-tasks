package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Lots {
    @JsonProperty("Lots list")
    private List<Lot> lots;

    public Lots(List<Lot> lots) {
        this.lots = lots;
    }

    public Lots() {
        this.lots = new ArrayList<>();
    }

    public void add(Lot lot) {
        lots.add(lot);
    }

    public void remove(int index) {
        lots.remove(index);
    }

    public void remove(Lot lot) {
        lots.remove(lot);
    }

    public List<Lot> getAll(){
        return lots;
    }

    public void change(int index, Lot newValue) {
        lots.set(index, newValue);
    }

    public void addAll(Lot ... lot){
        lots.addAll(Arrays.asList(lot));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lots)) return false;
        Lots lots1 = (Lots) o;
        return Objects.equals(lots, lots1.lots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lots);
    }

    @Override
    public String toString() {
        return "Lots{" +
                "lots=" + lots +
                '}';
    }
}
