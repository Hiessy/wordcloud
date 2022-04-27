package com.hiesso.service.unit;

import com.hiesso.service.StorageService;
import com.hiesso.service.WordServiceImpl;
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
@SpringBootTest(classes = WordServiceImpl.class)
public class WordCountTest {

    public static final String SMALL = "small";
    public static final String RED_BLUE_RED = "red blue red brown white white black red brown pink";
    @Autowired
    WordServiceImpl wordService;

    @MockBean
    StorageService storageService;

    @Test
    public void testCountUniqueWords() throws IOException {
        when(storageService.readFile(SMALL)).thenReturn(RED_BLUE_RED);
        Map<String, Long> wordCount = wordService.count(SMALL);

        assertEquals(2, wordCount.size());
        assertEquals(Long.valueOf("2"), wordCount.get("red"));
        assertEquals(Long.valueOf("1"), wordCount.get("blue"));
    }

}
