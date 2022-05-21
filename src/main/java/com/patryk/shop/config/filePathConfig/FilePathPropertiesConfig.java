package com.patryk.shop.config.filePathConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FilePathPropertiesConfig {

    private String productsPath;

}