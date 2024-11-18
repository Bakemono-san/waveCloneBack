package org.odc.demo.Services.Interfaces;

public interface QRCodeService {
    byte[] generateQRCodeCard(String matricule);

    String generateQRCode(String matricule);
}
