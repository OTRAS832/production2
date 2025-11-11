package com.OTRAS.DemoProject.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfExtractionService {

    public Map<String, Object> extractExamCentersFromPdf(MultipartFile pdfFile) {

        List<Map<String, String>> centers = new ArrayList<>();
        String rawText = "";

        try (PDDocument document = Loader.loadPDF(pdfFile.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();
            rawText = stripper.getText(document);
            String[] lines = rawText.split("\n");

            Pattern linePattern = Pattern.compile("^(\\S+)\\s+(.+?)\\s+(\\d+)$");

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Matcher match = linePattern.matcher(line);

                if (match.find()) {
                    Map<String, String> center = new HashMap<>();

                    String code = match.group(1).trim();
                    String details = match.group(2).trim();
                    String capacity = match.group(3).trim();

                    String centerName;
                    String address;

                    if (details.contains(",")) {
                        centerName = details.split(",")[0].trim();
                        address = details.trim();
                    } else {
                        centerName = details;
                        address = details;
                    }

                    center.put("centerCode", code);
                    center.put("centerName", centerName);
                    center.put("address", address);
                    center.put("capacity", capacity);

                    centers.add(center);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("PDF extraction failed: " + e.getMessage(), e);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("extractedCenters", centers);
        result.put("totalCenters", centers.size());
        result.put("rawText", rawText);

        return result;
    }
}
