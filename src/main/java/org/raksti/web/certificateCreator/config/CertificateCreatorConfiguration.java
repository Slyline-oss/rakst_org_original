package org.raksti.web.certificateCreator.config;

import org.raksti.web.certificateCreator.CreatePDFCertificate;
import org.raksti.web.certificateCreator.DownloadFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CertificateCreatorConfiguration {


    @Bean
    CreatePDFCertificate createPDFCertificate() {
        return new CreatePDFCertificate();
    }

    @Bean
    DownloadFile downloadFile(CreatePDFCertificate createPDFCertificate) {
        return new DownloadFile(createPDFCertificate);
    }
}
