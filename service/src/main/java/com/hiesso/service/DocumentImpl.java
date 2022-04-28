package com.hiesso.service;


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
public class DocumentImpl implements Document {

    static String DELIMITERS = "[,;:.\\s\"]";


    @Override
    public Map<String, Long> countWords(String document) throws IOException {

        Stream<String> documentStream = Stream.of(document.toLowerCase().split(DELIMITERS)).map(removeSpecialCharacter);

        return documentStream
                .filter(lengthSmallerThanThree)
                .collect(createMapCountingString)
                .entrySet().stream()
                .sorted(orderByValue.reversed())
                .collect(byValue);
    }

}
