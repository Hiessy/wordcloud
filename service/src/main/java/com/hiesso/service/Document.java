package com.hiesso.service;

import java.io.IOException;
import java.util.Map;

public interface Word {
    Map<String, Long> count(String file) throws IOException;
}
