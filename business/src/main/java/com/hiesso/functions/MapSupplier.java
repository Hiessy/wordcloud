package com.hiesso.functions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface MapSupplier {

    BinaryOperator<Long> binaryOperator = (key, value) -> value;
    Collector<Map.Entry<String, Long>, ?, LinkedHashMap<String, Long>> byValue =
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, binaryOperator, LinkedHashMap::new);

}
