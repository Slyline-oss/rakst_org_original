package org.raksti.web.certificateCreator;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.*;
import java.util.UUID;

public class DownloadFile {

    private final CreatePDFCertificate createPDFCertificate;

    public DownloadFile(CreatePDFCertificate createPDFCertificate) {
        this.createPDFCertificate = createPDFCertificate;
    }

    public Anchor getLink(String fullName, UUID id) throws IOException {


        StreamResource stream =  new StreamResource("Diktats_apliecinajums_22_rgb.pdf", new InputStreamFactory() {
            @Override
            public InputStream createInputStream() {
                ByteArrayOutputStream bos = null;
                try {
                    bos = createPDFCertificate.createCertificate(fullName, id);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return new ByteArrayInputStream(bos.toByteArray());
            }
        });

        return new Anchor(stream, "Diktats_apliecinajums_22_rgb.pdf");
    }

}
