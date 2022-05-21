package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Template;
import com.patryk.shop.domain.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    TemplateDto daoToDto(Template template);

    Template dtoToDao(TemplateDto templateDto);
}
