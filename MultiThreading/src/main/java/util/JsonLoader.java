package util;

import auction.Participate;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Lots;
import entity.Participates;

import java.io.*;

public class JsonLoader {
    public void saveParticipates(String path, Participates participates) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        mapper.writeValue(file, participates);
    }

    public Participates loadParticipates(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Reader reader = new FileReader(path);
        JsonParser parser = new JsonFactory().createParser(reader);
        return mapper.readValue(parser, Participates.class);
    }

    public void saveLots(String path, Lots lots) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        mapper.writeValue(file, lots);
    }

    public Lots loadLots(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Reader reader = new FileReader(path);
        JsonParser parser = new JsonFactory().createParser(reader);
        return mapper.readValue(parser, Lots.class);
    }

}
