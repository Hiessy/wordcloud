package com.hiesso.controller;

import com.hiesso.service.Document;
import com.hiesso.service.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/file")
public class WordCloud {

    @Autowired
    Storage storage;

    @Autowired
    Document document;

    /**
     * Endpoint for upload multiple files for processing.
     *
     * @param files text files
     * @return Map with file name as key and Map a value with word as key and count as value
     * @throws IOException
     */
    @GetMapping("/word/cloud")
    public ResponseEntity<Object> getWorldCloud(@RequestParam(value = "files") List<String> files) {
        Map<String, Object> response = generateResponse(files);
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> generateResponse(List<String> files) {
        Map<String, Object> response = new HashMap<>();
        files.forEach(file -> {
            try {
                response.put(
                        file, document.countWords(storage.readFile(file)));
            } catch (IOException e) {
                response.put(
                        file, e.getStackTrace());
            }
        });

        return response;
    }
}
