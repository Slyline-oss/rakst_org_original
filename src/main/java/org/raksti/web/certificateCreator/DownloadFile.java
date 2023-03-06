package org.raksti.web.certificateCreator;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;

import java.io.*;
import java.util.UUID;

public class DownloadFile {

    private final CreatePDFCertificate createPDFCertificate;

    public DownloadFile(CreatePDFCertificate createPDFCertificate) {
        this.createPDFCertificate = createPDFCertificate;
    }

    public Anchor getLink(String fullName, UUID id) throws IOException {
        File file = createPDFCertificate.createCertificate(fullName, id);
        StreamResource stream =  new StreamResource(file.getName(), () -> getStream(file));

        return new Anchor(stream, String.format("%s (%d KB)", file.getName(),
                (int) file.length() / 1024));
    }

    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
