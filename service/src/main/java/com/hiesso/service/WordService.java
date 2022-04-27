package com.hiesso.service;

import java.io.IOException;
import java.util.Map;

public interface WordService {
    Map<String, Long> count(String file) throws IOException;
}
