package com.patryk.shop.flyweight.strategy.generator.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.domain.FileType;
import com.patryk.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
@Slf4j
@Component
@RequiredArgsConstructor
public class GeneratorPdf implements StrategyGenerator {
    private final ProductRepository productRepository;
    @Override
    public byte[] generateFile() {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            PdfPTable pdfPTable = new PdfPTable(4);
            pdfPTable.addCell("Id");
            pdfPTable.addCell("Name");
            pdfPTable.addCell("Price");
            pdfPTable.addCell("Quantity");
            productRepository.findAll().forEach(a->{
                pdfPTable.addCell(a.getId().toString());
                pdfPTable.addCell(a.getName());
                pdfPTable.addCell(a.getPrice().toString());
                pdfPTable.addCell(a.getQuantity().toString());
            });
            document.add(pdfPTable);
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
           log.error(e.getMessage(),e) ;
        }
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.PDF;
    }
}
