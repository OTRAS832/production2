//package com.OTRAS.DemoProject.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.format.DateTimeFormatter;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
//import org.springframework.stereotype.Service;
//
//import com.OTRAS.DemoProject.Entity.AdmitCardConfig;
//import com.OTRAS.DemoProject.Entity.CandidateProfile;
//import com.OTRAS.DemoProject.Entity.PaymentSuccesfullData;
//import com.OTRAS.DemoProject.Util.FileUploadUtility;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AdmitCardPdfService {
//
//    private final FileUploadUtility fileUploadUtility;
//
//    public String generateAdmitCardPdf(PaymentSuccesfullData app, AdmitCardConfig config, String centerDetails) throws IOException {
//
//        CandidateProfile candidate = app.getCandidateProfile();
//        String filePath = "admit_cards/" + app.getOtrId() + "_" + app.getRollNumber() + ".pdf";
//        new File("admit_cards").mkdirs();
//
//        try (PDDocument doc = new PDDocument()) {
//
//            PDPage page = new PDPage(PDRectangle.A4);
//            doc.addPage(page);
//
//            PDPageContentStream stream = new PDPageContentStream(doc, page);
//
//            float margin = 50f;
//            float y = page.getMediaBox().getHeight() - margin;
//
//            // Border
//            stream.setLineWidth(2);
//            stream.setStrokingColor(0, 0, 0.5f);
//            stream.addRect(margin, margin, page.getMediaBox().getWidth() - 2 * margin, page.getMediaBox().getHeight() - 2 * margin);
//            stream.stroke();
//
//            // Header
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
//            stream.setNonStrokingColor(0, 0, 0.5f);
//            stream.beginText();
//            stream.newLineAtOffset(200, y);
//            stream.showText("ADMIT CARD - " + (config.getExamDate() != null ? config.getExamDate() : "TBD"));
//            stream.endText();
//            y -= 40;
//
//            // Candidate Information Title
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
//            stream.beginText();
//            stream.newLineAtOffset(margin, y);
//            stream.showText("CANDIDATE INFORMATION");
//            stream.endText();
//            y -= 25;
//
//            String[][] candData = {
//                    {"OTR ID:", candidate.getOtrasId() != null ? candidate.getOtrasId() : "N/A"},
//                    {"Roll No:", app.getRollNumber() != null ? app.getRollNumber() : "N/A"},
//                    {"Name:", candidate.getCandidateName() != null ? candidate.getCandidateName() : "N/A"},
//                    {"Father's Name:", candidate.getFathersName() != null ? candidate.getFathersName() : "N/A"},
//                    {"DOB:", candidate.getDateOfBirth() != null ? candidate.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "N/A"},
//                    {"Gender:", candidate.getGender() != null ? candidate.getGender() : "N/A"}
//            };
//
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
//            for (String[] row : candData) {
//                stream.beginText();
//                stream.newLineAtOffset(margin + 10, y);
//                stream.showText(row[0] + " " + row[1]);
//                stream.endText();
//                y -= 18;
//            }
//
//            y -= 10;
//
//            // Candidate Photo (optional)
//            if (app.getLivePhoto() != null && !app.getLivePhoto().isEmpty()) {
//                try {
//                    PDImageXObject photo = PDImageXObject.createFromFile(app.getLivePhoto(), doc);
//                    stream.drawImage(photo, page.getMediaBox().getWidth() - margin - 100, y - 100, 80, 100);
//                } catch (Exception ignored) {}
//            }
//            y -= 110;
//
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
//            stream.beginText();
//            stream.newLineAtOffset(margin, y);
//            stream.showText("EXAMINATION DETAILS");
//            stream.endText();
//            y -= 25;
//
//            String[][] examData = {
//                    {"Post:", "Probationary Officer (PO) 2025"},
//                    {"Center Code:", app.getAllocatedCenter() != null ? app.getAllocatedCenter() : "TBD"},
//                    {"Center Address:", centerDetails != null ? centerDetails : "TBD"},
//                    {"Date & Time:", (config.getExamDate() != null ? config.getExamDate() : "TBD") + " | " + (config.getExamTime() != null ? config.getExamTime() : "TBD")}
//            };
//
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
//            for (String[] row : examData) {
//                stream.beginText();
//                stream.newLineAtOffset(margin + 10, y);
//                stream.showText(row[0] + " " + row[1]);
//                stream.endText();
//                y -= 18;
//            }
//            y -= 10;
//
//            // Instructions
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
//            stream.beginText();
//            stream.newLineAtOffset(margin, y);
//            stream.showText("INSTRUCTIONS");
//            stream.endText();
//            y -= 25;
//
//            String[] instructions = (config.getInstructions() != null && !config.getInstructions().isEmpty())
//                    ? config.getInstructions().split("\n")
//                    : new String[] {"1. Carry this card & ID.", "2. Report 1hr early.", "3. No electronics."};
//
//            stream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
//            for (String line : instructions) {
//                stream.beginText();
//                stream.newLineAtOffset(margin + 10, y);
//                stream.showText(line);
//                stream.endText();
//                y -= 12;
//            }
//
//            // Signature
//            if (config.getSignatureFilePath() != null && !config.getSignatureFilePath().isEmpty()) {
//                try {
//                    PDImageXObject sig = PDImageXObject.createFromFile(config.getSignatureFilePath(), doc);
//                    stream.drawImage(sig, page.getMediaBox().getWidth() - margin - 150, margin + 20, 120, 40);
//                } catch (Exception ignored) {}
//            }
//
//            stream.beginText();
//            stream.newLineAtOffset(page.getMediaBox().getWidth() - margin - 140, margin);
//            stream.showText(config.getAuthorizedSignature() != null ? config.getAuthorizedSignature() : "Authorized Sign");
//            stream.endText();
//
//            stream.close();
//            doc.save(filePath);
//        }
//
//        return filePath;
//    }
//}
