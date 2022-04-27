package com.hiesso.service;

import java.io.IOException;
import java.util.Map;

public interface Document {
    Map<String, Long> countWords(String file) throws IOException;
}
