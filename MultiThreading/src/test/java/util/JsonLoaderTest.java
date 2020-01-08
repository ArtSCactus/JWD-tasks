package util;

import auction.Participate;
import entity.Lot;
import entity.Lots;
import entity.Participates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class JsonLoaderTest {
    private static final String PARTICIPATES_TEST_FILE_PATH = "src/test/resources/test participates.json";
    private static final String LOTS_TEST_FILE_PATH = "src/test/resources/test lots.json";
    private Lots referenceLots;
    private Participates referenceParticipates;
    private final JsonLoader loader = new JsonLoader();

    @Before
    public void init() {
        referenceLots = new Lots();
        referenceParticipates = new Participates();
        Lot firstLot = new Lot("Washington's revolver", 1, 4);
        Lot secondLot = new Lot("Prisoners at the front (Winslow Homer)", 2, 3);
        referenceLots.addAll(firstLot, secondLot);
        Participate participateOne = new Participate.Builder()
                .withID(1)
                .withCapital(5)
                .addPreferredLotName("Washington's revolver")
                .build();
        Participate participateTwo = new Participate.Builder()
                .withID(2)
                .withCapital(7)
                .addPreferredLotName("Washington's revolver")
                .addPreferredLotName("Prisoners at the front (Winslow Homer)")
                .build();
        referenceParticipates.addAll(participateOne, participateTwo);
    }

    @Test
    public void shouldSaveAndLoadParticipatesCorrect() throws IOException {
        loader.saveParticipates(PARTICIPATES_TEST_FILE_PATH, referenceParticipates);
        Participates methodResult = loader.loadParticipates(PARTICIPATES_TEST_FILE_PATH);
        new File(PARTICIPATES_TEST_FILE_PATH).deleteOnExit();
        Assert.assertEquals(methodResult, referenceParticipates);
    }

    @Test
    public void shouldSaveAndLoadLotsCorrect() throws IOException {
        loader.saveLots(LOTS_TEST_FILE_PATH, referenceLots);
        Lots methodResult = loader.loadLots(LOTS_TEST_FILE_PATH);
        new File(LOTS_TEST_FILE_PATH).deleteOnExit();
        Assert.assertEquals(methodResult, referenceLots);
    }
}