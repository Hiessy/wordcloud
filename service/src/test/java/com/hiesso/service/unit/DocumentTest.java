package com.hiesso.service.unit;

import com.hiesso.service.Storage;
import com.hiesso.service.DocumentImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentImpl.class)
public class DocumentTest {

    public static final String SMALL = "small";
    public static final String RED_BLUE_RED = "red blue red brown white white black red brown pink";
    @Autowired
    DocumentImpl document;

    @MockBean
    Storage storage;

    @Test
    public void testCountUniqueWords() throws IOException {
        when(storage.readFile(SMALL)).thenReturn(RED_BLUE_RED);
        Map<String, Long> wordCount = document.countWords(SMALL);

        assertEquals(6, wordCount.size());
        assertEquals(Long.valueOf("3"), wordCount.get("red"));
        assertEquals(Long.valueOf("1"), wordCount.get("blue"));


    }

}
