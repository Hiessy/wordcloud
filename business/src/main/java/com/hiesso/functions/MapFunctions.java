package com.hiesso.functions;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public interface MapFunctions {
    Comparator<Map.Entry<String, Long>> orderByValue = (c1, c2 ) -> c1.getValue().compareTo(c2.getValue());
    Collector<String, ?, Map<String, Long>> createMapCountingString = groupingBy(String::toString, counting());

    Collector<String, ?, Map<String, Long>> createMap = null;
}
