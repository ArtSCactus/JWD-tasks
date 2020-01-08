package com.epam.geometry.util;

import com.epam.geometry.exception.FileAccessException;
import com.epam.geometry.validators.StringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {

    private static final Logger LOGGER = LogManager.getLogger(DataLoader.class);

    public List<String> readFile(String filePath) throws FileAccessException {
        List<String> rows;
       try {
           FileReader fileStream = new FileReader(filePath);
           Scanner scanner = new Scanner(fileStream);
           rows = new ArrayList<>();
           StringValidator validator = new StringValidator();
           LOGGER.info("File stream successfully initiated. Starting reading");
           String currentRow;

           while (scanner.hasNextLine()) {
               currentRow = scanner.nextLine();
               if (validator.isRowValidToParse(currentRow)) {
                   currentRow.replaceAll("\\s{2,}", "");
                   rows.add(currentRow);
                   LOGGER.debug("Row passed the validation. Current row: " + currentRow);
               } else {
                   LOGGER.warn("An incorrect row was skipped: " + currentRow);
               }
           }
           LOGGER.info("File stream has successfully finished reading. Closing file stream.");
           fileStream.close();
       }catch (IOException e){
           throw new FileAccessException("File does not exist or is not accessible.", e.getCause());
       }
        return rows;
    }

    public List<String> readFile(File file) throws FileAccessException {
        List<String> rows;
        try {
           FileReader fileStream = new FileReader(file);
           Scanner scanner = new Scanner(fileStream);
            rows = new ArrayList<>();
           StringValidator validator = new StringValidator();
           String currentRow;
           LOGGER.info("File stream successfully initiated. Starting reading");

           while (scanner.hasNextLine()) {
               currentRow = scanner.nextLine();
               if (validator.isRowValidToParse(currentRow)) {
                   rows.add(currentRow);
                   LOGGER.debug("Row passed the validation. Current row: " + currentRow);
               } else {
                   LOGGER.warn("An incorrect row was skipped: " + currentRow);
               }
           }
           LOGGER.info("File stream has successfully finished reading. Closing file stream.");
           fileStream.close();
       } catch(IOException e){
           throw new FileAccessException("File does not exist or is not accessible.", e.getCause());
       }
        return rows;
    }
}
