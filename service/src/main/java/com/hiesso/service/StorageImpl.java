package com.hiesso.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.delete;

@Service
public class StorageImpl implements Storage {

    public static final String UPLOADED_FOLDER = "upload";
    public static final Charset CHARSET = Charset.defaultCharset();
    private String path;

    public StorageImpl() {
        path = Paths.get("").toAbsolutePath().toString()
                .concat("\\" + UPLOADED_FOLDER) + "\\";
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();
    }

    @Override
    public String readFile(String fileName) throws IOException {
        File file = new File(path + fileName);
        InputStream inputStream = new FileInputStream(file);
        String document = IOUtils.toString(inputStream, CHARSET);
        inputStream.close();
        return document;
    }

    @Override
    public List<String> saveFile(Stream<MultipartFile> files) {
        return files.map(saveFile).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        delete(Path.of(path + fileName));
    }

    @Override
    public File getAllFiles() {
        return Paths.get(path).toFile();
    }

    public String getPath() {
        return path;
    }

    private Function<MultipartFile, String> saveFile = file -> {
        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File(path + fileName));
            return "saved: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "error " + fileName;
        }
    };
}
