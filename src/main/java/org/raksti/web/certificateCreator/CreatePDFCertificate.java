package org.raksti.web.certificateCreator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class CreatePDFCertificate {

    Logger logger = LoggerFactory.getLogger(CreatePDFCertificate.class);

    public File createCertificate(String fullName, UUID id) throws IOException {
        String PDF_PATH = "src/main/resources/resources/pdfCertificates/original/Diktats_apliecinajums_22_rgb.pdf";
        PDDocument pdfFile = PDDocument.load(new FileInputStream(PDF_PATH));
        PDPage page = pdfFile.getPage(0);

        PDAcroForm acroForm = new PDAcroForm(pdfFile);
        pdfFile.getDocumentCatalog().setAcroForm(acroForm);

        PDPageContentStream contentStream = new PDPageContentStream(pdfFile, page, PDPageContentStream.AppendMode.APPEND, false);
        PDFont formFont = PDType0Font.load(pdfFile, new FileInputStream("src/main/resources/resources/fonts/Effra Bold.ttf"), false);
        float titleWidth = formFont.getStringWidth(fullName) / 1000 * 25;

        contentStream.beginText();
        contentStream.setFont(formFont, 25 );
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, 409 );
        contentStream.showText(fullName);
        contentStream.endText();

        contentStream.close();
        File file = new File("home/ubuntu/user_certificates/Diktats_apliecinajums_" + id + ".pdf");
        pdfFile.save(file);

        return file;
    }



}
