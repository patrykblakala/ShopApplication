package com.patryk.shop.flyweight;

import com.patryk.shop.flyweight.strategy.GenericStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenericFactory<K, V extends GenericStrategy<K>> {

    private final List<V> strategyList;
    private Map<K, V> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = strategyList.stream()
                .collect(Collectors.toMap(GenericStrategy::getType, Function.identity()));
    }

    public V getByKey(K k) {
        return strategyMap.get(k);
    }
}
