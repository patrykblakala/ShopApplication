package com.patryk.shop.flyweight.strategy.generator.impl;

import com.patryk.shop.domain.dao.Product;
import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.domain.FileType;
import com.patryk.shop.repository.ProductRepository;
import com.patryk.shop.security.SecurityUtils;
import com.patryk.shop.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeneratorXls implements StrategyGenerator {

    private final ProductRepository productRepository;

    private final MailService mailService;

    @Override
    public byte[] generateFile() {
        try (HSSFWorkbook hssfWorkbook = new HSSFWorkbook()) {
            HSSFSheet sheet = hssfWorkbook.createSheet("Report");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("Price");
            row.createCell(3).setCellValue("Quantity");

            List<Product> all = productRepository.findAll();
            for (int i = 0; i < all.size(); i++) {
                row = sheet.createRow(i + 1);
                Product product = all.get(i);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getPrice());
                row.createCell(3).setCellValue(product.getQuantity());
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            hssfWorkbook.write(byteArrayOutputStream);
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            mailService.send(currentUserEmail, "XlsEmailTemplate", Collections.emptyMap(),byteArrayOutputStream.toByteArray(), "Report");
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.XLS;
    }
}
