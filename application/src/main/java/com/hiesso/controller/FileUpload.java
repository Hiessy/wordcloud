package com.hiesso.controller;


import com.hiesso.service.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/file/upload")
public class FileUpload {

    @Autowired
    Storage storageService;

    /**
     * Endpoint for upload multiple files for processing.
     *
     * @param files text files
     * @return Map with file name as key and Map a value with word as key and count as value
     * @throws IOException
     */
    @PostMapping("")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
        return ResponseEntity.ok(storageService.saveFile(Stream.of(files)));
    }


}
