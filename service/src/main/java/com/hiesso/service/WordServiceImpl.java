package com.hiesso.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.hiesso.functions.MapFunctions.*;
import static com.hiesso.functions.MapSupplier.byValue;
import static com.hiesso.functions.StringFilter.lengthSmallerThanThree;

@Service
public class WordServiceImpl implements WordService {

    public static final String EMPTY = "";
    static String DELIMITERS = "[,;:.\\s\"]";
    static String CHAR_TO_REMOVE = "[0-9,;:.\\s\"()%\\[\\]\\?\\-±·™−'“”]";
    static private Function<String, String> removeSpecialCharacter = word -> word.replaceAll(CHAR_TO_REMOVE, EMPTY);

    @Autowired
    StorageService storageService;

    @Override
    public Map<String, Long> count(String fileName) throws IOException {
        String document = storageService.readFile(fileName);
        Stream<String> documentStream = Stream.of(document.toLowerCase().split(DELIMITERS));
        return documentStream.map(removeSpecialCharacter)
                .filter(lengthSmallerThanThree)
                .collect(createMapCountingString)
                .entrySet().stream()
                .sorted(orderByValue.reversed())
                .collect(byValue);
    }

}
