package org.odc.demo.Services.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.odc.demo.Services.Interfaces.QRCodeService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QRCodeCardServiceImpl implements QRCodeService {

    @Override
    public byte[] generateQRCodeCard(String matricule) {
        try {
            // QR Code generation
            String qrCodePath = generateQRCode(matricule);

            // Load the QR code image
            BufferedImage qrCodeImage = ImageIO.read(new File(qrCodePath));

            // Card dimensions (adjust as needed)
            int cardWidth = 500;
            int cardHeight = 700;

            // Create a blank card image
            BufferedImage cardImage = new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = cardImage.createGraphics();

            // Set background color (e.g., a light blue gradient)
            GradientPaint gradient = new GradientPaint(0, 0, new Color(0xBBDEFB), 0, cardHeight, new Color(0xE3F2FD));
            graphics.setPaint(gradient);
            graphics.fillRect(0, 0, cardWidth, cardHeight);

            // Draw a rounded rectangle border for the card
            int borderSize = 20;
            int arcSize = 40;
            graphics.setColor(new Color(0x1976D2));
            graphics.setStroke(new BasicStroke(10));
            graphics.draw(new RoundRectangle2D.Double(borderSize, borderSize, cardWidth - (borderSize * 2), cardHeight - (borderSize * 2), arcSize, arcSize));

            // Place the QR code in the center of the card
            int qrCodeWidth = qrCodeImage.getWidth();
            int qrCodeHeight = qrCodeImage.getHeight();
            int centerX = (cardWidth - qrCodeWidth) / 2;
            int centerY = (cardHeight - qrCodeHeight) / 2;
            graphics.drawImage(qrCodeImage, centerX, centerY, null);

            // Add a title or label (optional)
            graphics.setColor(new Color(0x1976D2));
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            String title = "Your QR Code";
            int titleWidth = graphics.getFontMetrics().stringWidth(title);
            graphics.drawString(title, (cardWidth - titleWidth) / 2, 50);

            // Finalize drawing
            graphics.dispose();

            // Convert the card image to byte array for further use
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(cardImage, "PNG", byteArrayOutputStream);

            // Return the card image as a byte array
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code card", e);
        }
    }

    // Existing method for generating QR code
    public String generateQRCode(String matricule) {
        try {
            String directoryPath = "qr_codes/";
            String filePath = directoryPath + matricule + ".png";

            // Ensure the directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it doesn't exist
            }

            // Generate QR code
            BitMatrix matrix = new MultiFormatWriter().encode(matricule, BarcodeFormat.QR_CODE, 300, 300);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            return filePath;  // Return the file path to the generated QR code
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }
}