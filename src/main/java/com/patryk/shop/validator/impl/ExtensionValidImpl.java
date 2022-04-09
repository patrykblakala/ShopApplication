package com.patryk.shop.validator.impl;

import com.patryk.shop.validator.ExtensionValid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExtensionValidImpl implements ConstraintValidator<ExtensionValid, MultipartFile> {

    private static final List<String> EXTENSIONS = List.of("png", "jpg");

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return EXTENSIONS.contains(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
    }
}
