package com.hiesso.service.functional;

import com.hiesso.service.StorageImpl;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StorageImpl.class)
public class StorageServiceTest {

    public static final String SMALL = "small";
    public static final String BIG = "big";
    public static final String ERROR = "error";
    public static final String MD5 = "MD5";
    public static final String HUGE = "huge";

    @Autowired
    private StorageImpl storage;

    private String testPath = Paths.get("").toAbsolutePath() + "\\src\\test\\resources\\test_data";

    @After
    public void teardown() {
        Stream.of(Objects.requireNonNull(
                storage.getAllFiles().listFiles()))
                .forEach(deleteFile);
    }

    @Test
    public void testWriteFiles() throws IOException, NoSuchAlgorithmException {
        List<String> result = storage.saveFile(createMultipartFile(BIG + "," + SMALL).stream());
        Assert.assertFalse(result.contains(ERROR));
        Assert.assertTrue(getChecksumFile(SMALL));
        Assert.assertTrue(getChecksumFile(BIG));
    }

    @Test
    public void testReadFile() throws IOException {
        storage.saveFile(createMultipartFile(SMALL).stream());
        String fs = storage.readFile(SMALL);
        Assert.assertEquals(91, fs.length());
    }

    @Test
    public void testDeleteFile() throws IOException {
        storage.saveFile(createMultipartFile(HUGE).stream());
        storage.deleteFile(HUGE);
        assertThrows(NoSuchFileException.class, () -> storage.deleteFile(HUGE));
    }

    @Test
    public void testGetAllFile() throws IOException {
        storage.saveFile(createMultipartFile(HUGE + "," + BIG).stream());
        assertEquals(2, Objects.requireNonNull(storage.getAllFiles().listFiles()).length);
    }

    private boolean getChecksumFile(String fileName) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(MD5);

        byte[] storageByte = getBytes(fileName, storage.getPath());
        byte[] testByte = getBytes(fileName, testPath);

        return Arrays.equals(md.digest(testByte), md.digest(storageByte));
    }

    private byte[] getBytes(String fileName, String path) throws IOException {
        return Files.readAllBytes(Paths.get(path + "\\" + fileName));
    }

    private List<MultipartFile> createMultipartFile(String fileNames) {
        return Stream.of(fileNames.split(",")).map(fileName -> {
            File file = new File(testPath + "\\" + fileName);
            try {
                FileInputStream input = new FileInputStream(file);
                return new MockMultipartFile("file",
                        file.getName(), "text/plain", IOUtils.toByteArray(input));
            } catch (IOException e) {
                byte[] bytes = new byte[]{(byte) 0xe0};
                e.printStackTrace();
                return new MockMultipartFile("file",
                        file.getName(), "text/plain", bytes);
            }
        }).collect(Collectors.toList());
    }

    private Consumer<File> deleteFile = file -> {
        try {
            storage.deleteFile(file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

}
