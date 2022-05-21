package com.patryk.shop.service;

import com.patryk.shop.domain.dao.Template;

public interface TemplateService {

    Template save(Template template);

    Template findById(Long id);

    Template findByName(String name);

    Template update(Template template, Long id);

    void deleteTemplateById(Long id);
}
