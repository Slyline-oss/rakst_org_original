package org.raksti.web.certificateCreator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

public class CreatePDFCertificate {

    Logger logger = LoggerFactory.getLogger(CreatePDFCertificate.class);

    public ByteArrayOutputStream createCertificate(String fullName, UUID id) throws IOException {
        try (InputStream pdfStream = CreatePDFCertificate.class.getResourceAsStream("/certificate_original/Diktats_apliecinajums_22_rgb.pdf");
        InputStream fontStream = CreatePDFCertificate.class.getResourceAsStream("/static/fonts/Effra_Bold.ttf")){

            PDDocument pdfFile = PDDocument.load(pdfStream);
            PDPage page = pdfFile.getPage(0);

            PDAcroForm acroForm = new PDAcroForm(pdfFile);
            pdfFile.getDocumentCatalog().setAcroForm(acroForm);

            PDPageContentStream contentStream = new PDPageContentStream(pdfFile, page, PDPageContentStream.AppendMode.APPEND, false);
            PDFont formFont = PDType0Font.load(pdfFile, fontStream, false);
            float titleWidth = formFont.getStringWidth(fullName) / 1000 * 25;

            contentStream.beginText();
            contentStream.setFont(formFont, 25 );
            contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, 409 );
            contentStream.showText(fullName);
            contentStream.endText();

            contentStream.close();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            pdfFile.save(bos);

            return bos;
        }

    }



}
