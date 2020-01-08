package util;

import auction.Auction;
import auction.Participate;
import entity.Lot;
import entity.Lots;
import entity.Observer;
import entity.Participates;

import java.util.List;

public class Configurator {
    /**
     * Attaches all participates to auction.
     *
     * @param auction      Current auction
     * @param participates Participate objects storage
     */
    public void configureAuction(Auction auction, Participates participates) {
        List<Participate> participatesList = participates.getAll();
        for (Observer participate : participatesList) {
            auction.attach(participate);
        }
    }

    /**
     * Connects given auction object to all participates.
     *
     * @param participates Participate objects storage
     * @param auction      Current auction
     */
    public void configureParticipates(Participates participates, Auction auction) {
        for (Participate participate : participates.getAll()) {
            participate.setAuction(auction);
        }
    }

    /**
     * Loads all lots from Lots object to auction.
     *
     * @param auction current auction
     * @param lots    Lots object
     */
    public void loadLotsToAuction(Auction auction, Lots lots) {
        for (Lot lot : lots.getAll()) {
            auction.addLot(lot);
        }
    }
}
