package director;

import auction.Auction;
import auction.Participate;
import entity.Lot;
import entity.Lots;
import entity.Observer;
import entity.Participates;
import util.Configurator;
import util.JsonLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static JsonLoader jsonLoader = new JsonLoader();
    private static final String PARTICIPATES_JSON_PATH = "data/participates.json";
    private static final String LOTS_JSON_PATH = "data/lots.json";

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        Auction auction = Auction.getInstance();
        Configurator configurator = new Configurator();

        Lots lots = jsonLoader.loadLots(LOTS_JSON_PATH);
        configurator.loadLotsToAuction(auction, lots);
        Participates participates = jsonLoader.loadParticipates(PARTICIPATES_JSON_PATH);
        configurator.configureAuction(auction, participates);
        configurator.configureParticipates(participates, auction);

        List<Future> futureList = new ArrayList<>();
        futureList.add(executorService.submit(auction));
        futureList.add(executorService.submit(participates.get(0)));
        futureList.add(executorService.submit(participates.get(1)));
        for (Future future : futureList) {
            future.get();
        }
        executorService.shutdown();
        Map<Lot, Participate> soldLots = auction.getSoldLots();
        System.out.println("Sold lots: " + soldLots.toString());
    }
}
