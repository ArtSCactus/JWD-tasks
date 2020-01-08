package auction;

import entity.Lot;
import entity.Observable;
import entity.Observer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.Timer;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Auction implements Runnable, Observable<Observer<Participate>> {
    private static final Logger LOGGER = LogManager.getLogger(Auction.class);
    private static Auction instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static AtomicBoolean isClosed = new AtomicBoolean(true);
    private static final Lock LOCKER = new ReentrantLock();
    private Queue<Lot> lots;
    private List<Observer<Participate>> observers;
    private Lot sellingLot;
    private Timer timer = new Timer(1);
    private Thread timerThread = new Thread(timer);
    private Participate lastBetParticipate;
    private Map<Lot, Participate> soldLots;
    private Semaphore permissionToMakeBet = new Semaphore(1);

    private Auction() {
        lots = new ArrayDeque<>();
        observers = new ArrayList<>();
        soldLots = new HashMap<>();
    }

    public static Auction getInstance() {
        if (!isInitialized.get()) {
            try {
                LOCKER.lock();
                if (!isInitialized.get()) {
                    instance = new Auction();
                    isInitialized.set(true);
                }
            } finally {
                LOCKER.unlock();
            }
        }

        return instance;
    }

    public void addLot(Lot lot) {
        LOCKER.lock();
        lots.add(lot);
        LOCKER.unlock();
    }

    public Queue<Lot> getLots() {
        return lots;
    }

    public Map<Lot, Participate> getSoldLots() {
        return soldLots;
    }

    public Participate getLastBetParticipate() {
        return lastBetParticipate;
    }

    public boolean isClosed() {
        return isClosed.get();
    }

    public void acceptBet(Participate participate) throws InterruptedException {
        permissionToMakeBet.acquire(1);
        LOCKER.lock();
        sellingLot.increasePrice();
        lastBetParticipate = participate;
        LOCKER.unlock();
        permissionToMakeBet.release();
    }


    @Override
    public void run() {
        isClosed.set(false);
        //locker here to prevent access to auction while it's starting
        double currentPrice;
        LOGGER.info("Auction was opened");
        timerThread = new Thread(timer);
        timerThread.setDaemon(true);
        timerThread.start();
        while (!lots.isEmpty()) {
            permissionToMakeBet.drainPermits();
            // lock here to prevent access to null fields and auction during lot changing
            LOCKER.lock();
            lastBetParticipate = null;
            sellingLot = lots.poll();
            LOGGER.info("Current selling lot: " + sellingLot.toString());
            currentPrice = sellingLot.getPrice();
            notifyObservers();
            timer.reset();
            LOCKER.unlock();
            permissionToMakeBet.release();
            while (!timer.isExpired()) {
                // lock here to prevent price changing during observers notification and bet registration
                permissionToMakeBet.drainPermits();
                LOCKER.lock();
                if (currentPrice != sellingLot.getPrice()) {
                    LOGGER.debug("A lot price changing detected. Timer has been reset.");
                    timer.stop();
                    currentPrice = sellingLot.getPrice();
                    notifyObservers();
                    timer.reset();
                }
                LOCKER.unlock();
                permissionToMakeBet.release();
            }
            // lock here to prevent access to null fields and auction during winner registration
            permissionToMakeBet.drainPermits();
            LOCKER.lock();
            timer.stop();
            if (lastBetParticipate != null) {
                soldLots.put(sellingLot, lastBetParticipate);
                LOGGER.info("A winner is determined. Participate with ID:" + lastBetParticipate.toString()
                        + " won lot " + sellingLot.toString());
                lastBetParticipate = null;
            } else {
                LOGGER.info("On current lot wasn't made any bets. Winner is not determined.");
            }
            timer.reset();
            LOCKER.unlock();
            permissionToMakeBet.release();
        }
        close();
    }

    public Lot currentLot() {
        return sellingLot;
    }


    @Override
    public void attach(Observer<Participate> observer) {
        observers.add(observer);
    }


    @Override
    public void detach(Observer<Participate> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(sellingLot);
        }
        LOGGER.debug("Observers has been notified about price changing.");
    }

    public void close() {
        isClosed.set(true);
        timer.kill();
        LOGGER.debug("An auction thread was closed.");
    }

}
