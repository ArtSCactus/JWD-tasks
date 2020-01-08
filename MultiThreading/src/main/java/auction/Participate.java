package auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Lot;
import entity.Observer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Participate implements Observer<Lot>, Runnable {
    @JsonIgnore
    private static final Logger LOGGER = LogManager.getLogger(Participate.class);
    @JsonProperty("ID")
    private int id;
    @JsonProperty("Capital")
    private double capital;
    @JsonIgnore // connects during working time
    private Auction auction;
    @JsonIgnore
    private Lot currentLot;
    @JsonIgnore
    private boolean isPreferred; // does current participate need a current lot or not
    @JsonIgnore
    private boolean isPausedToUpdate = false;
    @JsonProperty("Preferred lots")
    private List<String> preferredLots;
    @JsonIgnore
    private ReentrantLock locker = new ReentrantLock();
    @JsonIgnore
    private Semaphore threadControl = new Semaphore(1);

    public Participate(int id, int capital, Auction auction, List<String> preferredLots) {
        this.id = id;
        this.capital = capital;
        this.auction = auction;
        this.preferredLots = preferredLots;
    }

    private Participate() {
        preferredLots = new ArrayList<>();
        auction = Auction.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    private void makeBet() throws InterruptedException {
        if (auction.currentLot().getPrice() <= capital & isThisLotPreferred(auction.currentLot())) {
            auction.acceptBet(this);
            LOGGER.debug("Participate with id: " + id + " made a bet.");
        }
    }

    @Override
    public void run() {
        while (!auction.isClosed()) {
            if (auction.getLastBetParticipate() == null || !auction.getLastBetParticipate().equals(this)) {
                if (isPreferred) {
                    try {
                        makeBet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        LOGGER.info("Auction is closed. Closing participate.");
    }

    private boolean isThisLotPreferred(Lot lot) {
        for (String currentName : preferredLots) {
            if (lot.getName().equals(currentName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Lot lot) {
        threadControl.drainPermits();
        isPreferred = isThisLotPreferred(lot);
        LOGGER.info("Participate ID:" + id + " successfully updated");
        currentLot = lot;
        threadControl.release();
    }

    public static class Builder {
        private Participate newParticipate;

        public Builder() {
            newParticipate = new Participate();
        }

        public Builder withID(int id) {
            newParticipate.id = id;
            return this;
        }

        public Builder withCapital(int capital) {
            newParticipate.capital = capital;
            return this;
        }

        public Builder withAuction(Auction auction) {
            newParticipate.auction = auction;
            return this;
        }

        public Builder addPreferredLotName(String name) {
            newParticipate.preferredLots.add(name);
            return this;
        }

        public Builder withPreferredLots(List<String> lotNames) {
            newParticipate.preferredLots = lotNames;
            return this;
        }

        public Participate build() {
            return newParticipate;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participate)) return false;
        Participate that = (Participate) o;
        return getId() == that.getId() &&
                Double.compare(that.capital, capital) == 0 &&
                Objects.equals(auction, that.auction) &&
                Objects.equals(currentLot, that.currentLot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), capital, auction, currentLot);
    }

    /**
     * Not including currentLot because it's temporary variable.
     *
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "Participate{" +
                "id=" + id +
                ", capital=" + capital +
                ", auction=" + auction +
                '}';
    }
}
