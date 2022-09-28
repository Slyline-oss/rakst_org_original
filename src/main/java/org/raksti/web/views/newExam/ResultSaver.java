package org.raksti.web.views.newExam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ResultSaver {

    private final static Logger logger = LoggerFactory.getLogger(ResultSaver.class);

    public void saveResultIntoFile(String fileName, String data) {
        try {
            File result = new File("src/main/resources/results/" + fileName + ".txt");
            if (result.createNewFile()) {
                logger.info("Created file with id " + fileName);
                FileWriter fileWriter = new FileWriter(result);
                fileWriter.write(data);
                fileWriter.close();
                logger.info("Results successfully saved in file: " + fileName);
            } else logger.warn("File already exists (" + fileName + ")");

        } catch (IOException e) {
            logger.error("Error occurred while saving result into file");
            e.printStackTrace();
        }
    }
}
