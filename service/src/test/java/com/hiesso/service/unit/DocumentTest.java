package com.hiesso.service.unit;

import com.hiesso.service.DocumentImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentImpl.class)
public class DocumentTest {

    public static final String RED_BLUE_RED = "red blue red brown white white black red brown pink";
    @Autowired
    DocumentImpl document;

    @Test
    public void testCountUniqueWords() throws IOException {
        Map<String, Long> wordCount = document.countWords(RED_BLUE_RED);

        assertEquals(6, wordCount.size());
        assertEquals(Long.valueOf("3"), wordCount.get("red"));
        assertEquals(Long.valueOf("1"), wordCount.get("blue"));


    }

}
