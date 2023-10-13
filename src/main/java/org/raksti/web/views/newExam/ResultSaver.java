package org.raksti.web.views.newExam;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ResultSaver {
    private final static Logger logger = LoggerFactory.getLogger(ResultSaver.class);

    private final String directoryName;

    public ResultSaver(@NotNull @Value("${result.save.directory:/home/ubuntu/Documents}") String directoryName) {
        this.directoryName = directoryName;
    }

    public void saveResultIntoFile(String fileName, String data) {
        try {
            File result = new File(new File(directoryName), fileName + ".txt");
            FileUtils.writeStringToFile(result, data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Error occurred while saving result into file: " + e.getMessage());
        }
    }
}
