package com.patryk.shop.service.impl;

import com.patryk.shop.domain.dao.Template;
import com.patryk.shop.repository.TemplateRepository;
import com.patryk.shop.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public Template save(Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Template findById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("template not found"));
    }

    @Override
    public Template findByName(String name) {
        return templateRepository.findByName(name)
                .orElseThrow(()-> new EntityNotFoundException("template not found"));
    }

    @Override
    public Template update(Template template, Long id) {
        var templateDb = findById(id);
        templateDb.setName(template.getName());
        templateDb.setBody(template.getBody());
        templateDb.setSubject(template.getSubject());

        return templateRepository.save(templateDb);
    }

    @Override
    public void deleteTemplateById(Long id) {
        templateRepository.deleteById(id);
    }
}
