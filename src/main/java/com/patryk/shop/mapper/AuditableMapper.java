package com.patryk.shop.mapper;

import com.patryk.shop.domain.dao.Auditable;
import com.patryk.shop.domain.dto.AuditableDto;
import com.patryk.shop.security.SecurityUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface AuditableMapper<T extends Auditable, R extends AuditableDto> {
    @AfterMapping
    default void auditableMap(T dao, @MappingTarget R.AuditableDtoBuilder<?, ?> dto) {
        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            dto.createdBy(null);
            dto.lastModifiedBy(null);
            dto.version(null);
            dto.createdDate(null);
            dto.lastModifiedDate(null);
        }
    }
}
