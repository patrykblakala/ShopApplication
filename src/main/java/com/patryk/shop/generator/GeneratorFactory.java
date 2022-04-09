package com.patryk.shop.generator;

import com.patryk.shop.generator.domain.FileType;
import com.patryk.shop.generator.strategy.GeneratorsStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratorFactory {

    private final List<GeneratorsStrategy> strategyList;

    private Map<FileType, GeneratorsStrategy> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = strategyList.stream()
                .collect(Collectors.toMap(GeneratorsStrategy::getFileType, Function.identity()));
    }

    public GeneratorsStrategy getByKey(FileType fileType) {
        return strategyMap.get(fileType);
    }
}
