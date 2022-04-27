package com.hiesso.functions;

import java.util.function.Function;

public interface BusinessFunctions {

    String EMPTY = "";
    String CHAR_TO_REMOVE = "[0-9,;:.\\s\"()%\\[\\]\\?\\-±·™−'“”]";

    Function<String, String> removeSpecialCharacter = word -> word.replaceAll(CHAR_TO_REMOVE, EMPTY);
}
