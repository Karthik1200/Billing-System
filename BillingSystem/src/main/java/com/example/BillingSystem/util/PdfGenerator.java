package com.example.BillingSystem.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class PdfGenerator {

    public byte[] generateInvoicePdf(Long invoiceId, String invoiceNumber, String customerName, String amount) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph title = new Paragraph("INVOICE");
            title.setAlignment(1);
            document.add(title);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Invoice Number: " + invoiceNumber));
            document.add(new Paragraph("Generated Date: " + LocalDateTime.now()));
            document.add(new Paragraph("Customer: " + customerName));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Amount: " + amount));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    public byte[] generateReportPdf(String title, String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph titlePara = new Paragraph(title);
            titlePara.setAlignment(1);
            document.add(titlePara);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph(content));
            document.add(new Paragraph("Generated: " + LocalDateTime.now()));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
