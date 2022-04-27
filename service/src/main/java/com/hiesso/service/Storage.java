package com.hiesso.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public interface StorageService {
    String readFile(String file) throws IOException;
    List<String> saveFile(Stream<MultipartFile> files) throws IOException;
    void deleteFile(String fileName) throws IOException;
    File getAllFiles();
}
