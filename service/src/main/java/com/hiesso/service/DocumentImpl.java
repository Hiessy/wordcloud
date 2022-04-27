package com.hiesso.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static com.hiesso.functions.BusinessFunctions.removeSpecialCharacter;
import static com.hiesso.functions.MapFunctions.createMapCountingString;
import static com.hiesso.functions.MapFunctions.orderByValue;
import static com.hiesso.functions.MapSupplier.byValue;
import static com.hiesso.functions.StringFilter.lengthSmallerThanThree;

@Service
public class WordImpl implements Word {
    static String DELIMITERS = "[,;:.\\s\"]";

    @Autowired
    Storage storage;
    @Override
    public Map<String, Long> count(String fileName) throws IOException {
        String document = storage.readFile(fileName);

        Stream<String> documentStream = Stream.of(document.toLowerCase().split(DELIMITERS)).map(removeSpecialCharacter);

        return documentStream
                .filter(lengthSmallerThanThree)
                .collect(createMapCountingString)
                .entrySet().stream()
                .sorted(orderByValue.reversed())
                .collect(byValue);
    }

}