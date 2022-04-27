package com.hiesso.functions;

import java.util.function.Predicate;

public interface StringFilter {
    Predicate<String> lengthSmallerThanThree = word -> word.length() >= 3;

}
