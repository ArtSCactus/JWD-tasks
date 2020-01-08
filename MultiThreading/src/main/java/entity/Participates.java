package entity;

import auction.Auction;
import auction.Participate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Participates {
    @JsonProperty("Participates list")
    private List<Participate> participateList = new ArrayList<>();
    @JsonIgnore
    private Auction auction;

    public Participates(List<Participate> participateList, Auction auction) {
        this.participateList = participateList;
        this.auction = auction;
    }

    public Participates(){
    }

    public Participates(Auction auction) {
        this.auction = auction;
    }

    public List<Participate> getParticipateList() {
        return participateList;
    }

    public void setParticipateList(List<Participate> participateList) {
        this.participateList = participateList;
    }

    public void add(Participate participate) {
        participate.setAuction(auction);
        participateList.add(participate);
    }

    public Participate get(int index) {
        return participateList.get(index);
    }

    public List<Participate> getAll(){
        return participateList;
    }

    public void addAll(Participate ...participates){
        participateList.addAll(Arrays.asList(participates));
    }

    public void change(int index, Participate participate) {
        participateList.set(index, participate);
    }

    public void setAuction(Auction auction){
        this.auction= auction;
        for (Participate participate : participateList){
            participate.setAuction(auction);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participates)) return false;
        Participates that = (Participates) o;
        return Objects.equals(getParticipateList(), that.getParticipateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParticipateList());
    }

    @Override
    public String toString() {
        return "Participates{" +
                "participateList=" + participateList +
                '}';
    }
}
