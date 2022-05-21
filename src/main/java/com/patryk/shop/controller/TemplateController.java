package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.TemplateDto;
import com.patryk.shop.mapper.TemplateMapper;
import com.patryk.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/templates", produces = MediaType.APPLICATION_JSON_VALUE)
public class TemplateController {

    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @GetMapping("/{id}")
    public TemplateDto findTemplateById(@PathVariable Long id) {
        return  templateMapper.daoToDto(templateService.findById(id));
    }

    @GetMapping
    public TemplateDto findTemplateByName(@RequestParam String name) {
        return templateMapper.daoToDto(templateService.findByName(name));
    }

    @PostMapping
    public TemplateDto createTemplate(@RequestBody TemplateDto template) {
        return templateMapper.daoToDto(templateService.save(templateMapper.dtoToDao(template)));
    }

    @DeleteMapping("/{id}")
    public  void deleteById(@PathVariable Long id) {
        templateService.deleteTemplateById(id);
    }

    @PutMapping("/{id}")
    public TemplateDto updateTemplate(@RequestBody TemplateDto template, @PathVariable Long id) {
        return templateMapper.daoToDto(templateService.update(templateMapper.dtoToDao(template), id));
    }
}
