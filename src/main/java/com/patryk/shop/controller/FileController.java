package com.patryk.shop.controller;

import com.patryk.shop.flyweight.GenericFactory;
import com.patryk.shop.flyweight.strategy.generator.StrategyGenerator;
import com.patryk.shop.generator.GeneratorFactory;
import com.patryk.shop.generator.domain.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final GeneratorFactory generatorFactory;
    private final GenericFactory<FileType, StrategyGenerator> genericFactory;

    @GetMapping
    public void getFile(@RequestParam FileType fileType) {
        generatorFactory.getByKey(fileType).generateFile();
    }

    @GetMapping("/generic")
    public ResponseEntity<byte[]> getFileGeneric(@RequestParam FileType fileType) {
        byte[] file = genericFactory.getByKey(fileType).generateFile();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.set(HttpHeaders.CONTENT_LENGTH, Integer.toString(file.length));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=report." + fileType.name().toLowerCase());
        return ResponseEntity.ok().headers(headers).body(file);

    }

}
