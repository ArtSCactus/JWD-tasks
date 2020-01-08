package reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Reader {
    private static final Logger LOGGER = LogManager.getLogger(Reader.class);
    public static String readText(String path){
        List<String> rows;
        try {
            rows =  Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            LOGGER.fatal("File with source text not found or may not be available.");
            throw new RuntimeException("File with source text not found or may not be available.");
        }
        StringBuilder builder = new StringBuilder();
        for (String row : rows){
            builder.append(row);
            builder.append("\n");
        }

        return builder.toString();
    }
}
